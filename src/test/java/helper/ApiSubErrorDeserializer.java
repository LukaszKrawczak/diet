package helper;

import com.brus5.diet.model.ApiSubError;
import com.brus5.diet.model.ApiValidationError;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ApiSubErrorDeserializer extends StdDeserializer<ApiSubError> {

    public ApiSubErrorDeserializer() {
        super(ApiSubError.class);
    }

    @Override
    public ApiSubError deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        // Here, perform the appropriate deserialization based on the content of JsonNode.
        // You can use if/else conditions or other mechanisms to choose the appropriate ApiSubError implementation.
        // For example, if the JsonNode contains the "type" field, you can use it for implementation selection.
        // For instance:
        if (node.has("type") && node.get("type").asText().equals("ValidationError")) {
            return p.getCodec().treeToValue(node, ApiValidationError.class);
        }
        return p.getCodec().treeToValue(node, ApiValidationError.class);
    }
}
