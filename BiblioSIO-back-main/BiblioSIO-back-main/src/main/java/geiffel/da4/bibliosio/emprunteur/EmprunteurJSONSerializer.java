package geiffel.da4.bibliosio.emprunteur;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmprunteurJSONSerializer extends JsonSerializer<Emprunteur> {
    @Override
    public void serialize(Emprunteur emprunteur, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("nom",emprunteur.getNomEmprunteur());
        gen.writeStringField("prenom",emprunteur.getPrenomEmprunteur());
        gen.writeStringField("url", "/emprunteurs/"+emprunteur.getId());
        gen.writeEndObject();
    }
}
