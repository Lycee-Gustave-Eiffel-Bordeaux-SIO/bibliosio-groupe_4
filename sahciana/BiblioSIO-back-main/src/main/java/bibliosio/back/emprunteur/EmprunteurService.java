package bibliosio.back.emprunteur;

import bibliosio.back.exceptions.ResourceAlreadyExistsException;
import bibliosio.back.exceptions.ResourceNotFoundException;

import java.util.List;

public interface EmprunteurService {

    List<Emprunteur> getAll();

    Emprunteur getById(Long id);

    Emprunteur create(Emprunteur newEmp) throws ResourceAlreadyExistsException;

    Emprunteur update(Long id, Emprunteur updatedEmp) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
