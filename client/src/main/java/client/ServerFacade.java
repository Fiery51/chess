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
        String[] returnObject = new String[2];
        var data = Map.of("username", username, "password", password, "email", email);
        var serializer = new Gson();
        String jsonRequest = serializer.toJson(data);
        
        HttpResponse<?> response;
        Map<?, ?> result; 
        try {
            //make HTTP request to rester a user
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/user"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());;
            System.out.println(response.body());
            result = serializer.fromJson((String) response.body(), Map.class);
        } catch (Exception e) {
            returnObject[0] = "Error";
            returnObject[1] = String.valueOf(500);
            return returnObject;
        }

        if(response.statusCode() == 200){
            authToken = result.get("authToken").toString();
        }
        else{
            authToken = "Error";
        }
        returnObject[0] = authToken;
        returnObject[1] = String.valueOf(response.statusCode());
        return returnObject;
    }

    public String[] loginRequest(String username, String password) throws IOException, InterruptedException{
        String authToken;
        var data = Map.of("username", username, "password", password);
        var serializer = new Gson();
        String jsonRequest = serializer.toJson(data);
        String[] returnObject = new String[2];
        
        HttpResponse<?> response = null;
        Map<?, ?> result = null; 
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/session"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());;
            System.out.println(response.body());
            result = serializer.fromJson((String) response.body(), Map.class);
        } catch (Exception e) {
            returnObject[0] = "Error";
            returnObject[1] = String.valueOf(500);
            return returnObject;
        }

        if(response.statusCode() == 200){
            authToken = result.get("authToken").toString();
        }
        else{
            authToken = "Error";
        }

        returnObject[0] = authToken;
        returnObject[1] = String.valueOf(response.statusCode());
        return returnObject;
    }

    public int logoutRequest(String authToken) throws IOException, InterruptedException{
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/session"))
                .DELETE()
                .header("authorization", authToken)
                .timeout(Duration.ofSeconds(5))
                .build();

            HttpResponse<?> response = client.send(request, BodyHandlers.ofString());
            return response.statusCode();
        } catch (Exception e) {
            return 500;
        }
    }

    public int[] createGame(String authToken, String gameName){
        int[] returnObject = new int[2];
        var data = Map.of("gameName", gameName);
        var serializer = new Gson();
        String jsonRequest = serializer.toJson(data);

        HttpResponse<?> response = null;
        Map<?, ?> result = null; 

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/game"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .header("authorization", authToken)
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());
            returnObject[0] = response.statusCode(); 
            result = serializer.fromJson((String) response.body(), Map.class);
            returnObject[1] = (int) result.get("gameID");
            return returnObject;
        } catch (Exception e) {
            returnObject[0] = 500; 
            return returnObject; 
        }
    }










    //public int[] createGame(String authToken, String gameName){
    //    int[] returnObject = new int[2];
    //    var data = Map.of("gameName", gameName);
    //    var serializer = new Gson();
    //    String jsonRequest = serializer.toJson(data);
//
    //    HttpResponse<?> response = null;
    //    Map result = null; 
//
    //    try {
    //        HttpClient client = HttpClient.newBuilder().build();
    //        HttpRequest request = HttpRequest.newBuilder()
    //            .uri(URI.create("http://localhost:8080/session"))
    //            .POST(BodyPublishers.ofString(jsonRequest))
    //            .header("authorization", authToken)
    //            .timeout(Duration.ofSeconds(5))
    //            .build();
//
    //        response = client.send(request, BodyHandlers.ofString());
    //        return returnObject;
    //    } catch (Exception e) {
    //        return returnObject; 
    //    }
    //}
}
