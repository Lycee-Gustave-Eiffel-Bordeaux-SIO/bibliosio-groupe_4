package geiffel.da4.bibliosio.emprunteur;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Emprunteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomEmprunteur;

    private String prenomEmprunteur;

    private String mailEmprunteur;

    public Emprunteur(){}

    public Emprunteur(Long id, String nomEmprunteur, String prenomEmprunteur, String mailEmprunteur) {
        this.id = id;
        this.nomEmprunteur = nomEmprunteur;
        this.prenomEmprunteur = prenomEmprunteur;
        this.mailEmprunteur = mailEmprunteur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEmprunteur() {
        return nomEmprunteur;
    }

    public void setNomEmprunteur(String nomEmprunteur) {
        this.nomEmprunteur = nomEmprunteur;
    }

    public String getPrenomEmprunteur() {
        return prenomEmprunteur;
    }

    public void setPrenomEmprunteur(String prenomEmprunteur) {
        this.prenomEmprunteur = prenomEmprunteur;
    }

    public String getMailEmprunteur() {
        return mailEmprunteur;
    }

    public void setMailEmprunteur(String mailEmprunteur) {
        this.mailEmprunteur = mailEmprunteur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunteur that = (Emprunteur) o;
        return Objects.equals(id, that.id) && Objects.equals(nomEmprunteur, that.nomEmprunteur) && Objects.equals(prenomEmprunteur, that.prenomEmprunteur) && Objects.equals(mailEmprunteur, that.mailEmprunteur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomEmprunteur, prenomEmprunteur, mailEmprunteur);
    }
}
