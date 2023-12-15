package bibliosio.back.emprunteur;


import bibliosio.back.exceptions.ResourceAlreadyExistsException;
import bibliosio.back.exceptions.ResourceNotFoundException;
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

@ContextConfiguration(classes = EmprunteurJPAService.class)
@SpringBootTest
public class EmprunteurServiceTest {

    @Qualifier("jpa")
    @Autowired
    private EmprunteurService emprunteurService;

    private List<Emprunteur> emprunteurs;

    @MockBean
    private EmprunteurRepository emprunteurRepository;

    @BeforeEach
    void setUp() {
        emprunteurs = new ArrayList<>(){{
            add(new Emprunteur(1L,"Rouzeau","Paul","paulR@gmail.com"));
            add(new Emprunteur(2L,"Larrieu","Joseph","josephL@gmail.com"));
            add(new Emprunteur(3L,"Oriot","Maxime","maximeO@gmail.com"));
        }};
        Emprunteur emprunteur =emprunteurs.get(0);
        when(emprunteurRepository.findById(1L)).thenReturn(Optional.of(emprunteur));
    }

    @Test
    void whenGettingAll_shouldReturn3() {
        when(emprunteurRepository.findAll()).thenReturn(emprunteurs);
        assertEquals(3, emprunteurService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        when(emprunteurRepository.findById(1L)).thenReturn(Optional.of(emprunteurs.get(0)));
        when(emprunteurRepository.findById(3L)).thenReturn(Optional.of(emprunteurs.get(2)));
        when(emprunteurRepository.findById(12L)).thenReturn(Optional.empty());
        assertAll(
                () -> assertEquals(emprunteurs.get(0), emprunteurService.getById(1L)),
                () -> assertEquals(emprunteurs.get(2), emprunteurService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> emprunteurService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> emprunteurService.getById(4L))
        );
    }

    @Test
    void testCreation(){
        Emprunteur emprunteur = new Emprunteur(5L , "nom", "prenom", "mail");
        Emprunteur emprunteur1 = new Emprunteur(3L,"nom", "prenom", "mail");
        when(emprunteurRepository.save(any(Emprunteur.class))).thenReturn(emprunteur);
        when(emprunteurRepository.existsById(emprunteur1.getNUMEROEMP())).thenReturn(true);
        assertAll(
                ()-> assertEquals(emprunteur, emprunteurService.create(emprunteur)),
                ()-> assertThrows(ResourceAlreadyExistsException.class, ()-> emprunteurService.create(emprunteur1))
        );
    }


    @Test
    void testUpdate(){
        Emprunteur emprunteur = emprunteurs.get(0);
        emprunteur.setNOMEMP("nouveauNom");

        when(emprunteurRepository.existsById(emprunteur.getNUMEROEMP())).thenReturn(true);
        when(emprunteurRepository.save(any(Emprunteur.class))).thenReturn(emprunteur);

        assertEquals(emprunteur, emprunteurService.update(emprunteur.getNUMEROEMP(), emprunteur));
    }

    @Test
    void testUpdateError(){
        Emprunteur emprunteur = new Emprunteur(5L , "nom", "prenom", "mail");
        emprunteur.setNUMEROEMP(3L);
        when(emprunteurRepository.exists(Example.of(emprunteur))).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> emprunteurService.update(emprunteur.getNUMEROEMP(), emprunteur));
    }

    @Test
    void testDelete(){
        Emprunteur toDelete = emprunteurs.get(0);
        when(emprunteurRepository.existsById(toDelete.getNUMEROEMP())).thenReturn(true);
        emprunteurService.delete(toDelete.getNUMEROEMP());
        verify(emprunteurRepository).deleteById(toDelete.getNUMEROEMP());
    }

    @Test
    void testDeleteError(){
        Emprunteur toDelete = new Emprunteur(51L , "nom", "prenom", "mail");
        doThrow(ResourceNotFoundException.class).when(emprunteurRepository).deleteById(any());

        assertThrows(ResourceNotFoundException.class, () -> emprunteurService.delete(toDelete.getNUMEROEMP()));
    }
    
}
