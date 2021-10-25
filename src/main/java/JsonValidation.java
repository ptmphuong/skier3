import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonValidation {
    static final String schemaStr = "{" +
            "    \"$schema\": \"http://json-schema.org/draft-04/schema#\"," +
            "    \"title\": \"LiftIndo\",\n" +
            "    \"description\": \"LiftInfo jsonObject\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"liftID\": {\n" +
            "            \"description\": \"The unique identifier for a lift\",\n" +
            "            \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"skierID\": {\n" +
            "            \"description\": \"The unique identifier for a skier\",\n" +
            "            \"type\": \"integer\"\n" +
            "        },\n" +
            "        \"time\": {\n" +
            "            \"description\": \"time\",\n" +
            "            \"type\": \"integer\",\n" +
            "            \"minimum\": 0,\n" +
            "        },\n" +
            "    },\n" +
            "    \"required\": [\"liftID\", \"skierID\", \"time\"]\n" +
            "}";

    public static boolean isCorrect(JSONObject jsonObject) throws JSONException {
        Schema schema = loadSchema();
        try {
            schema.validate(jsonObject);
            return true;
        } catch (ValidationException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static org.everit.json.schema.Schema loadSchema() throws JSONException {
        JSONObject schemaObg = new JSONObject(schemaStr);
        org.everit.json.schema.Schema schema = SchemaLoader.load(schemaObg);
        return schema;
    }

    public static void main(String[] args) throws JSONException {
        String jsonStr = "{\"liftID\":36,\"time\":415,\"skierID\":9}";
        JSONObject obj = new JSONObject(jsonStr);
        System.out.println(isCorrect(obj));
    }
}
