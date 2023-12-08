package bibliosio.back.emprunteur;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmprunteurJSONSerializer extends JsonSerializer<Emprunteur>
{
    @Override
    public void serialize(Emprunteur emprunteur, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        gen.writeStartObject();
        gen.writeStringField("nom",emprunteur.getNOMEMP());
        gen.writeStringField("prenom",emprunteur.getPRENOMEMP());
        gen.writeStringField("url", "/emprunteurs/"+emprunteur.getNUMEROEMP());
        gen.writeEndObject();
    }
}
