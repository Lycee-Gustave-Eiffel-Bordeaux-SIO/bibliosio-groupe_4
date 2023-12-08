package bibliosio.back.article;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Article
{
    // Attributs

    @Id
    private Long id;

    private String titre;

    private String description;



    // Constructeurs


}