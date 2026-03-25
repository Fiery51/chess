package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;

import com.google.gson.Gson;

public class ServerFacade {
    public String[] registerRequest(String username, String password, String email) throws IOException, InterruptedException{
        String authToken; 
        var data = Map.of("username", username, "password", password, "email", email);
        var serializer = new Gson();
        String jsonRequest = serializer.toJson(data);
        
        //make HTTP request to rester a user
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/user"))
            .POST(BodyPublishers.ofString(jsonRequest))
            .timeout(Duration.ofSeconds(5))
            .build();

        HttpResponse<?> response = client.send(request, BodyHandlers.ofString());;
        System.out.println(response.body());
        var result = serializer.fromJson((String) response.body(), Map.class);

        if(response.statusCode() == 200){
            authToken = result.get("authToken").toString();
        }
        else{
            authToken = "Error";
        }
        String[] returnObject = new String[2];
        returnObject[0] = authToken;
        returnObject[1] = String.valueOf(response.statusCode());
        return returnObject;
    }

    public String[] loginRequest(String username, String password) throws IOException, InterruptedException{
        String authToken;
        var data = Map.of("username", username, "password", password);
        var serializer = new Gson();
        String jsonRequest = serializer.toJson(data);
        
        //make HTTP request to rester a user
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/session"))
            .POST(BodyPublishers.ofString(jsonRequest))
            .timeout(Duration.ofSeconds(5))
            .build();

        HttpResponse<?> response = client.send(request, BodyHandlers.ofString());;
        System.out.println(response.body());
        var result = serializer.fromJson((String) response.body(), Map.class);

        if(response.statusCode() == 200){
            authToken = result.get("authToken").toString();
        }
        else{
            authToken = "Error";
        }

        String[] returnObject = new String[2];
        returnObject[0] = authToken;
        returnObject[1] = String.valueOf(response.statusCode());
        return returnObject;
    }

    public void logoutRequest(String authToken) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/session"))
            .DELETE()
            .header("authorization", authToken)
            .timeout(Duration.ofSeconds(5))
            .build();

        HttpResponse<?> response = client.send(request, BodyHandlers.ofString());
    }
}
