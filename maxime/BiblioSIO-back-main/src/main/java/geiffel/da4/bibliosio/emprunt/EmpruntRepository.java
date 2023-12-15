package geiffel.da4.bibliosio.emprunt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

}
