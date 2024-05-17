package geiffel.da4.bibliosio.revue;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RevueService 
{
    List<Revue> getAll();

    Revue getById(Long id) throws ResourceNotFoundException;

    Revue create(Revue newRevue) throws ResourceAlreadyExistsException;

    Revue update(Long id, Revue newRevue) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
