package validations;

import org.json.JSONObject;
import validations.PostBody;

public class SkierMessage {
    private int resortID;
    private int seasonID;
    private int dayID;
    private int skierID;
    private int liftID;
    private int time;

    public SkierMessage(UrlValidation urlParts, PostBody postBody) {
        this.resortID = urlParts.getResortID();
        this.seasonID = urlParts.getSeasonID();
        this.dayID = urlParts.getDayID();
        this.skierID = urlParts.getSkierID();
        this.liftID = postBody.getLiftID();
        this.time = postBody.getTime();
    }

    @Override
    public String toString() {
        return "{" +
                "resortID:" + resortID +
                ", seasonID:" + seasonID +
                ", dayID:" + dayID +
                ", skierID:" + skierID +
                ", liftID:" + liftID +
                ", time:" + time +
                '}';
    }

    public JSONObject toJson() {
        String str = this.toString();
        try {
            JSONObject object = new JSONObject(str);
            return object;
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot turn validations.SkierMessage to JSONObject: " + e);
        }
    }

}
