package client;

import java.util.ArrayList;
import java.util.Map;

import chess.ChessGame;

public record ListGamesResult(int statusCode, Map<Integer, String> games, ArrayList<String> whiteUsernames, ArrayList<String> blackUsernames, Map<Integer, ChessGame> gameBoards) {}