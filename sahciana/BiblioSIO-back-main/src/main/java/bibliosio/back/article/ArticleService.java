package bibliosio.back.article;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService 
{
    List<Article> getAll();

    Article getById(Long id);

    Article create(Article newArticle);

    Article update(Long id, Article newArticle);

    void delete(Long id);
    
}