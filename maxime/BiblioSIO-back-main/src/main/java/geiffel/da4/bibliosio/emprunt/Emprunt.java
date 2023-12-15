package geiffel.da4.bibliosio.emprunt;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import geiffel.da4.bibliosio.emprunteur.Emprunteur;
import geiffel.da4.bibliosio.exemplaire.Exemplaire;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "IDEMPRUNT")
public class Emprunt {

    @Id
    @JsonProperty
    private Long IDEMPRUNT;

    private String DATEDEBUT;

    private String DATERETOUR;

    private String STATUT;
    @ManyToOne
    private Emprunteur emprunteur;

    @ManyToOne
    private Exemplaire exemplaire;

    public Emprunt() {}

    public Emprunt(Long IDEMPRUNT, String DATEDEBUT, String DATERETOUR, String STATUT, Emprunteur emprunteur, Exemplaire exemplaire) {
        this.IDEMPRUNT = IDEMPRUNT;
        this.DATEDEBUT = DATEDEBUT;
        this.DATERETOUR = DATERETOUR;
        this.STATUT = STATUT;
        this.emprunteur = emprunteur;
        this.exemplaire = exemplaire;
    }

    public Long getIDEMPRUNT() {
        return IDEMPRUNT;
    }

    public void setIDEMPRUNT(Long IDEMPRUNT) {
        this.IDEMPRUNT = IDEMPRUNT;
    }

    public String getDATEDEBUT() {
        return DATEDEBUT;
    }

    public void setDATEDEBUT(String DATEDEBUT) {
        this.DATEDEBUT = DATEDEBUT;
    }

    public String getDATERETOUR() {
        return DATERETOUR;
    }

    public void setDATERETOUR(String DATERETOUR) {
        this.DATERETOUR = DATERETOUR;
    }

    public String getSTATUT() {
        return STATUT;
    }

    public void setSTATUT(String STATUT) {
        this.STATUT = STATUT;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Emprunt emprunt = (Emprunt) obj;
        return Objects.equals(IDEMPRUNT, emprunt.IDEMPRUNT) &&
                Objects.equals(DATEDEBUT, emprunt.DATEDEBUT) &&
                Objects.equals(DATERETOUR, emprunt.DATERETOUR) &&
                Objects.equals(STATUT, emprunt.STATUT) &&
                Objects.equals(emprunteur, emprunt.emprunteur) &&
                Objects.equals(exemplaire, emprunt.exemplaire);
    }


    @Override
    public int hashCode() {
        return Objects.hash(IDEMPRUNT, DATEDEBUT, DATERETOUR, STATUT, emprunteur, exemplaire);
    }
}
