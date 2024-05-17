package geiffel.da4.bibliosio;

import geiffel.da4.bibliosio.article.Article;
import geiffel.da4.bibliosio.article.ArticleRepository;
import geiffel.da4.bibliosio.emprunt.Emprunt;
import geiffel.da4.bibliosio.emprunt.EmpruntRepository;
import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import geiffel.da4.bibliosio.emprunteur.EmprunteurRepository;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import geiffel.da4.bibliosio.exemplaire.ExemplaireRepository;
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

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private EmprunteurRepository emprunteurRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

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

            List<Emprunteur> emprunteurs = new ArrayList<>(){{
                add(new Emprunteur(1L, "nom1", "prénom1", "mail1"));
                add(new Emprunteur(2L, "nom2", "prénom2", "mail2"));
                add(new Emprunteur(3L, "nom3", "prénom3", "mail3"));
            }};
            emprunteurRepository.saveAll(emprunteurs);

            List<Exemplaire> exemplaires = new ArrayList<>(){{
                add(new Exemplaire(1L, "titre1", null, null, revues.get(0)));
                add(new Exemplaire(2L, "titre2", null, null, revues.get(1)));
                add(new Exemplaire(3L, "titre3", null, null, revues.get(2)));
            }};
            exemplaireRepository.saveAll(exemplaires);

            List<Article> articles = new ArrayList<>(){{
                add(new Article(1L, "Article1", "desc1", revues.get(0), exemplaires.get(0)));
                add(new Article(2L, "Article2", "desc2", revues.get(1), exemplaires.get(1)));
                add(new Article(3L, "Article3", "desc3", revues.get(2), exemplaires.get(2)));
            }};
            articleRepository.saveAll(articles);

            List<Emprunt> emprunts = new ArrayList<>(){{
                add(new Emprunt(1L, null, null, null, emprunteurs.get(0), exemplaires.get(0)));
                add(new Emprunt(2L, null, null, null, emprunteurs.get(1), exemplaires.get(1)));
                add(new Emprunt(3L, null, null, null, emprunteurs.get(2), exemplaires.get(2)));
            }};
            empruntRepository.saveAll(emprunts);

        };
    }

}
