package bibliosio.back.revue;

import bibliosio.back.exceptions.ExceptionHandlingAdvice;
import bibliosio.back.exceptions.ResourceAlreadyExistsException;
import bibliosio.back.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RevueController.class)
@ContextConfiguration(classes = RevueController.class)
@Import({ExceptionHandlingAdvice.class, RevueController.class})
public class RevueControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean(name="jpaRevues")
    RevueService revueService;

    private List<Revue> revues;

    @BeforeEach
    void setUp() 
    {
        revues = new ArrayList<>() 
        {{
            add(new Revue(1L, "Machin"));
            add(new Revue(2L, "Chose"));
            add(new Revue(3L, "Truc"));
            add(new Revue(14L, "higher"));
            add(new Revue(7L, "lower"));
            add(new Revue(28L, "way higher"));
        }};
        when(revueService.getAll()).thenReturn(revues);
        when(revueService.getById(7L)).thenReturn(revues.get(4));
        when(revueService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception
    {
        when(revueService.getAll()).thenReturn(revues);
        assertEquals(6, revueService.getAll().size());

        mockMvc.perform(get("/revues")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception
    {
        Revue revue = new Revue();
        revue.setId(7L);
        revue.setTitre("lower");

        when(revueService.getById(7L)).thenReturn(revue);
        assertEquals("lower", revue.getTitre());

        mockMvc.perform(get("/revues/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(content().string("{\"id\":7,\"titre\":\"lower\"}")
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception
    {
        doThrow(ResourceNotFoundException.class).when(revueService).getById(49L);

        mockMvc.perform(get("/revues/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception
    {
        Revue new_revue = new Revue(89L, "nouveau");
        ArgumentCaptor<Revue> revue_received = ArgumentCaptor.forClass(Revue.class);

        when(revueService.create(any())).thenReturn(new_revue);

        mockMvc.perform(post("/revues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_revue))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/revues/"+new_revue.getId())
        ).andDo(print());

        verify(revueService).create(revue_received.capture());
        assertEquals(new_revue, revue_received.getValue());
    }


    @Test
    void whenCreatingWithExistingId_should404() throws Exception
    {
        when(revueService.create(any())).thenThrow(ResourceAlreadyExistsException.class);

        mockMvc.perform(post("/revues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.revues.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveRevueToUpdate_andReturnNoContent() throws Exception
    {
        Revue initial_revue = revues.get(1);
        Revue updated_revue = new Revue(initial_revue.getId(), "titre updated");
        ArgumentCaptor<Revue> revue_received = ArgumentCaptor.forClass(Revue.class);

        mockMvc.perform(put("/revues/"+initial_revue.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_revue))
        ).andExpect(status().isNoContent());

        verify(revueService).update(anyLong(), revue_received.capture());
        assertEquals(updated_revue, revue_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception
    {
        Long id = 28L;

        mockMvc.perform(delete("/revues/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(revueService).delete(id_received.capture());

        assertEquals(id, id_received.getValue());
    }
    
}