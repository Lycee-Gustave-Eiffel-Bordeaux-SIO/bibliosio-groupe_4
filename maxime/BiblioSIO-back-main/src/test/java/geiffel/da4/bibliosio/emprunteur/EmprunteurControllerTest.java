package geiffel.da4.bibliosio.emprunteur;

import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.bibliosio.exceptions.ExceptionHandlingAdvice;
import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = EmprunteurController.class)
@Import(ExceptionHandlingAdvice.class)
public class EmprunteurControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean(name = "jpa")
    EmprunteurService emprunteurService;

    private List<Emprunteur> emprunteurs;

    @BeforeEach
    void setUp() {
        emprunteurs= new ArrayList<>(){{
            add(new Emprunteur(1L,"Rouzeau","Paul","paulR@gmail.com"));
            add(new Emprunteur(2L,"Larrieu","Joseph","josephL@gmail.com"));
            add(new Emprunteur(3L,"Oriot","Maxime","maximeO@gmail.com"));
            add(new Emprunteur(14L,"Garrido","Fernand","FernandG@gmail.com"));
            add(new Emprunteur(7L,"Jobs","Steve","SteveJ@gmail.com"));
            add(new Emprunteur(28L,"Benzema","Karim","KarimB@gmail.com"));
        }};
        when(emprunteurService.getAll()).thenReturn(emprunteurs);
        when(emprunteurService.getById(7L)).thenReturn(emprunteurs.get(4));
        when(emprunteurService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {
        mockMvc.perform(get("/emprunteurs")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/emprunteurs/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.NUMEROEMP", is(7))
        ).andExpect(jsonPath("$.nomemp", is("Jobs"))
        ).andExpect(jsonPath("$.prenomemp", is("Steve"))
        ).andExpect(jsonPath("$.mailemp", is("SteveJ@gmail.com"))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/emprunteurs/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Emprunteur new_emp = new Emprunteur(89L, "test", "test","test");
        ArgumentCaptor<Emprunteur> emp_received = ArgumentCaptor.forClass(Emprunteur.class);
        when(emprunteurService.create(any())).thenReturn(new_emp);

        mockMvc.perform(post("/emprunteurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_emp))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/emprunteurs/"+new_emp.getNUMEROEMP())
        ).andDo(print());

        verify(emprunteurService).create(emp_received.capture());
        assertEquals(new_emp, emp_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(emprunteurService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/emprunteurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.emprunteurs.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveEmprunteurToUpdate_andReturnNoContent() throws Exception {
        Emprunteur initial_emp = emprunteurs.get(1);
        Emprunteur updated_emp = new Emprunteur(initial_emp.getNUMEROEMP(), "updated","updated", "updated");
        ArgumentCaptor<Emprunteur> emp_received = ArgumentCaptor.forClass(Emprunteur.class);

        mockMvc.perform(put("/emprunteurs/"+initial_emp.getNUMEROEMP())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_emp))
        ).andExpect(status().isNoContent());

        verify(emprunteurService).update(anyLong(), emp_received.capture());
        assertEquals(updated_emp, emp_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/emprunteurs/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(emprunteurService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }
}
