import com.google.gson.Gson;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class PostBody {
    int liftID;
    int time;

    public int getLiftID() {
        return liftID;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PostBody{" +
                "liftID=" + liftID +
                ", time=" + time +
                '}';
    }

    public static PostBody fromJsonStr(String jsonStr) {
        Gson gson = new Gson();
        PostBody body;
        try {
            body = gson.fromJson(jsonStr, PostBody.class);
            return body;
        } catch (Exception e) {
            throw new IllegalArgumentException("illegal json body - " + e);
        }
    }

    public static void main(String[] args) {
        String jsonStr = "{\"liftID\":36,\"time\":415}";
        Gson gson = new Gson();
        PostBody body;
        try {
            body = gson.fromJson(jsonStr, PostBody.class);
            System.out.println(body);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

