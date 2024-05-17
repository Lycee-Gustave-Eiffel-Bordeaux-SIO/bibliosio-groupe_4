package geiffel.da4.bibliosio.revue;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = RevueJPAService.class)
@SpringBootTest
public class RevueServiceTest 
{
    @Autowired
    private RevueJPAService revueJPAService;

    @MockBean
    private RevueRepository revueRepository;

    private List<Revue> revues;

    @BeforeEach
    void setUp()
    {
         revues = new ArrayList<>()
        {{
            add(new RevueBuilder().build());
            add(new RevueBuilder().withId(2L).build());
            add(new RevueBuilder().withId(3L).build());
        }};
    }

    @Test
    void whenGettingAll_shouldReturn3()
    {
        when(revueRepository.findAll()).thenReturn(revues);

        assertEquals(3, revueJPAService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        when(revueRepository.findById(1L)).thenReturn(Optional.of(revues.get(0)));
        when(revueRepository.findById(3L)).thenReturn(Optional.of(revues.get(2)));
        when(revueRepository.findById(12L)).thenReturn(Optional.empty());
        when(revueRepository.findById(4L)).thenReturn(Optional.empty());

        assertAll(
                () -> assertEquals(revues.get(0), revueJPAService.getById(1L)),
                () -> assertEquals(revues.get(2), revueJPAService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueJPAService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueJPAService.getById(4L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame() {
        Revue newRevue = new RevueBuilder().withId(4L).build();

        when(revueRepository.save(any(Revue.class))).thenReturn(newRevue);

        assertEquals(newRevue, revueJPAService.create(newRevue));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty() {
        Revue same_revue = revues.get(0);

        doThrow(ResourceAlreadyExistsException.class).when(revueRepository).existsById(same_revue.getId());

        assertThrows(ResourceAlreadyExistsException.class, ()->revueJPAService.create(same_revue));
    }

    @Test
    void whenUpdating_shouldModifyRevue() {
        Revue revue = revues.get(0);
        revue.setTitre("nouveauTitre");

        when(revueRepository.findAll()).thenReturn(revues);
        when(revueRepository.existsById(revue.getId())).thenReturn(true);
        when(revueRepository.save(any(Revue.class))).thenReturn(revue);

        Revue updatedRevue = revueJPAService.update(revue.getId(), revue);

        assertEquals(revue, updatedRevue);
        assertTrue(revueJPAService.getAll().contains(updatedRevue));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Revue revue = revues.get(2);

        when(revueRepository.exists(Example.of(revue))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->revueJPAService.update(75L, revue));
    }

    @Test
    void whenDeletingExistingRevue_shouldNotBeInArticlesAnymore() {
        Revue revue = revues.get(1);
        Long id = revue.getId();

        Mockito.reset(revueRepository);
        when(revueRepository.existsById(revue.getId())).thenReturn(true);

        revueJPAService.delete(id);

        assertFalse(revueJPAService.getAll().contains(revue));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;

        doThrow(ResourceNotFoundException.class).when(revueRepository).delete(any());

        assertThrows(ResourceNotFoundException.class, ()->revueJPAService.delete(id));
    }
}