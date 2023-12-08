package bibliosio.back.article;

import bibliosio.back.exceptions.ResourceAlreadyExistsException;
import bibliosio.back.exceptions.ResourceNotFoundException;
import bibliosio.back.article.Article;
import bibliosio.back.article.ArticleJPAService;
import bibliosio.back.article.ArticleRepository;
import bibliosio.back.article.ArticleService;
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
    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;

    private List<Article> articles;

    @BeforeEach
    void setUp()
    {
        articles = new ArrayList<>()
        {{
            add(new Article());
            add(new Article());
            add(new Article());
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
        assertEquals(3, articleService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame()
    {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(articles.get(0)));
        when(articleRepository.findById(3L)).thenReturn(Optional.of(articles.get(2)));
        when(articleRepository.findById(12L)).thenReturn(Optional.empty());
        when(articleRepository.findById(4L)).thenReturn(Optional.empty());

        assertAll(
                () -> assertEquals(articles.get(0), articleService.getById(1L)),
                () -> assertEquals(articles.get(2), articleService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> articleService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> articleService.getById(4L))
        );
    }


    @Test
    void whenCreating_ShouldReturnSame()
    {
        Article newArticle = new Article();

        when(articleRepository.save(any(Article.class))).thenReturn(newArticle);

        assertEquals(newArticle, articleService.create(newArticle));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty()
    {
        Article same_article = articles.get(0);

        doThrow(ResourceAlreadyExistsException.class).when(articleRepository).existsById(same_article.getId());

        assertThrows(ResourceAlreadyExistsException.class, ()->articleService.create(same_article));
    }

    @Test
    void whenUpdating_shouldModifyArticle()
    {
        Article article = articles.get(0);
        article.setTitre("nouveauTitre");

        when(articleRepository.existsById(article.getId())).thenReturn(true);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        assertEquals(article, articleService.update(article.getId(), article));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException()
    {
        Article article = articles.get(2);

        when(articleRepository.exists(Example.of(article))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->articleService.update(75L, article));
    }

    @Test
    void whenDeletingExistingArticle_shouldNotBeInArticlesAnymore()
    {
        Article article = articles.get(1);
        Long id = article.getId();

        when(articleRepository.existsById(article.getId())).thenReturn(true);

        articleService.delete(id);

        assertFalse(articleService.getAll().contains(article));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException()
    {
        Long id = 68L;

        doThrow(ResourceNotFoundException.class).when(articleRepository).delete(any());

        assertThrows(ResourceNotFoundException.class, ()->articleService.delete(id));
    }


}