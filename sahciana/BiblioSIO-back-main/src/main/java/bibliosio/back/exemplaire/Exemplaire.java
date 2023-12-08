package bibliosio.back.exemplaire;

import bibliosio.back.revue.Revue;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "IDEX")
public class Exemplaire {
    @Id
    private Long IDEX;

    private String TITREEX;

    private String DATEPARUEX;

    private String STATUTEX;
    @ManyToOne
    private Revue revue;

    public Exemplaire() {}

    public Exemplaire(Long IDEX, String TITREEX, String DATEPARUEX, String STATUTEX, Revue revue) {
        this.IDEX = IDEX;
        this.TITREEX = TITREEX;
        this.DATEPARUEX = DATEPARUEX;
        this.STATUTEX = STATUTEX;
        this.revue = revue;
    }

    public Long getIDEX() {
        return IDEX;
    }

    public String getTITREEX() {
        return TITREEX;
    }

    public String getDATEPARUEX() {
        return DATEPARUEX;
    }

    public String getSTATUTEX() {
        return STATUTEX;
    }

    public Revue getRevue() { return revue; }

    public void setIDEX(Long IDEX) {
        this.IDEX = IDEX;
    }

    public void setTITREEX(String TITREEX) {
        this.TITREEX = TITREEX;
    }

    public void setDATEPARUEX(String DATEPARUEX) {
        this.DATEPARUEX = DATEPARUEX;
    }

    public void setSTATUTEX(String STATUTEX) {
        this.STATUTEX = STATUTEX;
    }

    public void setRevue(Revue revue) { this.revue = revue; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return Objects.equals(IDEX, that.IDEX) && Objects.equals(TITREEX, that.TITREEX) && Objects.equals(DATEPARUEX, that.DATEPARUEX) && Objects.equals(STATUTEX, that.STATUTEX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDEX, TITREEX, DATEPARUEX, STATUTEX);
    }
}
