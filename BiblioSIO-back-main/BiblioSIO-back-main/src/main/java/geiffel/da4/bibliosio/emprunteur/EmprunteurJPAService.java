package geiffel.da4.bibliosio.emprunteur;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class EmprunteurJPAService implements EmprunteurService{

    @Autowired
    private EmprunteurRepository emprunteurRepository;

    public EmprunteurJPAService(EmprunteurRepository emprunteurRepository) {
        this.emprunteurRepository=emprunteurRepository;
    }

    @Override
    public List<Emprunteur> getAll() {
        return emprunteurRepository.findAll();
    }

    @Override
    public Emprunteur getById(Long id) {
        Optional<Emprunteur> emprunteur=emprunteurRepository.findById(id);
        if(emprunteur.isPresent()){
            return emprunteur.get();
        }else{
            throw new ResourceNotFoundException("Emprunteur",id);
        }
    }

    @Override
    public Emprunteur create(Emprunteur newEmp) throws ResourceAlreadyExistsException {
        if(emprunteurRepository.existsById(newEmp.getId())){
            throw new ResourceAlreadyExistsException("Emprunteur",newEmp.getId());
        }
        else {
            return emprunteurRepository.save(newEmp);
        }
    }

    @Override
    public Emprunteur update(Long id, Emprunteur updatedEmp) throws ResourceNotFoundException {
        if(!emprunteurRepository.existsById(id)){
            throw new ResourceNotFoundException("Emprunteur",id);
        }
        else {
            return emprunteurRepository.save(updatedEmp);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        emprunteurRepository.deleteById(id);
    }
}
