package geiffel.da4.bibliosio.emprunt;

import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import jakarta.persistence.*;

import java.sql.Date;
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

    private Date dateEcheance;

    public Emprunt() {}

    public Emprunt(Long id, String dateDebut, String dateRetour, String statut, Emprunteur emprunteur, Exemplaire exemplaire, Date dateEcheance) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateRetour = dateRetour;
        this.statut = statut;
        this.emprunteur = emprunteur;
        this.exemplaire = exemplaire;
        this.dateEcheance = dateEcheance;
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

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(id, emprunt.id) && Objects.equals(dateDebut, emprunt.dateDebut) && Objects.equals(dateRetour, emprunt.dateRetour) && Objects.equals(statut, emprunt.statut) && Objects.equals(emprunteur, emprunt.emprunteur) && Objects.equals(exemplaire, emprunt.exemplaire) && Objects.equals(dateEcheance, emprunt.dateEcheance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateDebut, dateRetour, statut, emprunteur, exemplaire, dateEcheance);
    }
}
