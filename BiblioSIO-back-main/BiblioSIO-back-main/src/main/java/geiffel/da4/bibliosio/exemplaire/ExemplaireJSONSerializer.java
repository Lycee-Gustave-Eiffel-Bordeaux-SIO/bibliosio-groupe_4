package geiffel.da4.bibliosio.exemplaire;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import geiffel.da4.bibliosio.emprunteur.Emprunteur;

import java.io.IOException;

public class ExemplaireJSONSerializer extends JsonSerializer<Exemplaire> {
    @Override
    public void serialize(Exemplaire exemplaire, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("titre",exemplaire.getTitre());
        gen.writeStringField("url", "/exemplaires/"+exemplaire.getId());
        gen.writeEndObject();
    }
}
