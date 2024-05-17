package geiffel.da4.bibliosio.exemplaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/exemplaires")
@CrossOrigin(origins = "*")
public class ExemplaireController {

    private final ExemplaireService exemplaireService;

    @Autowired
    public ExemplaireController(@Qualifier("jpaExemplaires") ExemplaireService exemplaireService) {
        this.exemplaireService=exemplaireService;
    }

    @GetMapping("")
    public List<Exemplaire> getAll() {
        return exemplaireService.getAll();
    }

    @GetMapping("/{id}")
    public Exemplaire getById(@PathVariable Long id) {
        return exemplaireService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createExemplaire(@RequestBody Exemplaire exemplaire) {
        Exemplaire created_exemplaire = exemplaireService.create(exemplaire);
        return ResponseEntity.created(URI.create("/exemplaires/"+created_exemplaire.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateExemplaire(@PathVariable Long id, @RequestBody Exemplaire exemplaire) {
        exemplaireService.update(id, exemplaire);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteExemplaire(@PathVariable Long id) {
        exemplaireService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
