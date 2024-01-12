package geiffel.da4.bibliosio.exemplaire;

import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;
import geiffel.da4.bibliosio.revue.Revue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@ContextConfiguration(classes = ExemplaireJPAService.class)
@SpringBootTest
public class ExemplaireServiceTest {

    @Qualifier("jpa")
    @Autowired
    private ExemplaireService exemplaireService;

    private List<Exemplaire> exemplaires;

    @MockBean
    private ExemplaireRepository exemplaireRepository;


    @BeforeEach
    void setUp() {
        Revue revue1 = new Revue(1L,"revue1");
        Revue revue2 = new Revue(2L,"revue2");
        exemplaires = new ArrayList<>(){{
            add(new Exemplaire(1L,"titre1","01/01/2023","statut1",revue1));
            add(new Exemplaire(2L,"titre2","02/02/2023","statut2",revue1));
            add(new Exemplaire(3L,"titre3","03/03/2023","statut3",revue2));

        }};
        Exemplaire exemplaire =exemplaires.get(0);
        when(exemplaireRepository.findById(1L)).thenReturn(Optional.of(exemplaire));
    }

    @Test
    void testGetAll_renvoie3(){
        when(exemplaireRepository.findAll()).thenReturn(exemplaires);
        assertEquals(3 , exemplaireService.getAll().size());
    }

    @Test
    void testGetById(){
        when(exemplaireRepository.findById(1L)).thenReturn(Optional.of(exemplaires.get(0)));
        when(exemplaireRepository.findById(12L)).thenReturn(Optional.empty());
        assertAll(
                ()-> assertEquals(exemplaires.get(0), exemplaireService.getById(1L)),
                ()-> assertThrows(ResourceNotFoundException.class, ()-> exemplaireService.getById(12L))
        );


    }

    @Test
    void testCreation(){
        Revue revue = new Revue(3L,"titre");
        Exemplaire exemplaire = new Exemplaire(5L , "Titre1", "novembre 2010","statut", revue);
        Exemplaire exemplaire1 = new Exemplaire(3L , "Titre1", "novembre 2010","statut", revue);
        when(exemplaireRepository.save(any(Exemplaire.class))).thenReturn(exemplaire);
        when(exemplaireRepository.existsById(exemplaire1.getIDEX())).thenReturn(true);
        assertAll(
                ()-> assertEquals(exemplaire, exemplaireService.create(exemplaire)),
                ()-> assertThrows(ResourceAlreadyExistsException.class, ()-> exemplaireService.create(exemplaire1))
        );
    }

    @Test
    void testUpdate(){
        Exemplaire exemplaire = exemplaires.get(0);
        exemplaire.setTITREEX("nouveauTitre");

        when(exemplaireRepository.existsById(exemplaire.getIDEX())).thenReturn(true);
        when(exemplaireRepository.save(any(Exemplaire.class))).thenReturn(exemplaire);

        assertEquals(exemplaire, exemplaireService.update(exemplaire.getIDEX(), exemplaire));
    }



    @Test
    void testUpdateError(){
        Revue revue = new Revue(3L,"titre");
        Exemplaire exemplaire = new Exemplaire(5L , "Titre1", "novembre 2010","statut", revue);
        exemplaire.setIDEX(3L);
        when(exemplaireRepository.exists(Example.of(exemplaire))).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> exemplaireService.update(exemplaire.getIDEX(), exemplaire));
    }

    @Test
    void testDelete(){
        Exemplaire toDelete = exemplaires.get(0);
        when(exemplaireRepository.existsById(toDelete.getIDEX())).thenReturn(true);
        exemplaireService.delete(toDelete.getIDEX());
        verify(exemplaireRepository).deleteById(toDelete.getIDEX());
    }

    @Test
    void testDeleteError(){
        Revue revue = new Revue(3L,"titre");
        Exemplaire toDelete = new Exemplaire(51L , "Titre1", "novembre 2010","statut", revue);
        doThrow(ResourceNotFoundException.class).when(exemplaireRepository).deleteById(any());

        assertThrows(ResourceNotFoundException.class, () -> exemplaireService.delete(toDelete.getIDEX()));
    }

}
