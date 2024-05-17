package geiffel.da4.bibliosio.emprunt;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmpruntService {

    List<Emprunt> getAll();

    Emprunt getById(Long id);

    Emprunt create(Emprunt newEmp) throws ResourceAlreadyExistsException;

    Emprunt update(Long id, Emprunt updatedEmp) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}
