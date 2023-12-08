package bibliosio.back.revue;

import bibliosio.back.exceptions.ResourceAlreadyExistsException;
import bibliosio.back.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpaRevues")
public class RevueJPAService implements RevueService
{
    @Autowired
    private RevueRepository revueRepository;

    public RevueJPAService(List<Revue> revues)
    {

    }

    @Override
    public List<Revue> getAll()
    {
        return revueRepository.findAll();
    }

    @Override
    public Revue getById(Long id) throws ResourceNotFoundException
    {
        Optional<Revue> revue = revueRepository.findById(id);
        if(revue.isPresent())
        {
            return revue.get();
        }
        else
        {
            throw new ResourceNotFoundException("Revue", id);
        }
    }

    @Override
    public Revue create(Revue newRevue) throws ResourceAlreadyExistsException {
        Long id = newRevue.getId();
        if(revueRepository.existsById(id))
        {
            throw new ResourceAlreadyExistsException("Revue", id);
        }
        else
        {
            return revueRepository.save(newRevue);
        }
    }

    @Override
    public Revue update(Long id, Revue newRevue) throws ResourceNotFoundException
    {
        if(!revueRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Revue", id);
        }
        else
        {
                return revueRepository.save(newRevue);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException
    {
        if(!revueRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Revue", id);
        }
        else
        {
            revueRepository.deleteById(id);
        }
    }


}
