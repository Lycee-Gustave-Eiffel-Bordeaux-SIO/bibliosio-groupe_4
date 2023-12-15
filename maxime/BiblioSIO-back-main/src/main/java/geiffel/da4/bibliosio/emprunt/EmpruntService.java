package geiffel.da4.bibliosio.emprunt;

import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;

import java.util.List;

public interface EmpruntService {

    List<Emprunt> getAll();

    Emprunt getById(Long id);

    Emprunt create(Emprunt newEmp) throws ResourceAlreadyExistsException;

    Emprunt update(Long id, Emprunt updatedEmp) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}
