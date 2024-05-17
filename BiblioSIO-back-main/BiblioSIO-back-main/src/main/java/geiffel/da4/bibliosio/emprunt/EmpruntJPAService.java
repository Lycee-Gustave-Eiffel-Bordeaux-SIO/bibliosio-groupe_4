package geiffel.da4.bibliosio.emprunt;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class EmpruntJPAService implements EmpruntService{

    @Autowired
    private EmpruntRepository empruntRepository;

    public EmpruntJPAService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    @Override
    public List<Emprunt> getAll() {
        return empruntRepository.findAll();
    }

    @Override
    public Emprunt getById(Long id) {
        Optional<Emprunt> emprunt=empruntRepository.findById(id);
        if(emprunt.isPresent()){
            return emprunt.get();
        }else{
            throw new ResourceNotFoundException("Emprunt",id);
        }
    }

    @Override
    public Emprunt create(Emprunt newEmp) throws ResourceAlreadyExistsException {
        if(empruntRepository.existsById(newEmp.getId())){
            throw new ResourceAlreadyExistsException("Emprunt",newEmp.getId());
        }
        else {
            return empruntRepository.save(newEmp);
        }
    }

    @Override
    public Emprunt update(Long id, Emprunt updatedEmp) throws ResourceNotFoundException {
        if(!empruntRepository.existsById(id)){
            throw new ResourceNotFoundException("Emprunt",id);
        }
        else {
            return empruntRepository.save(updatedEmp);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        empruntRepository.deleteById(id);
    }
}
