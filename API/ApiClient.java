import java.net.*;
import java.io.*;

public class ApiClient {
    public static void main(String[] args) {
        try {
            // Step 1: Create URL object
            URL url = new URL("http://localhost:3000");

            // Step 2: Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Step 3: Set request headers and method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Step 4: Create request body for creating an account
            String createAccountRequestBody = "{\"name\":\"John Doe\",\"email\":\"johndoe@example.com\",\"password\":\"password123\"}";

            // Step 5: Write request body to request stream for creating an account
            OutputStream createAccountOutputStream = connection.getOutputStream();
            byte[] createAccountRequestBodyBytes = createAccountRequestBody.getBytes("UTF-8");
            createAccountOutputStream.write(createAccountRequestBodyBytes);
            createAccountOutputStream.close();

            // Step 6: Read response for creating an account
            int createAccountResponseCode = connection.getResponseCode();
            if (createAccountResponseCode == HttpURLConnection.HTTP_CREATED) {
                InputStream createAccountInputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(createAccountInputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonResponse = response.toString();
                System.out.println(jsonResponse); // Success - display account created message and user data
            } else {
                // Handle error response for creating an account
            }

            // Step 7: Close connection
            connection.disconnect();
            
            // Create a new connection for playlist creation
            connection = (HttpURLConnection) url.openConnection();

            // Set request headers and method for playlist creation
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Create request body for creating a playlist
            String createPlaylistRequestBody = "{\"name\":\"My Playlist\",\"songs\":[\"song1\",\"song2\",\"song3\"]}";

            // Write request body to request stream for creating a playlist
            OutputStream createPlaylistOutputStream = connection.getOutputStream();
            byte[] createPlaylistRequestBodyBytes = createPlaylistRequestBody.getBytes("UTF-8");
            createPlaylistOutputStream.write(createPlaylistRequestBodyBytes);
            createPlaylistOutputStream.close();

            // Read response for creating a playlist
            int createPlaylistResponseCode = connection.getResponseCode();
            if (createPlaylistResponseCode == HttpURLConnection.HTTP_CREATED) {
                InputStream createPlaylistInputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(createPlaylistInputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonResponse = response.toString();
                System.out.println(jsonResponse); // Success - display playlist created message and playlist data
            } else {
                // Handle error response for creating a playlist
            }

            // Close connection
            connection.disconnect();
            
            // Create a new connection for searching
            connection = (HttpURLConnection) url.openConnection();

            // Set request headers and method for searching
            connection.setRequestMethod("GET");

            // Define search query parameter
            String searchQuery = "search_term";

            // Write search query to request stream
            OutputStream searchOutputStream = connection.getOutputStream();
            byte[] searchRequestBodyBytes = searchQuery.getBytes("UTF-8");
            searchOutputStream.write(searchRequestBodyBytes);
            searchOutputStream.close();

            // Read response for searching
            int searchResponseCode = connection.getResponseCode();
            if (searchResponseCode == HttpURLConnection.HTTP_OK) {
                InputStream searchInputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(searchInputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String jsonResponse = response.toString();
                System.out.println(jsonResponse); // Display search results
            } else {
                // Handle error response for searching
            }

            // Close connection
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
