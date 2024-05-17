package geiffel.da4.bibliosio.emprunt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmpruntJSONSerializer extends JsonSerializer<Emprunt> {
    @Override
    public void serialize(Emprunt emprunt, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("emprunteur",emprunt.getEmprunteur().getNomEmprunteur());
        gen.writeStringField("exemplaire",emprunt.getExemplaire().getTitre());
        gen.writeStringField("date debut",emprunt.getDateDebut());
        gen.writeStringField("date de retour",emprunt.getDateRetour());
        gen.writeStringField("statut",emprunt.getStatut());
        gen.writeStringField("url", "/emprunts/"+emprunt.getId());
        gen.writeEndObject();
    }
}
