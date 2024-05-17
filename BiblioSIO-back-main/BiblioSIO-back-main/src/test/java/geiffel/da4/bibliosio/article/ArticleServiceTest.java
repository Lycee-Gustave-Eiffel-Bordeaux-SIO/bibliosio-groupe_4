package geiffel.da4.bibliosio.article;


import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import geiffel.da4.bibliosio.revue.Revue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ArticleJPAService.class)
@SpringBootTest
public class ArticleServiceTest 
{
    @Autowired
    private ArticleJPAService articleJPAService;

    @MockBean
    private ArticleRepository articleRepository;

    private List<Article> articles;

    private List<Revue> revues;

    private List<Exemplaire> exemplaires;

    @BeforeEach
    void setUp()
    {
        revues = new ArrayList<>()
        {{
            add(new Revue(1L, "Titre1"));
            add(new Revue(2L, "Titre1"));
            add(new Revue(3L, "Titre1"));
        }};

        exemplaires = new ArrayList<>()
        {{
            add(new Exemplaire(1L,"titre1", "Décembre", "disponible", revues.get(0)));
            add(new Exemplaire(2L,"titre2", "Janvier", "disponible", revues.get(1)));
            add(new Exemplaire(3L,"titre3", "Février", "disponible", revues.get(2)));
        }};

        articles = new ArrayList<>()
        {{
            add(new Article(1L, "titre1", "description1", revues.get(0),exemplaires.get(0)));
            add(new Article(2L, "titre2", "description2", revues.get(1),exemplaires.get(1)));
            add(new Article(3L, "titre3", "description3", revues.get(2),exemplaires.get(2)));
        }};

        Article article1 = articles.get(0);
        Article article3 = articles.get(2);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article1));
        when(articleRepository.findById(3L)).thenReturn(Optional.of(article3));
    }

    @Test
    void whenGettingAll_shouldReturn3()
    {
        when(articleRepository.findAll()).thenReturn(articles);
        assertEquals(3, articleJPAService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame()
    {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(articles.get(0)));
        when(articleRepository.findById(3L)).thenReturn(Optional.of(articles.get(2)));
        when(articleRepository.findById(12L)).thenReturn(Optional.empty());
        when(articleRepository.findById(4L)).thenReturn(Optional.empty());

        assertAll(
                () -> assertEquals(articles.get(0), articleJPAService.getById(1L)),
                () -> assertEquals(articles.get(2), articleJPAService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> articleJPAService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> articleJPAService.getById(4L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame()
    {
        Article newArticle = new Article(4L, "titre4", "description4", revues.get(0),exemplaires.get(1));

        when(articleRepository.save(any(Article.class))).thenReturn(newArticle);

        assertEquals(newArticle, articleJPAService.create(newArticle));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty()
    {
        Article same_article = articles.get(0);

        doThrow(ResourceAlreadyExistsException.class).when(articleRepository).existsById(same_article.getId());

        assertThrows(ResourceAlreadyExistsException.class, ()->articleJPAService.create(same_article));
    }

    @Test
    void whenUpdating_shouldModifyArticle()
    {
        Article article = articles.get(0);
        article.setTitre("nouveauTitre");

        when(articleRepository.existsById(article.getId())).thenReturn(true);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        assertEquals(article, articleJPAService.update(article.getId(), article));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException()
    {
        Article article = articles.get(2);

        when(articleRepository.exists(Example.of(article))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->articleJPAService.update(75L, article));
    }

    @Test
    void whenDeletingExistingArticle_shouldNotBeInArticlesAnymore()
    {
        Article article = articles.get(1);
        Long id = article.getId();

        when(articleRepository.existsById(article.getId())).thenReturn(true);

        articleJPAService.delete(id);

        assertFalse(articleJPAService.getAll().contains(article));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException()
    {
        Long id = 68L;

        doThrow(ResourceNotFoundException.class).when(articleRepository).delete(any());

        assertThrows(ResourceNotFoundException.class, ()->articleJPAService.delete(id));
    }
}