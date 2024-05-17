package geiffel.da4.bibliosio.revue;

public class RevueBuilder {
    private Long id = 1L;
    private String titre = "default title";

    public RevueBuilder() {
    }

    public RevueBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public RevueBuilder withTitre(String titre) {
        this.titre = titre;
        return this;
    }
    public Revue build() {
       return new Revue(id,titre);
    }
}
