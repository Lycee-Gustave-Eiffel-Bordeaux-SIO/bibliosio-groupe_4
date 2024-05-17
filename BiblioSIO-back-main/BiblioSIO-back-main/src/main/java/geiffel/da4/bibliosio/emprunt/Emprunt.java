package geiffel.da4.bibliosio.emprunt;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dateDebut;

    private String dateRetour;

    private String statut;

    @ManyToOne
    private Emprunteur emprunteur;

    @ManyToOne
    private Exemplaire exemplaire;

    public Emprunt() {}

    public Emprunt(Long id, String dateDebut, String dateRetour, String statut, Emprunteur emprunteur, Exemplaire exemplaire) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateRetour = dateRetour;
        this.statut = statut;
        this.emprunteur = emprunteur;
        this.exemplaire = exemplaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Emprunteur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Emprunteur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(id, emprunt.id) && Objects.equals(dateDebut, emprunt.dateDebut) && Objects.equals(dateRetour, emprunt.dateRetour) && Objects.equals(statut, emprunt.statut) && Objects.equals(emprunteur, emprunt.emprunteur) && Objects.equals(exemplaire, emprunt.exemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateDebut, dateRetour, statut, emprunteur, exemplaire);
    }
}
