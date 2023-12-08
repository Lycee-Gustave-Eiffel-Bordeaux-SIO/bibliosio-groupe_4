package bibliosio.back;

import bibliosio.back.revue.Revue;
import bibliosio.back.revue.RevueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class BiblioSIOBackApplication
{
    @Autowired
    private RevueRepository revueRepository;


    public static void main(String[] args)
    {
        SpringApplication.run(BiblioSIOBackApplication.class, args);
    }


    @Bean
    public CommandLineRunner setUpBDD() 
    {
        return (args) ->
        {
            List<Revue> revues = new ArrayList<>()
            {{
                add(new Revue(1L, "Machin"));
                add(new Revue(2L, "Chose"));
                add(new Revue(3L, "Truc"));
                add(new Revue(14L, "higher"));
                add(new Revue(7L, "lower"));
                add(new Revue(28L, "way higher"));
            }};

            revueRepository.saveAll(revues);

        };


    }

}
