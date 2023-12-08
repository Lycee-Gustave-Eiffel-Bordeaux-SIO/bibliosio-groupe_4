package bibliosio.back.revue;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Revue
{

    // Attributs

    @Id
    private Long id;
    private String titre;

    // Constructeurs

    public Revue(Long id, String titre)
    {
        this.id = id;
        this.titre = titre;
    }

    public Revue()
    {

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

    // Méthodes

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revue revue = (Revue) o;
        return Objects.equals(id, revue.id) && Objects.equals(titre, revue.titre);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, titre);
    }
}
