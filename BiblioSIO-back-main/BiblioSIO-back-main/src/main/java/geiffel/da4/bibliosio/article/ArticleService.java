package geiffel.da4.bibliosio.article;


import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService 
{
    List<Article> getAll();

    Article getById(Long id) throws ResourceNotFoundException;

    Article create(Article newArticle) throws ResourceAlreadyExistsException;

    Article update(Long id, Article newArticle) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
    
}