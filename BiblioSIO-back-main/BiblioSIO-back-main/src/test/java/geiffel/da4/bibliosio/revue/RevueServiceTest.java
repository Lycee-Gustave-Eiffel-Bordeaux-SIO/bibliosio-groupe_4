package geiffel.da4.bibliosio.revue;


import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = RevueJPAService.class)
@SpringBootTest
public class RevueServiceTest 
{
    @Autowired
    private RevueService revueService;

    @MockBean
    private RevueRepository revueRepository;

    private List<Revue> revues;

    @BeforeEach
    void setUp()
    {
         revues = new ArrayList<>()
        {{
            add(new Revue(1L, "Titre1"));
            add(new Revue(2L, "Titre1"));
            add(new Revue(3L, "Titre1"));
        }};

        Revue revue1 = revues.get(0);
        Revue revue3 = revues.get(2);
        when(revueRepository.findById(1L)).thenReturn(Optional.of(revue1));
        when(revueRepository.findById(3L)).thenReturn(Optional.of(revue3));
    }

    @Test
    void whenGettingAll_shouldReturn3()
    {
        when(revueRepository.findAll()).thenReturn(revues);
        assertEquals(3, revueService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame()
    {
        when(revueRepository.findById(1L)).thenReturn(Optional.of(revues.get(0)));
        when(revueRepository.findById(3L)).thenReturn(Optional.of(revues.get(2)));
        when(revueRepository.findById(12L)).thenReturn(Optional.empty());
        when(revueRepository.findById(4L)).thenReturn(Optional.empty());

        assertAll(
                () -> assertEquals(revues.get(0), revueService.getById(1L)),
                () -> assertEquals(revues.get(2), revueService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueService.getById(4L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame()
    {
        Revue newRevue = new Revue(5L, "titre5");

        when(revueRepository.save(any(Revue.class))).thenReturn(newRevue);

        assertEquals(newRevue, revueService.create(newRevue));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty()
    {
        Revue same_revue = revues.get(0);

        doThrow(ResourceAlreadyExistsException.class).when(revueRepository).existsById(same_revue.getId());

        assertThrows(ResourceAlreadyExistsException.class, ()->revueService.create(same_revue));
    }

    @Test
    void whenUpdating_shouldModifyRevue()
    {
        Revue revue = revues.get(0);
        revue.setTitre("nouveauTitre");

        when(revueRepository.existsById(revue.getId())).thenReturn(true);
        when(revueRepository.save(any(Revue.class))).thenReturn(revue);

        assertEquals(revue, revueService.update(revue.getId(), revue));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException()
    {
        Revue revue = revues.get(2);

        when(revueRepository.exists(Example.of(revue))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->revueService.update(75L, revue));
    }

    @Test
    void whenDeletingExistingRevue_shouldNotBeInRevuesAnymore()
    {
        Revue revue = revues.get(1);
        Long id = revue.getId();

        when(revueRepository.existsById(revue.getId())).thenReturn(true);

        revueService.delete(id);

        assertFalse(revueService.getAll().contains(revue));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException()
    {
        Long id = 68L;

        doThrow(ResourceNotFoundException.class).when(revueRepository).delete(any());

        assertThrows(ResourceNotFoundException.class, ()->revueService.delete(id));
    }
}