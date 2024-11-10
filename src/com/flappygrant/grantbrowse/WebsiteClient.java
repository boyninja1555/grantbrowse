package com.flappygrant.grantbrowse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebsiteClient {
    private String url;
    private WindowManager window;

    public WebsiteClient(String url, WindowManager window) {
        this.url = url;
        this.window = window;
    }

    public JsonObject[] getElementTree() {
        try {
            String _url = url;

            if (!url.endsWith("/")) {
                _url += "/index.json";
            } else {
                _url += "index.json";
            }

            @SuppressWarnings("deprecation")
            URL urlObj = new URL(_url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JsonElement jsonElement = JsonParser.parseString(response.toString());
            
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                JsonObject[] elements = new JsonObject[jsonArray.size()];
                
                for (int i = 0; i < jsonArray.size(); i++) {
                    elements[i] = jsonArray.get(i).getAsJsonObject();
                }
                
                return elements;
            }
        } catch (Exception e) {
            window.loadErrorPage("URL could not be resolved!");
        }
        
        return new JsonObject[0];
    }
}
