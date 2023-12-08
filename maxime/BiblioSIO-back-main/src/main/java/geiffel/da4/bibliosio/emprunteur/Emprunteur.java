package geiffel.da4.bibliosio.emprunteur;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "NUMEROEMP")
public class Emprunteur {

    @Id
    @JsonProperty
    private Long NUMEROEMP;

    private String NOMEMP;

    private String PRENOMEMP;

    private String MAILEMP;

    public Emprunteur(){}

    public Emprunteur(Long num, String nom, String prenom, String mail){
        this.NUMEROEMP=num;
        this.NOMEMP=nom;
        this.PRENOMEMP=prenom;
        this.MAILEMP=mail;
    }

    public Long getNUMEROEMP() {
        return NUMEROEMP;
    }

    public void setNUMEROEMP(Long NUMEROEMP) {
        this.NUMEROEMP = NUMEROEMP;
    }

    public String getNOMEMP() {
        return NOMEMP;
    }

    public void setNOMEMP(String NOMEMP) {
        this.NOMEMP = NOMEMP;
    }

    public String getPRENOMEMP() {
        return PRENOMEMP;
    }

    public void setPRENOMEMP(String PRENOMEMP) {
        this.PRENOMEMP = PRENOMEMP;
    }

    public String getMAILEMP() {
        return MAILEMP;
    }

    public void setMAILEMP(String MAILEMP) {
        this.MAILEMP = MAILEMP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunteur that = (Emprunteur) o;
        return Objects.equals(NUMEROEMP, that.NUMEROEMP) && Objects.equals(NOMEMP, that.NOMEMP) && Objects.equals(PRENOMEMP, that.PRENOMEMP) && Objects.equals(MAILEMP, that.MAILEMP);
    }

}
