package geiffel.da4.bibliosio.emprunt;

import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = EmpruntJPAService.class)
@SpringBootTest
public class EmpruntServiceTest {

    @Qualifier("jpaEmprunts")
    @Autowired
    private EmpruntService empruntService;

    private List<Emprunt> emprunts;

    @MockBean
    private EmpruntRepository empruntRepository;
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
            add(new Emprunt(1L,"01/01/2023","11/01/2023","statut1",emprunteur1,ex1,null));
            add(new Emprunt(2L,"02/02/2023","12/02/2023","statut2",emprunteur2,ex2,null));
            add(new Emprunt(3L,"03/03/2023","13/03/2023","statut3",emprunteur1,ex2,null));
        }};
        Emprunt emprunt =emprunts.get(0);
        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunt));
    }

    @Test
    void testGetAll_renvoie3(){
        when(empruntRepository.findAll()).thenReturn(emprunts);
        assertEquals(3 , empruntService.getAll().size());
    }

    @Test
    void testGetById(){
        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunts.get(0)));
        when(empruntRepository.findById(12L)).thenReturn(Optional.empty());
        assertAll(
                ()-> assertEquals(emprunts.get(0), empruntService.getById(1L)),
                ()-> assertThrows(ResourceNotFoundException.class, ()-> empruntService.getById(12L))
        );
    }

    @Test
    void testCreation(){
        Emprunt emprunt = new Emprunt(5L , "dated", "datef","statut", emprunteur1,ex1,null);
        Emprunt emprunt1 = new Emprunt(3L , "dated", "datef","statut", emprunteur1,ex1,null);
        when(empruntRepository.save(any(Emprunt.class))).thenReturn(emprunt);
        when(empruntRepository.existsById(emprunt1.getId())).thenReturn(true);
        assertAll(
                ()-> assertEquals(emprunt, empruntService.create(emprunt)),
                ()-> assertThrows(ResourceAlreadyExistsException.class, ()-> empruntService.create(emprunt1))
        );
    }

    @Test
    void testUpdate(){
        Emprunt emprunt = emprunts.get(0);
        emprunt.setStatut("nouveauStatut");

        when(empruntRepository.existsById(emprunt.getId())).thenReturn(true);
        when(empruntRepository.save(any(Emprunt.class))).thenReturn(emprunt);

        assertEquals(emprunt, empruntService.update(emprunt.getId(), emprunt));
    }

    @Test
    void testUpdateError(){
        Emprunt emprunt = new Emprunt(5L , "dated", "datef","statut", emprunteur1,ex1,null);
        emprunt.setId(3L);
        when(empruntRepository.exists(Example.of(emprunt))).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> empruntService.update(emprunt.getId(), emprunt));
    }

    @Test
    void testDelete(){
        Emprunt toDelete = emprunts.get(0);
        when(empruntRepository.existsById(toDelete.getId())).thenReturn(true);
        empruntService.delete(toDelete.getId());
        verify(empruntRepository).deleteById(toDelete.getId());
    }

    @Test
    void testDeleteError(){
        Emprunt toDelete = new Emprunt(51L , "dated", "datef","statut", emprunteur2,ex2,null);
        doThrow(ResourceNotFoundException.class).when(empruntRepository).deleteById(any());

        assertThrows(ResourceNotFoundException.class, () -> empruntService.delete(toDelete.getId()));
    }
}
