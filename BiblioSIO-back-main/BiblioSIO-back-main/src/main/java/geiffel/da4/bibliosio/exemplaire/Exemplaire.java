package geiffel.da4.bibliosio.exemplaire;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import geiffel.da4.bibliosio.revue.Revue;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String dateparution;

    private String statut;

    @ManyToOne
    private Revue revue;

    public Exemplaire() {}

    public Exemplaire(Long id, String titre, String dateparution, String statut, Revue revue) {
        this.id = id;
        this.titre = titre;
        this.dateparution = dateparution;
        this.statut = statut;
        this.revue = revue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateparution() {
        return dateparution;
    }

    public void setDateparution(String dateparution) {
        this.dateparution = dateparution;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Revue getRevue() {
        return revue;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return Objects.equals(id, that.id) && Objects.equals(titre, that.titre) && Objects.equals(dateparution, that.dateparution) && Objects.equals(statut, that.statut) && Objects.equals(revue, that.revue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, dateparution, statut, revue);
    }
}
