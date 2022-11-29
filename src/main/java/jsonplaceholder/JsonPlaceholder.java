package jsonplaceholder;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonPlaceholder {
    private String api;

    public JsonPlaceholder() {
        this.api = "https://jsonplaceholder.typicode.com/posts";
    }

    public JsonPlaceholder(String api) {
        this.api = api;
    }

    public void get() {
        Gson gson = new Gson();
        Post[] posts = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.api))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            posts = gson.fromJson(response.body(), Post[].class);
            for (Post p : posts)
                System.out.println(p.getId() + " : " + p.getTitle());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void post() {
        Gson gson = new Gson();
        Post post = new Post(1, 101, "foo", "bar");
        post.setId(0);
        try {
            String json = gson.toJson(post);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.api))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            post = gson.fromJson(response.body(), Post.class);
            System.out.println(post.getId());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(Post post) {
        Gson gson = new Gson();
        try {
            String json = gson.toJson(post);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.api + "/" + post.getId()))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Post newPost = gson.fromJson(response.body(), Post.class);
//           TODO: investigate why only id is in the response body.
            System.out.println(newPost.getId());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
