package geiffel.da4.bibliosio.emprunteur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EmprunteurRepository extends JpaRepository<Emprunteur, Long>{

}
