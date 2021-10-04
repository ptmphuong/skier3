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

public class UrlPath {
    private Integer resortID;
    private String seasonID;
    private String dayID;
    private Integer skierID;

    private static final String SEASONS = "seasons";
    private static final String DAYS = "days";
    private static final String SKIERS = "skiers";
    private static final Integer URL_LENGTH = 8;
    private static final Integer DAY_FIRST = 1;
    private static final Integer DAY_LAST = 366;

    public UrlPath(String[] urlParts) throws IllegalArgumentException{
        if (!urlLengthValid(urlParts)) throw new IllegalArgumentException("invalid length");
        if (!urlDefaultWordValid(urlParts)) throw new IllegalArgumentException("invalid default words");
        if (!urlInfoFieldValid(urlParts)) throw new IllegalArgumentException("invalid info field");
        this.resortID = Integer.parseInt(urlParts[1]);
        this.seasonID = urlParts[3];
        this.dayID = urlParts[5];
        this.skierID = Integer.parseInt(urlParts[7]);
    }

    public Integer getResortID() {
        return this.resortID;
    }

    public String getSeasonID() {
        return this.seasonID;
    }

    public String getDayID() {
        return this.dayID;
    }

    public Integer getSkierID() {
        return this.skierID;
    }

    private boolean urlValid(String[] urlParts) {
        return urlLengthValid(urlParts) && urlDefaultWordValid(urlParts) && urlInfoFieldValid(urlParts);
    }

    private boolean urlLengthValid(String[] urlParts) {
        // length
        return urlParts.length == URL_LENGTH;
    }

    private boolean urlDefaultWordValid(String[] urlParts) {
        // default words
        boolean validSeasonsWord = urlParts[2].equals(SEASONS);
        boolean validDaysWord = urlParts[4].equals(DAYS);
        boolean validSkiersWord = urlParts[6].equals(SKIERS);
        return validSeasonsWord && validDaysWord && validSkiersWord;
    }

    private boolean urlInfoFieldValid(String[] urlParts) {
        // type, can cast.
        boolean resortIDisInt = isInteger(urlParts[1]);
        boolean skierIDisInt = isInteger(urlParts[7]);

        boolean dayIDisInt = isInteger(urlParts[5]);
        if (!dayIDisInt) return false;

        Integer dayID = Integer.parseInt(urlParts[5]);
        boolean dayIDWithinRange = dayID >= DAY_FIRST && dayID <= DAY_LAST;

        return resortIDisInt && skierIDisInt && dayIDWithinRange;
    }

    public static boolean isInteger(String str) {
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
}
