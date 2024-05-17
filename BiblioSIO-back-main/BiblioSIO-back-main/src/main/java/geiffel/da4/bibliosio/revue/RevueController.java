package geiffel.da4.bibliosio.revue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/revues")
@CrossOrigin(origins = "*")
public class RevueController
{
    @Autowired
    private RevueService revueService;

    @Autowired
    public RevueController(@Qualifier("jpaRevue") RevueService revueService)
    {
        this.revueService=revueService;
    }

    @GetMapping("")
    public List<Revue> getAll()
    {
        return revueService.getAll();
    }

    @GetMapping("/{id}")
    public Revue getById(@PathVariable Long id)
    {
        return revueService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createRevue(@RequestBody Revue revue)
    {
        Revue createdRevue = revueService.create(revue);
        return ResponseEntity.created(URI.create("/revues/"+createdRevue.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRevue(@PathVariable Long id, @RequestBody Revue revue)
    {
        revueService.update(id, revue);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRevue(@PathVariable Long id)
    {
        revueService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
