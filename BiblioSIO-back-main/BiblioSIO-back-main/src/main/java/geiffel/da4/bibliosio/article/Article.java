package geiffel.da4.bibliosio.article;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import geiffel.da4.bibliosio.exemplaire.ExemplaireJSONSerializer;
import geiffel.da4.bibliosio.revue.Revue;
import geiffel.da4.bibliosio.revue.RevueEmbeddedJSONSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Article
{
    // Attributs

    @Id
    private Long id;

    private String titre;

    private String description;

    @ManyToOne
    @JsonSerialize(using = RevueEmbeddedJSONSerializer.class)
    private Revue revue;

    @ManyToOne
    @JsonSerialize(using = ExemplaireJSONSerializer.class)
    private Exemplaire exemplaire;

    // Constructeurs


    public Article() {
    }

    public Article(Long id, String titre, String description, Revue revue, Exemplaire exemplaire)
    {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.revue = revue;
        this.exemplaire = exemplaire;
    }



    // Getters - Setters

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Revue getRevue()
    {
        return revue;
    }

    public void setRevue(Revue revue)
    {
        this.revue = revue;
    }

    public Exemplaire getExemplaire()
    {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire)
    {
        this.exemplaire = exemplaire;
    }

    // Méthodes


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
                Objects.equals(titre, article.titre) &&
                Objects.equals(description, article.description) &&
                Objects.equals(revue, article.revue) &&
                Objects.equals(exemplaire, article.exemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, description, revue, exemplaire);
    }
}