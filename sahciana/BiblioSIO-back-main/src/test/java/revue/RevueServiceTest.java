package revue;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RevueServiceTest 
{
    private RevueService revueService;

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
        revueService = new RevueLocalService(revues);
    }

    @Test
    void whenGettingAll_shouldReturn3()
    {
        assertEquals(3, revueService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame()
    {
        assertAll(
                () -> assertEquals(revues.get(0), revueService.getById(1L)),
                () -> assertEquals(revues.get(2), revueService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueService.getById(4L))
        );
    }


    @Test
    void whenCreating_ShouldReturnSame() {
        Revue newRevue = new Revue(5L, "titre5");

        assertEquals(newRevue, revueService.create(newRevue));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty() {
        Revue same_revue = revues.get(0);

        assertThrows(ResourceAlreadyExistsException.class, ()->revueService.create(same_revue));
    }

    @Test
    void whenUpdating_shouldModifyRevue() {
        Revue initial_revue = revues.get(2);
        Revue new_revue = new Revue(initial_revue.getId(), "Titre updaté");

        revueService.update(new_revue.getId(), new_revue);
        Revue updated_revue = revueService.getById(initial_revue.getId());
        assertEquals(new_revue, updated_revue);
        assertTrue(revueService.getAll().contains(new_revue));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Revue revue = revues.get(2);

        assertThrows(ResourceNotFoundException.class, ()->revueService.update(75L, revue));
    }

    @Test
    void whenDeletingExistingRevue_shouldNotBeInRevuesAnymore() {
        Revue revue = revues.get(1);
        Long id = revue.getId();

        revueService.delete(id);
        assertFalse(revueService.getAll().contains(revue));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;

        assertThrows(ResourceNotFoundException.class, ()->revueService.delete(id));
    }
    
}
