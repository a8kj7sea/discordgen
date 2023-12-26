import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainClass {
    public static void main(String[] args) {

        AgentsLoader agentsLoader = new AgentsLoader("agents.txt");
        agentsLoader.load();
        int size = agentsLoader.getAgents().size();
        System.out.println("loaded " + size + " agents");

        try {
            StorageFile storageFile = new StorageFile(System.getProperty("user.dir"));
            for (int x = 0; x < size; x++) {
                doRequest(storageFile, agentsLoader.getAgents().get(x));
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    private static void doRequest(StorageFile storageFile, String agent) {
        OkHttpClient client = new OkHttpClient();
        String jsonBody = "{\"partnerUserId\":\"29061f69-f059-48c3-8599-e8164e5ec9b7\"}";

        Request request = new Request.Builder()
                .url("https://api.discord.gx.games/v1/direct-fulfillment")
                .post(RequestBody.create(MediaType.parse("application/json"), jsonBody))
                .header("sec-ch-ua", "\"Chromium\";v=\"118\", \"Opera\";v=\"104\", \"Not=A?Brand\";v=\"99\"")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("Referer", "https://www.opera.com/")
                .header("sec-ch-ua-mobile", "?0")
                .header("User-Agent", agent)
                .header("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {

            if (response.code() != 200)
                return;

            String toStore = String.format("https://discord.com/billing/partner-promotions/1180231712274387115/%s",
                    getString("token", response.body().string()));

            storageFile.write(toStore);

        } catch (IOException e) {
        }

    }

    private static String getString(String fieldName, String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode fieldNode = jsonNode.get(fieldName);

            if (fieldNode != null && fieldNode.isTextual()) {
                return fieldNode.asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
