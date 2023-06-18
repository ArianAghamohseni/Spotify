import java.net.*;
import java.io.*;

public class ApiClient {
    public static void main(String[] args) {
        try {
            // Step 1: Create URL object
            URL url = new URL("http://api.example.com/v1/songs");

            // Step 2: Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Step 3: Set request headers and method
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer <token>");

            // Step 4: Send request
            connection.connect();

            // Step 5: Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonResponse = response.toString();
                // Parse jsonResponse into Java objects using a JSON library
            }

            // Step 6: Optional - write data to server

            // Step 7: Close connection
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
