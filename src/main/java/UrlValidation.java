import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Length: 8
 * index: 0. Part:
 * index: 1. Part: 12
 * index: 2. Part: seasons
 * index: 3. Part: 2019
 * index: 4. Part: day
 * index: 5. Part: 1
 * index: 6. Part: skier
 * index: 7. Part: 123
 */

public class UrlValidation {
    private static final String SEASONS = "seasons";
    private static final String DAYS = "days";
    private static final String SKIERS = "skiers";
    private static final Integer URL_LENGTH = 8;
    private static final Integer DAY_FIRST = 1;
    private static final Integer DAY_LAST = 366;

    public static boolean isCorrect(String[] urlParts) {
        return urlLengthValid(urlParts) && urlDefaultWordValid(urlParts) && urlInfoFieldValid(urlParts);
    }

    public static String getSkierID(String[] urlParts) {
        String skierIDStr = urlParts[urlParts.length-1];
        return skierIDStr;
    }

    private static boolean urlLengthValid(String[] urlParts) {
        // length
        return urlParts.length == URL_LENGTH;
    }

    private static boolean urlDefaultWordValid(String[] urlParts) {
        // default words
        boolean validSeasonsWord = urlParts[2].equals(SEASONS);
        boolean validDaysWord = urlParts[4].equals(DAYS);
        boolean validSkiersWord = urlParts[6].equals(SKIERS);
        return validSeasonsWord && validDaysWord && validSkiersWord;
    }

    private static boolean urlInfoFieldValid(String[] urlParts) {
        // type, can cast.
        boolean resortIDisInt = isInteger(urlParts[1]);
        boolean skierIDisInt = isInteger(urlParts[7]);

        boolean dayIDisInt = isInteger(urlParts[5]);
        if (!dayIDisInt) return false;

        Integer dayID = Integer.parseInt(urlParts[5]);
        boolean dayIDWithinRange = dayID >= DAY_FIRST && dayID <= DAY_LAST;

        return resortIDisInt && skierIDisInt && dayIDWithinRange;
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws JSONException {
        String schemaStr = "{" +
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
        String jsonString = "{\"liftID\":31,\"time\":363}";
        JSONObject schemaObg = new JSONObject(schemaStr);
        Schema schema = SchemaLoader.load(schemaObg);
        JSONObject json = new JSONObject(jsonString);
        schema.validate(json);
        System.out.println(json);
        System.out.println(json.toString());
    }
}
