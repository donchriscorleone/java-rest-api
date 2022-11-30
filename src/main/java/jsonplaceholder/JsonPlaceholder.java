package jsonplaceholder;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonPlaceholder {
    private static final String BASE_API = "https://jsonplaceholder.typicode.com/";
    private String api;

    public JsonPlaceholder(String api) {
        this.api = JsonPlaceholder.BASE_API + api;
    }

    public Post[] get() {
        Gson gson = new Gson();
        Post[] resources = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.api))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            resources = gson.fromJson(response.body(), Post[].class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return resources;
    }

    public Post post(Post newPost) {
        Gson gson = new Gson();
        try {
            String json = gson.toJson(newPost);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.api))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            newPost = gson.fromJson(response.body(), Post.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return newPost;
    }

    public Post put(Post post) {
        Gson gson = new Gson();
        Post newPost = null;
        try {
            String json = gson.toJson(post);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.api + "/" + post.getId()))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            newPost = gson.fromJson(response.body(), Post.class);
//           TODO: investigate why only id is in the response body.
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return newPost;
    }
}
