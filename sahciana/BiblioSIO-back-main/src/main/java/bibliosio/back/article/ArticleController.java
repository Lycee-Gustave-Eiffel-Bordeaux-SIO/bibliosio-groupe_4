package bibliosio.back.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController 
{
    @Autowired
    private ArticleService articleService;

    @Autowired
    public ArticleController(@Qualifier("jpaArticles") ArticleService articleService)
    {
        this.articleService=articleService;
    }

    @GetMapping("")
    public List<Article> getAll()
    {
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public Article getById(@PathVariable Long id)
    {
        return articleService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createArticle(@RequestBody Article article)
    {
        Article createdArticle = articleService.create(article);
        return ResponseEntity.created(URI.create("/articles/"+createdArticle.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateArticle(@PathVariable Long id, @RequestBody Article article)
    {
        articleService.update(id, article);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable Long id)
    {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
