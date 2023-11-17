package revue;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import utils.LocalService;
import java.util.List;

@Service
public class RevueLocalService extends LocalService<Revue, Long> implements RevueService {

    public RevueLocalService() { super(); }

    public RevueLocalService(List<Revue> revues)
    {
        super(revues);
    }

    @Override
    protected String getIdentifier()
    {
        return "id";
    }

    @Override
    public List<Revue> getAll()
    {
        return super.getAll();
    }

    @Override
    public Revue getById(Long id) {
        return this.getByIdentifier(id);
    }

    public IndexAndValue<Revue> findById(Long id) {
        return super.findByProperty(id);
    }

    @Override
    public Revue create(Revue newRevue) throws ResourceAlreadyExistsException {
        try {
            this.findById(newRevue.getId());
            throw new ResourceAlreadyExistsException("Revue", newRevue.getId());
        } catch (ResourceNotFoundException e) {
            this.allValues.add(newRevue);
            return newRevue;
        }
    }

    @Override
    public void update(Long id, Revue newRevue) throws ResourceNotFoundException
    {
        IndexAndValue<Revue> found = this.findById(id);
        this.allValues.remove(found.index());
        this.allValues.add(found.index(), newRevue);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        IndexAndValue<Revue> found = this.findById(id);
        this.allValues.remove(found.value());
    }


}
