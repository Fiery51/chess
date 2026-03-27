package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;


public class ServerFacade {
    int portNumber;
    String baseURI;
    public ServerFacade(int portNumber){
        this.portNumber = portNumber;
        baseURI = "http://localhost:" + portNumber;
    }

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
                .uri(URI.create(baseURI + "/user"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());;
            //System.out.println(response.body());
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
                .uri(URI.create(baseURI + "/session"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());;
            //System.out.println(response.body());
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
                .uri(URI.create(baseURI + "/session"))
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
                .uri(URI.create( baseURI + "/game"))
                .POST(BodyPublishers.ofString(jsonRequest))
                .header("authorization", authToken)
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());
            //System.out.println(response.statusCode());
            returnObject[0] = response.statusCode(); 
            if(returnObject[0] != 200){
                return returnObject; 
            }
            result = serializer.fromJson((String) response.body(), Map.class);
            String stringVersion = String.valueOf(result.get("gameID"));
            int intVersion = (int) Double.parseDouble(stringVersion);
            returnObject[1] = intVersion;
            return returnObject;
        } catch (Exception e) {
            e.printStackTrace();
            returnObject[0] = 500; 
            return returnObject; 
        }
    }

    public ListGamesResult listGames(String authToken){
        HttpResponse<?> response = null;
        Map<Integer, String> result = new HashMap<>();
        var serializer = new Gson();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/game"))
                .GET()
                .header("authorization", authToken)
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());
            if(response.statusCode() != 200){
                ListGamesResult returnObject = new ListGamesResult(response.statusCode(), null, null, null);
                return returnObject; 
            }

            
            Map<?, ?> root = serializer.fromJson((String) response.body(), Map.class);
            ArrayList<?> games = (ArrayList<?>) root.get("games");
            ArrayList<String> whiteUsernames = new ArrayList<>();
            ArrayList<String> blackUsernames = new ArrayList<>();
            for (int i = 0; i < games.size(); i++) {
                //turn the game BACK INTO A MAP AGAIN
                Map<?, ?> gameMap = (Map<?, ?>) games.get(i);
                //then we can NOW grab the ID and Name from it
                int gameID = ((Number) gameMap.get("gameID")).intValue();
                String gameName = (String) gameMap.get("gameName");
                String whiteUsername = (String) gameMap.get("whiteUsername");
                whiteUsernames.add(whiteUsername);
                String blackUsername = (String) gameMap.get("blackUsername");
                blackUsernames.add(blackUsername);
                //System.out.println(whiteUsername);
                //NOW we can put it into the stupid OTHER map holy smokes
                result.put(gameID, gameName);
            }


            ListGamesResult returnObject = new ListGamesResult(response.statusCode(), result, whiteUsernames, blackUsernames);
            return returnObject;
        } catch (Exception e) {
            e.printStackTrace();
            ListGamesResult returnObject = new ListGamesResult(500, null, null, null);
            return returnObject; 
        }
    }

    public int playGame(String authToken, String color, String gameID){
        HttpResponse<?> response = null;
        var data = Map.of("playerColor", color, "gameID", gameID);
        var serializer = new Gson();
        String jsonRequest = serializer.toJson(data);

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/game"))
                .PUT(BodyPublishers.ofString(jsonRequest))
                .header("authorization", authToken)
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());
            return response.statusCode();

        } catch (Exception e) {
            e.printStackTrace();
            return 500; 
        }
    }

    public int observeGame(String authToken, String gameID){
        int gameIDInt;
        try {
            gameIDInt = Integer.parseInt(gameID);
        } catch (Exception e) {
            return 400;
        }
        HttpResponse<?> response = null;
        Map<Integer, String> result = new HashMap<>();
        var serializer = new Gson();
        if(authToken == null){
            return 401;
        }
        if(gameID == null){
            System.out.println("game ID was null");
            return 400;
        }

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/game"))
                .GET()
                .header("authorization", authToken)
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());
            if(response.statusCode() != 200){
                return response.statusCode();
            }
            //System.out.println(response.statusCode());
            //System.out.println(response.body());

            
            Map<?, ?> root = serializer.fromJson((String) response.body(), Map.class);
            ArrayList<?> games = (ArrayList<?>) root.get("games");
            ArrayList<String> whiteUsernames = new ArrayList<>();
            ArrayList<String> blackUsernames = new ArrayList<>();
            for (int i = 0; i < games.size(); i++) {
                //turn the game BACK INTO A MAP AGAIN
                Map<?, ?> gameMap = (Map<?, ?>) games.get(i);
                //then we can NOW grab the ID and Name from it
                int gameIDSomething = ((Number) gameMap.get("gameID")).intValue();
                String gameName = (String) gameMap.get("gameName");
                String whiteUsername = (String) gameMap.get("whiteUsername");
                whiteUsernames.add(whiteUsername);
                String blackUsername = (String) gameMap.get("blackUsername");
                blackUsernames.add(blackUsername);
                //System.out.println(whiteUsername);
                //NOW we can put it into the stupid OTHER map holy smokes
                result.put(gameIDSomething, gameName);
                //System.out.println(gameIDSomething);
                //System.out.println(gameIDInt);
                if(gameIDSomething == gameIDInt){
                    return 200;
                }
            }
        }
        catch(Exception e){
            return 500;
        }
        return 400;
    }

    public int deleteRequest(){
        HttpResponse<?> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/db"))
                .DELETE()
                .timeout(Duration.ofSeconds(5))
                .build();

            response = client.send(request, BodyHandlers.ofString());
            return response.statusCode();
        }
        catch(Exception e){
            return 500;
        }
    }
}
