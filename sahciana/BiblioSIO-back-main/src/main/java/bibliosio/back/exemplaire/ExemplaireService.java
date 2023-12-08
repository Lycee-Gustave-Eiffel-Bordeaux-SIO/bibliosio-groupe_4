package geiffel.da4.bibliosio.exemplaire;

import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ExemplaireService {

    List<Exemplaire> getAll();

    Exemplaire getById(Long id);

    Exemplaire create(Exemplaire newEx) throws ResourceAlreadyExistsException;

    Exemplaire update(Long id, Exemplaire updatedEx) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
