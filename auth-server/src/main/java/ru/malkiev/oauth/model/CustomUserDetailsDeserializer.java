package ru.malkiev.oauth.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.io.IOException;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetailsDeserializer extends JsonDeserializer<CustomUserDetails> {

  @Override
  public CustomUserDetails deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    JsonNode jsonNode = mapper.readTree(jsonParser);
    Long id = readJsonNode(jsonNode, "id").asLong();
    String username = readJsonNode(jsonNode, "username").asText();
    String firstName = readJsonNode(jsonNode, "firstName").asText();
    String lastname = readJsonNode(jsonNode, "lastName").asText();
    String email = readJsonNode(jsonNode, "email").asText();
    String password = readJsonNode(jsonNode, "password").asText();
    List<GrantedAuthority> authorities = mapper
        .readerForListOf(GrantedAuthority.class)
        .readValue(jsonNode.get("authorities"));

    CustomUserDetails details = new CustomUserDetails();
    details.setId(id);
    details.setUsername(username);
    details.setPassword(password);
    details.setFirstName(firstName);
    details.setLastName(lastname);
    details.setEmail(email);
    details.setAuthorities(authorities);
    return details;
  }

  private JsonNode readJsonNode(JsonNode jsonNode, String field) {
    return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
  }
}
