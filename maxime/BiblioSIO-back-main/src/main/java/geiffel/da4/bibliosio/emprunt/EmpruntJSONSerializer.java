package geiffel.da4.bibliosio.emprunt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmpruntJSONSerializer extends JsonSerializer<Emprunt> {
    @Override
    public void serialize(Emprunt emprunt, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("emprunteur",emprunt.getEmprunteur().getNOMPRENOM());
        gen.writeStringField("exemplaire",emprunt.getExemplaire().getTITREEX());
        gen.writeStringField("date debut",emprunt.getDATEDEBUT());
        gen.writeStringField("date de retour",emprunt.getDATERETOUR());
        gen.writeStringField("statut",emprunt.getSTATUT());
        gen.writeStringField("url", "/emprunts/"+emprunt.getIDEMPRUNT());
        gen.writeEndObject();
    }
}
