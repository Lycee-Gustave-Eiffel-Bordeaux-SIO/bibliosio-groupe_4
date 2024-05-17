package geiffel.da4.bibliosio.user;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User getById(Long id) throws ResourceNotFoundException;

    User create(User user) throws ResourceAlreadyExistsException;

    User update(Long id, User user) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}
