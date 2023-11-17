package revue;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RevueJPAService implements RevueService
{
    private final RevueRepository revueRepository;

    public RevueJPAService(RevueRepository revueRepository)
    {
        this.revueRepository = revueRepository;
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
    public void update(Long id, Revue newRevue) throws ResourceNotFoundException
    {
        if(!revueRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Revue", id);
        }
        else
        {
            revueRepository.save(newRevue);
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
