package geiffel.da4.bibliosio.article;


import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ExceptionHandlingAdvice;
import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import geiffel.da4.bibliosio.revue.Revue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest
@ContextConfiguration(classes = ArticleController.class)
@Import({ExceptionHandlingAdvice.class})
public class ArticleControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean(name="jpaArticles")
    ArticleService articleService;

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
            add(new Exemplaire(1L,"titre1", "2024", "Octobre",null, revues.get(0)));
            add(new Exemplaire(2L,"titre2", null, "Octobre/Décembre",null, revues.get(1)));
            add(new Exemplaire(3L,"titre3", "2019", null,null, revues.get(2)));
        }};

        articles = new ArrayList<>()
        {{
            add(new Article(1L, "titre1", "description1", revues.get(0),exemplaires.get(0)));
            add(new Article(2L, "titre2", "description2", revues.get(1),exemplaires.get(1)));
            add(new Article(3L, "titre3", "description3", revues.get(2),exemplaires.get(2)));
            add(new Article(14L, "titre14", "description14", revues.get(0),exemplaires.get(0)));
            add(new Article(7L, "titre7", "description14", revues.get(1),exemplaires.get(1)));
            add(new Article(28L, "titre28", "description14", revues.get(2),exemplaires.get(2)));
        }};

        when(articleService.getAll()).thenReturn(articles);
        when(articleService.getById(7L)).thenReturn(articles.get(4));
        when(articleService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception
    {
        when(articleService.getAll()).thenReturn(articles);
        assertEquals(6, articleService.getAll().size());

        mockMvc.perform(get("/articles")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception
    {
        Article article = new Article();
        article.setId(7L);
        article.setTitre("titre7");

        String content = "{\"id\":7,\"titre\":\"titre7\",\"description\":\"description14\",\"revue\":{\"titre\":\"Titre1\",\"url\":\"/revues/2\"},\"exemplaire\":{\"titre\":\"titre2\",\"url\":\"/exemplaires/2\"}}";

        assertEquals("titre7", article.getTitre());

        mockMvc.perform(get("/articles/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(content().string(content)
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception
    {
        doThrow(ResourceNotFoundException.class).when(articleService).getById(49L);

        mockMvc.perform(get("/articles/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception
    {
        Article new_article = new Article(89L, "titre89", null, null, null);
        ArgumentCaptor<Article> article_received = ArgumentCaptor.forClass(Article.class);

        when(articleService.create(any())).thenReturn(new_article);

        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_article))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/articles/"+new_article.getId())
        ).andDo(print());

        verify(articleService).create(article_received.capture());
        assertEquals(new_article, article_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should409() throws Exception
    {
        when(articleService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        Article a = mock(Article.class);

        String obj = new ObjectMapper().writeValueAsString(this.articles.get(0));

        mockMvc.perform(post("/articles")
                        .contentType("application/json")
                        .characterEncoding(StandardCharsets.UTF_8)
                .content(obj)
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveArticleToUpdate_andReturnNoContent() throws Exception
    {
        Article initial_article = articles.get(1);
        Article updated_article = new Article(initial_article.getId(), "titre updated", initial_article.getDescription(), initial_article.getRevue(), initial_article.getExemplaire());
        ArgumentCaptor<Article> article_received = ArgumentCaptor.forClass(Article.class);

        mockMvc.perform(put("/articles/"+initial_article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_article))
        ).andExpect(status().isNoContent());

        verify(articleService).update(anyLong(), article_received.capture());
        assertEquals(updated_article, article_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception
    {
        Long id = 28L;

        mockMvc.perform(delete("/articles/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(articleService).delete(id_received.capture());

        assertEquals(id, id_received.getValue());
    }
}