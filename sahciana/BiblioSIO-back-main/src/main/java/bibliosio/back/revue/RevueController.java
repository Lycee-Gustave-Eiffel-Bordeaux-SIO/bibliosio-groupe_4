package bibliosio.back.revue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/revues")
public class RevueController
{
    @Autowired
    private RevueService revueService;

    @Autowired
    public RevueController(@Qualifier("jpaRevues") RevueService revueService)
    {
        this.revueService=revueService;
    }

    @GetMapping("")
    public List<Revue> getAll()
    {
        return revueService.getAll();
    }


}
