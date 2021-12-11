package validations;

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

// /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}

public class UrlValidation {
    private static final String SEASONS = "seasons";
    private static final String DAYS = "days";
    private static final String SKIERS = "skiers";
    private static final String RESORTS = "resorts";
    private static final String VERTICAL = "vertical";
    private static final Integer DAY_FIRST = 1;
    private static final Integer DAY_LAST = 366;

    private int resortID;
    private int seasonID;
    private int dayID;
    private int skierID;


    private UrlValidation(String[] urlParts, boolean getRequest, boolean skiersAPI) {
        if (!getRequest && skiersAPI && correctSkiersPOST(urlParts)) {
            this.resortID = Integer.parseInt(urlParts[1]);
            this.seasonID = Integer.parseInt(urlParts[3]);
            this.dayID = Integer.parseInt(urlParts[5]);
            this.skierID = Integer.parseInt(urlParts[7]);
        } else {
            throw new IllegalArgumentException("Invalid url parts");
        }
    }

    public int getResortID() {
        return resortID;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public int getDayID() {
        return dayID;
    }

    public int getSkierID() {
        return skierID;
    }

    public static boolean correctSkiersPOST(String[] urlParts) {
        // POST/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
        return validSkiersPostUrlLen(urlParts) && validSkiersPostDefaultWordPositions(urlParts) && validSkiersPostInfoField(urlParts);
    }

    public static boolean correctSkiersGETVerticalByDay(String[] urlParts) {
        // get the total vertical for the skier for the specified ski day
        // GET/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
        return validSkiersPostUrlLen(urlParts) && validSkiersPostDefaultWordPositions(urlParts) && validSkiersPostInfoField(urlParts);
    }

    public static boolean correctSkiersGETTotalVerticalAtResort(String[] urlParts) {
        // get the total vertical for the skier the specified resort. If no season is specified, return all seasons
        // GET/skiers/{skierID}/vertical
        boolean correctLen = (urlParts.length == 3);
        boolean correctDefaultWords = urlParts[2].equals(VERTICAL);
        boolean correctInfoField = isInteger(urlParts[1]);
        return correctLen && correctDefaultWords && correctInfoField;
    }

    public static boolean correctResortsGETUniqueSkiers(String[] urlParts) {
        // get number of unique skiers at resort/season/day
        // GET/resorts/{resortID}/seasons/{seasonID}/days/{dayID}/skiers
        boolean correctLen = (urlParts.length == 7);
        boolean correctDefaultWords = urlParts[2].equals(SEASONS) && urlParts[4].equals(DAYS) && urlParts[6].equals(SKIERS);
        boolean correctInfoField = isInteger(urlParts[1]) && isInteger(urlParts[3]) && isInteger(urlParts[5]);
        return correctLen && correctDefaultWords && correctInfoField;
    }

//    public static boolean correctResortsGETAllResorts(String[] urlParts) {
//        // .../resorts
//        boolean correctLen = (urlParts.length == 0);
//        return correctLen;
//    }
//
//    public static boolean correctResortsGETSeasons(String[] urlParts) {
//        // .../resorts/{resortID}/seasons/
//        boolean correctLen = (urlParts.length == 3);
//        boolean correctDefaultWords = urlParts[2].equals(SEASONS);
//        boolean correctInfoField = isInteger(urlParts[1]);
//        return correctLen && correctDefaultWords && correctInfoField;
//    }

    private static boolean validSkiersPostUrlLen(String[] urlParts) {
        // length
        return urlParts.length == 8;
    }

    private static boolean validSkiersPostDefaultWordPositions(String[] urlParts) {
        // default words
        boolean validSeasonsWord = urlParts[2].equals(SEASONS);
        boolean validDaysWord = urlParts[4].equals(DAYS);
        boolean validSkiersWord = urlParts[6].equals(SKIERS);
        return validSeasonsWord && validDaysWord && validSkiersWord;
    }

    private static boolean validSkiersPostInfoField(String[] urlParts) {
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

    public static UrlValidation skiersGET(String[] urlParts) {
        return new UrlValidation(urlParts, false, true);
    }

    public static UrlValidation skiersPOST(String[] urlParts) {
        return new UrlValidation(urlParts, false, true);
    }

    public static UrlValidation resortsGET(String[] urlParts) {
        return new UrlValidation(urlParts, false, true);
    }

    public static UrlValidation resortsPOST(String[] urlParts) {
        return new UrlValidation(urlParts, false, true);
    }

//    public static void main(String[] args) throws JSONException {
//        String schemaStr = "{" +
//                "    \"$schema\": \"http://json-schema.org/draft-04/schema#\"," +
//                "    \"title\": \"LiftInfo\",\n" +
//                "    \"description\": \"LiftInfo jsonObject\",\n" +
//                "    \"type\": \"object\",\n" +
//                "    \"properties\": {\n" +
//                "        \"liftID\": {\n" +
//                "            \"description\": \"The unique identifier for a lift\",\n" +
//                "            \"type\": \"integer\"\n" +
//                "        },\n" +
//                "        \"skierID\": {\n" +
//                "            \"description\": \"The unique identifier for a skier\",\n" +
//                "            \"type\": \"integer\"\n" +
//                "        },\n" +
//                "        \"time\": {\n" +
//                "            \"description\": \"time\",\n" +
//                "            \"type\": \"integer\",\n" +
//                "            \"minimum\": 0,\n" +
//                "        },\n" +
//                "    },\n" +
//                "    \"required\": [\"liftID\", \"skierID\", \"time\"]\n" +
//                "}";
//        String jsonString = "{\"liftID\":31,\"time\":363}";
//        JSONObject schemaObg = new JSONObject(schemaStr);
//        Schema schema = SchemaLoader.load(schemaObg);
//        JSONObject json = new JSONObject(jsonString);
//        schema.validate(json);
//        System.out.println(json);
//        System.out.println(json.toString());
//    }
}
