package geiffel.da4.bibliosio.article;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ArticleEmbeddedJSONSerializer extends JsonSerializer<Article>
{
    @Override
    public void serialize(Article article, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        gen.writeStartObject();
        gen.writeStringField("titre",article.getTitre());
        gen.writeStringField("description",article.getDescription());
        gen.writeObjectField("revue", article.getRevue());
        gen.writeObjectField("exemplaire", article.getExemplaire());
        gen.writeStringField("url", "/articles/"+article.getId());
        gen.writeEndObject();
    }

}
