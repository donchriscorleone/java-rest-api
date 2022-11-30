package org.example;

import jsonplaceholder.JsonPlaceholder;
import jsonplaceholder.Post;

import java.net.http.HttpRequest;

public class Main {
    public static void main(String[] args) {
        JsonPlaceholder jsonPlaceholder = new JsonPlaceholder("posts");
        Post[] posts = jsonPlaceholder.get();
        for (Post post: posts)
            System.out.println(post.getId() + " " + post.getTitle());

        Post newPost = new Post(1, 1, "Testing put", "testing put");
        newPost = jsonPlaceholder.post(newPost);
        System.out.println(newPost.getId() + " " + newPost.getTitle());
    }
}