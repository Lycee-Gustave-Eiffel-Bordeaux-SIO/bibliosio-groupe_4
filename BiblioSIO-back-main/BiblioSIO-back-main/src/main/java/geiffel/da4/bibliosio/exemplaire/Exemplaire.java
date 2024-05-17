package geiffel.da4.bibliosio.exemplaire;

import geiffel.da4.bibliosio.revue.Revue;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String anneeParution;

    private String moisParution;

    private String statut;

    @ManyToOne
    private Revue revue;

    public Exemplaire() {}

    public Exemplaire(Long id, String titre, String anneeParution, String moisParution, String statut, Revue revue) {
        this.id = id;
        this.titre = titre;
        this.anneeParution = anneeParution;
        this.moisParution = moisParution;
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

    public String getAnneeParution() {
        return anneeParution;
    }

    public void setAnneeParution(String anneeParution) {
        this.anneeParution = anneeParution;
    }

    public String getMoisParution() {
        return moisParution;
    }

    public void setMoisParution(String moisParution) {
        this.moisParution = moisParution;
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
        return Objects.equals(id, that.id) && Objects.equals(titre, that.titre) && Objects.equals(anneeParution, that.anneeParution) && Objects.equals(moisParution, that.moisParution) && Objects.equals(statut, that.statut) && Objects.equals(revue, that.revue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, anneeParution, moisParution, statut, revue);
    }
}
