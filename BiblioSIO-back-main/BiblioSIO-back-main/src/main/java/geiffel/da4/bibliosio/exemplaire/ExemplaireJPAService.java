package geiffel.da4.bibliosio.exemplaire;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class ExemplaireJPAService implements ExemplaireService{

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public ExemplaireJPAService(ExemplaireRepository exemplaireRepository) {
        this.exemplaireRepository=exemplaireRepository;
    }
    @Override
    public List<Exemplaire> getAll() {
        return exemplaireRepository.findAll();
    }

    @Override
    public Exemplaire getById(Long id) {
        Optional<Exemplaire> exemplaire=exemplaireRepository.findById(id);
        if(exemplaire.isPresent()){
            return exemplaire.get();
        }else{
            throw new ResourceNotFoundException("Exemplaire",id);
        }
    }

    @Override
    public Exemplaire create(Exemplaire newEx) throws ResourceAlreadyExistsException {
        if(exemplaireRepository.existsById(newEx.getId())){
            throw new ResourceAlreadyExistsException("Exemplaire",newEx.getId());
        }
        else {
            return exemplaireRepository.save(newEx);
        }
    }

    @Override
    public Exemplaire update(Long id, Exemplaire updatedEx) throws ResourceNotFoundException {
        if(!exemplaireRepository.existsById(id)){
            throw new ResourceNotFoundException("Exemplaire",id);
        }
        else {
            return exemplaireRepository.save(updatedEx);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        exemplaireRepository.deleteById(id);
    }
}
