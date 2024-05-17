package geiffel.da4.bibliosio.emprunteur;

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

    private String promo;

    private String classe;

    public Emprunteur(){}

    public Emprunteur(Long id, String nomEmprunteur, String prenomEmprunteur, String mailEmprunteur, String promo, String classe) {
        this.id = id;
        this.nomEmprunteur = nomEmprunteur;
        this.prenomEmprunteur = prenomEmprunteur;
        this.mailEmprunteur = mailEmprunteur;
        this.promo = promo;
        this.classe = classe;
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

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunteur that = (Emprunteur) o;
        return Objects.equals(id, that.id) && Objects.equals(nomEmprunteur, that.nomEmprunteur) && Objects.equals(prenomEmprunteur, that.prenomEmprunteur) && Objects.equals(mailEmprunteur, that.mailEmprunteur) && Objects.equals(promo, that.promo) && Objects.equals(classe, that.classe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomEmprunteur, prenomEmprunteur, mailEmprunteur, promo, classe);
    }
}
