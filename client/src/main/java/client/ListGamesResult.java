package client;

import java.util.Map;

public record ListGamesResult(int statusCode, Map<Integer, String> games) {}