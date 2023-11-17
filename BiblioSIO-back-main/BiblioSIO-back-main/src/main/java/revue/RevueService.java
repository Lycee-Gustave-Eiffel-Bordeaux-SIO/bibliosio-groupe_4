package revue;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RevueService 
{
    List<Revue> getAll();

    Revue getById(Long id);

    Revue create(Revue newRevue);

    void update(Long id, Revue newRevue);

    void delete(Long id);
}