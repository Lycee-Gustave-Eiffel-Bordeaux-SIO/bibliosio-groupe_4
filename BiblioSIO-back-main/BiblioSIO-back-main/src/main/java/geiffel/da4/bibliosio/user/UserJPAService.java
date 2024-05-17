package geiffel.da4.bibliosio.user;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpaUsers")
public class UserJPAService implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserJPAService(List<User> users)
    {

    }

    @Override
    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) throws ResourceNotFoundException
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        {
            return user.get();
        }
        else
        {
            throw new ResourceNotFoundException("User", id);
        }
    }

    @Override
    public User create(User user) throws ResourceAlreadyExistsException
    {
        Long id = user.getId();
        if(userRepository.existsById(id))
        {
            throw new ResourceAlreadyExistsException("User", id);
        }
        else
        {
            return userRepository.save(user);
        }
    }

    @Override
    public User update(Long id, User user) throws ResourceNotFoundException
    {
        if(!userRepository.existsById(id))
        {
            throw new ResourceNotFoundException("User", id);
        }
        else
        {
            return userRepository.save(user);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException
    {
        if(!userRepository.existsById(id))
        {
            throw new ResourceNotFoundException("User", id);
        }
        else
        {
            userRepository.deleteById(id);
        }
    }
}
