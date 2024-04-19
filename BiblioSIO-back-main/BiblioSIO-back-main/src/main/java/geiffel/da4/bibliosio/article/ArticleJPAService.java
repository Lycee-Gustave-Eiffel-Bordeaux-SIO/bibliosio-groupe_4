package geiffel.da4.bibliosio.article;


import geiffel.da4.bibliosio.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.bibliosio.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpaArticles")
public class ArticleJPAService implements ArticleService
{
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleJPAService(List<Article> articles)
    {

    }

    @Override
    public List<Article> getAll()
    {
        return articleRepository.findAll();
    }

    @Override
    public Article getById(Long id) throws ResourceNotFoundException
    {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isPresent())
        {
            return article.get();
        }
        else
        {
            throw new ResourceNotFoundException("Article", id);
        }
    }

    @Override
    public Article create(Article newArticle) throws ResourceAlreadyExistsException
    {
        Long id = newArticle.getId();
        if(articleRepository.existsById(id))
        {
            throw new ResourceAlreadyExistsException("Article", id);
        }
        else
        {
            return articleRepository.save(newArticle);
        }
    }

    @Override
    public Article update(Long id, Article newArticle) throws ResourceNotFoundException
    {
        if(!articleRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Article", id);
        }
        else
        {
            return articleRepository.save(newArticle);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException
    {
        if(!articleRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Article", id);
        }
        else
        {
            articleRepository.deleteById(id);
        }
    }
}
