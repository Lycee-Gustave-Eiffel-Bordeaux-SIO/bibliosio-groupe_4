package bibliosio.back.exemplaire;

import bibliosio.back.exceptions.ResourceAlreadyExistsException;
import bibliosio.back.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExemplaireService
{

    List<Exemplaire> getAll();

    Exemplaire getById(Long id);

    Exemplaire create(Exemplaire newEx) throws ResourceAlreadyExistsException;

    Exemplaire update(Long id, Exemplaire updatedEx) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
