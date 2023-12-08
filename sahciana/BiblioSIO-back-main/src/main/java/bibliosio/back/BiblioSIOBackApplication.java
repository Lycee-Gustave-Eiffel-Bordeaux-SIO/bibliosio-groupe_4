package bibliosio.back;

import bibliosio.back.article.Article;
import bibliosio.back.article.ArticleRepository;
import bibliosio.back.exemplaire.Exemplaire;
import bibliosio.back.exemplaire.ExemplaireRepository;
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

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private ArticleRepository articleRepository;

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
                add(new Revue(1L, "Titre1"));
                add(new Revue(2L, "Titre1"));
                add(new Revue(3L, "Titre1"));
            }};

            revueRepository.saveAll(revues);

            List<Exemplaire> exemplaires = new ArrayList<>()
            {{
                add(new Exemplaire(1L,"titre1", "Décembre", "disponible", revues.get(0)));
                add(new Exemplaire(2L,"titre2", "Janvier", "disponible", revues.get(1)));
                add(new Exemplaire(3L,"titre3", "Février", "disponible", revues.get(2)));
            }};

            exemplaireRepository.saveAll(exemplaires);

            List<Article> articles = new ArrayList<>()
            {{
                add(new Article(1L, "titre1", "description1", revues.get(0),exemplaires.get(0)));
                add(new Article(2L, "titre2", "description2", revues.get(1),exemplaires.get(1)));
                add(new Article(3L, "titre3", "description3", revues.get(2),exemplaires.get(2)));
            }};

            articleRepository.saveAll(articles);
        };
    }
}
