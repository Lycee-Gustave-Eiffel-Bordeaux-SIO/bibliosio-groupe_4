package geiffel.da4.bibliosio.revue;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import geiffel.da4.bibliosio.exemplaire.ExemplaireJSONSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Revue
{

    // Attributs

    @Id
    private Long id;
    private String titre;
    @OneToMany(mappedBy = "revue")
    @JsonSerialize(contentUsing = ExemplaireJSONSerializer.class)
    private List<Exemplaire> exemplaires;

    // Constructeurs

    public Revue(Long id, String titre)
    {
        this.id = id;
        this.titre = titre;
        this.exemplaires = new ArrayList<>();
    }

    public Revue() {

    }

    public void addExemplaire(Exemplaire exemplaire){
        this.exemplaires.add(exemplaire);

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

    public List<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }

    // Méthodes

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revue revue = (Revue) o;
        return Objects.equals(id, revue.id) && Objects.equals(titre, revue.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre);
    }
}
