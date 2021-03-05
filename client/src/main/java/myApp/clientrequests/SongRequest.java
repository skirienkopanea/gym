package myApp.clientrequests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import myApp.clientobjects.Song;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SongRequest {

    private static HttpClient client = HttpClient.newBuilder().build();

    private static Gson gson = new Gson();

    /**
     * Retrieves a list of songs from the server.
     * @query the parameters for the get request to the server.
     * @return the parsed response
     */

    public static List<Song> getSongs(String query) {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/jcole/search?q=" + query)).build();

        //Declare response object so that we can save it here once we get a response from the server.
        HttpResponse<String> response = null;

        

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();

            //return an empty list in case of error
            return List.of();

        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return gson.fromJson(response.body(), new TypeToken<List<Song>>(){}.getType());
    }

    public static List<Song> mockSong() {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/test")).build();

        //Declare response object so that we can save it here once we get a response from the server.
        HttpResponse<String> response = null;

        List songs = new ArrayList<>();


        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String name = response.body();
            Song song = new Song(name, "KOD", 2021);
            songs.add(song);


        } catch (Exception e) {
            e.printStackTrace();

            //return an empty list in case of error
            return List.of();

        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return songs;
        //gson.fromJson(response.body(), new TypeToken<List<Song>>(){}.getType());
    }
}
