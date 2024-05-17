package geiffel.da4.bibliosio.emprunteur;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmprunteurService {

    List<Emprunteur> getAll();

    Emprunteur getById(Long id);

    Emprunteur create(Emprunteur newEmp) throws ResourceAlreadyExistsException;

    Emprunteur update(Long id, Emprunteur updatedEmp) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
