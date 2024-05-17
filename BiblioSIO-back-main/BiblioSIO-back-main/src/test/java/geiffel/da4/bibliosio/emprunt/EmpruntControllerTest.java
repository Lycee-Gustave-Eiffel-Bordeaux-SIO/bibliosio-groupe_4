package geiffel.da4.bibliosio.emprunt;

import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import exceptions.ExceptionHandlingAdvice;
import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
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
@ContextConfiguration(classes = EmpruntController.class)
@Import(ExceptionHandlingAdvice.class)
public class EmpruntControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean(name = "jpaEmprunts")
    EmpruntService empruntService;

    private List<Emprunt> emprunts;
    private Emprunteur emprunteur1, emprunteur2;
    private Exemplaire ex1, ex2;
    @BeforeEach
    void setUp() {
        emprunteur1 = Mockito.mock(Emprunteur.class);
        emprunteur2 = Mockito.mock(Emprunteur.class);
        ex1 = Mockito.mock(Exemplaire.class);
        ex2 = Mockito.mock(Exemplaire.class);

        Mockito.when(emprunteur1.getId()).thenReturn(1L);
        Mockito.when(emprunteur2.getId()).thenReturn(2L);
        Mockito.when(ex1.getId()).thenReturn(1L);
        Mockito.when(ex2.getId()).thenReturn(2L);

        emprunts = new ArrayList<>(){{
            add(new Emprunt(1L,"01/01/2023","11/01/2023","statut1",emprunteur1,ex1, null));
            add(new Emprunt(2L,"02/02/2023","12/02/2023","statut2",emprunteur2,ex2, null));
            add(new Emprunt(3L,"03/03/2023","13/03/2023","statut3",emprunteur1,ex2, null));
            add(new Emprunt(14L,"04/04/2023","14/04/2023","statut4",emprunteur1,ex1, null));
            add(new Emprunt(7L,"05/05/2023","15/05/2023","statut5",emprunteur2,ex2, null));
            add(new Emprunt(28L,"06/06/2023","16/06/2023","statut6",emprunteur2,ex1, null));
        }};
        when(empruntService.getAll()).thenReturn(emprunts);
        when(empruntService.getById(7L)).thenReturn(emprunts.get(4));
        when(empruntService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {
        mockMvc.perform(get("/emprunts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/emprunts/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.idemprunt", is(7))
        ).andExpect(jsonPath("$.datedebut", is("05/05/2023"))
        ).andExpect(jsonPath("$.dateretour", is("15/05/2023"))
        ).andExpect(jsonPath("$.statut", is("statut5"))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/emprunts/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Emprunt new_emp = new Emprunt(89L , "dated", "datef","statut", emprunteur1,ex1,null);
        ArgumentCaptor<Emprunt> emp_received = ArgumentCaptor.forClass(Emprunt.class);
        when(empruntService.create(any())).thenReturn(new_emp);

        mockMvc.perform(post("/emprunts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_emp))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/emprunts/"+new_emp.getId())
        ).andDo(print());

        verify(empruntService).create(emp_received.capture());
        assertEquals(new_emp, emp_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(empruntService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/emprunts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.emprunts.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveEmprunteurToUpdate_andReturnNoContent() throws Exception {
        Emprunt initial_emp = emprunts.get(1);
        Emprunt updated_emp = new Emprunt(initial_emp.getId() , "dated", "datef","statut", emprunteur1,ex1,null);
        ArgumentCaptor<Emprunt> emp_received = ArgumentCaptor.forClass(Emprunt.class);

        mockMvc.perform(put("/emprunts/"+initial_emp.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_emp))
        ).andExpect(status().isNoContent());

        verify(empruntService).update(anyLong(), emp_received.capture());
        assertEquals(updated_emp, emp_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/emprunts/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(empruntService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }
}
