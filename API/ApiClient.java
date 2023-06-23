import java.net.*;
import java.io.*;

public class ApiClient {
    public static void main(String[] args) {
        try {
            // Step 1: Create URL object
            URL url = new URL("http://localhost:3000/api/users");

            // Step 2: Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Step 3: Set request headers and method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Step 4: Create request body for creating user account
            String requestBody = "{\"username\":\"JohnDoe\",\"email\":\"johndoe@example.com\",\"password\":\"password123\"}";

            // Step 5: Write request body to request stream
            OutputStream outputStream = connection.getOutputStream();
            byte[] requestBodyBytes = requestBody.getBytes("UTF-8");
            outputStream.write(requestBodyBytes);
            outputStream.close();

            // Step 6: Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonResponse = response.toString();
                System.out.println(jsonResponse); // Success - display account created message and user data

                // Get the user ID from the response JSON
                JSONObject jsonObject = new JSONObject(jsonResponse);
                int userId = jsonObject.getInt("id");

                // Update the request URL to add the user ID to get liked songs
                String likedSongsUrl = "http://localhost:3000/api/users/" + userId + "/liked_songs";
                url = new URL(likedSongsUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                // Read response for getting liked songs
                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    response = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    jsonResponse = response.toString();
                    System.out.println(jsonResponse); // Display the user's liked songs
                } else {
                    // Handle error response
                }

            } else {
                // Handle error response
            }

            // Step 7: Close connection
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
