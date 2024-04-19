package geiffel.da4.bibliosio;

import geiffel.da4.bibliosio.article.Article;
import geiffel.da4.bibliosio.article.ArticleRepository;
import geiffel.da4.bibliosio.revue.Revue;
import geiffel.da4.bibliosio.revue.RevueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BiblioSIOBackApplication {

    @Autowired
    private RevueRepository revueRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public static void main(String[] args){
        SpringApplication.run(BiblioSIOBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpBDD()
    {
        return (args) ->
        {
            List<Revue> revues = new ArrayList<>()
            {{
                add(new Revue(1L, "Revue1"));
                add(new Revue(2L, "Revue2"));
                add(new Revue(3L, "Revue3"));
            }};
            revueRepository.saveAll(revues);

            List<Article> articles = new ArrayList<>(){{
                add(new Article(1L, "Article1", null, null, null));
                add(new Article(2L, "Article2", null, null, null));
                add(new Article(3L, "Article3", null, null, null));
            }};
            articleRepository.saveAll(articles);
        };
    }

}
