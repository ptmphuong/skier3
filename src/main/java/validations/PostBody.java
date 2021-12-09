package validations;

import com.google.gson.Gson;

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
        return "validations.PostBody{" +
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

