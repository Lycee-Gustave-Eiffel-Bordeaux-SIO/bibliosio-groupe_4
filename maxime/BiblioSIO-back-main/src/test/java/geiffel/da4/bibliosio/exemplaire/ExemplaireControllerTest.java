package geiffel.da4.bibliosio.exemplaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import geiffel.da4.bibliosio.exceptions.ExceptionHandlingAdvice;
import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;
import geiffel.da4.bibliosio.revue.Revue;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = ExemplaireController.class)
@Import(ExceptionHandlingAdvice.class)
public class ExemplaireControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean(name = "jpa")
    ExemplaireService exemplaireService;

    private List<Exemplaire> exemplaires;

    @BeforeEach
    void setUp() {
        Revue revue1 = new Revue(1L,"revue1");
        Revue revue2 = new Revue(2L,"revue2");
        Revue revue3 = new Revue(3L,"revue3");
        exemplaires= new ArrayList<>(){{
            add(new Exemplaire(1L,"titre1","01/01/2023","statut1",revue1));
            add(new Exemplaire(2L,"titre2","02/02/2023","statut2",revue1));
            add(new Exemplaire(3L,"titre3","03/03/2023","statut3",revue2));
            add(new Exemplaire(14L,"titre4","04/04/2023","statut4",revue3));
            add(new Exemplaire(7L,"titre5","05/05/2023","statut5",revue3));
            add(new Exemplaire(28L,"titre6","06/06/2023","statut6",revue3));

        }};
        when(exemplaireService.getAll()).thenReturn(exemplaires);
        when(exemplaireService.getById(7L)).thenReturn(exemplaires.get(4));
        when(exemplaireService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {
        mockMvc.perform(get("/exemplaires")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/exemplaires/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.idex", is(7))
        ).andExpect(jsonPath("$.titreex", is("titre5"))
        ).andExpect(jsonPath("$.dateparuex", is("05/05/2023"))
        ).andExpect(jsonPath("$.statutex", is("statut5"))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/exemplaires/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Revue revue = new Revue(4L,"titre");
        Exemplaire new_ex = new Exemplaire(89L, "test", "test","test",revue);
        ArgumentCaptor<Exemplaire> ex_received = ArgumentCaptor.forClass(Exemplaire.class);
        when(exemplaireService.create(any())).thenReturn(new_ex);

        mockMvc.perform(post("/exemplaires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_ex))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/exemplaires/"+new_ex.getIDEX())
        ).andDo(print());

        verify(exemplaireService).create(ex_received.capture());
        assertEquals(new_ex, ex_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(exemplaireService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/exemplaires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.exemplaires.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveEmprunteurToUpdate_andReturnNoContent() throws Exception {
        Revue revue = new Revue(4L,"titre");
        Exemplaire initial_ex = exemplaires.get(1);
        Exemplaire updated_ex = new Exemplaire(initial_ex.getIDEX(), "updated","updated", "updated",revue);
        ArgumentCaptor<Exemplaire> ex_received = ArgumentCaptor.forClass(Exemplaire.class);

        mockMvc.perform(put("/exemplaires/"+initial_ex.getIDEX())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_ex))
        ).andExpect(status().isNoContent());

        verify(exemplaireService).update(anyLong(), ex_received.capture());
        assertEquals(updated_ex, ex_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/exemplaires/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(exemplaireService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }
}
