package geiffel.da4.bibliosio.revue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevueRepository extends JpaRepository<Revue, Long>
{


}

