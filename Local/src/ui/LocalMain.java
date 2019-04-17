package ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.util.Status;
import model.Difficulty;
import response.JsonConvert;
import response.ResponseStatus;
import services.LocalMasterService;
import services.LocalUserService;

/*
    JsonElement jelement = new JsonParser().parse(jsonLine);
    JsonObject  jobject = jelement.getAsJsonObject();
    jobject = jobject.getAsJsonObject("data");
    JsonArray jarray = jobject.getAsJsonArray("translations");
    jobject = jarray.get(0).getAsJsonObject();
    String result = jobject.get("translatedText").getAsString();
    return result;
 */

public class LocalMain {

    //API
    public static final LocalMasterService masterService = new LocalMasterService();
    public static final LocalUserService userService = new LocalUserService();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {

        String json = masterService.createGame(10, 10, 10, Difficulty.EASY);
        System.out.println(json);

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ResponseStatus status = ResponseStatus.fromString(jsonObject.get("status").getAsString());

    }

}
