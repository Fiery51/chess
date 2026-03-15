package dataaccess;

import java.sql.SQLException;


public class MySqlDataAccess {
    
    public MySqlDataAccess() throws RuntimeException {
        configureDatabase();
    }




    private final String[] createStatements = {

        """
        CREATE TABLE IF NOT EXISTS users (
          `username` varchar(256) NOT NULL,
          `password` varchar(256) NOT NULL,
          `email` varchar(256) NOT NULL,
          PRIMARY KEY (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """,

        """
        CREATE TABLE IF NOT EXISTS auth (
          `authToken` varchar(256) NOT NULL,
          `username` varchar(256) NOT NULL,
          PRIMARY KEY (`authToken`),
          INDEX(username)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """,

        """
        CREATE TABLE IF NOT EXISTS game (
          `gameID` int NOT NULL AUTO_INCREMENT,
          `whiteUsername` varchar(256) DEFAULT NULL,
          `blackUsername` varchar(256) DEFAULT NULL,
          `gameName` varchar(256) NOT NULL,
          `game` TEXT DEFAULT NULL,
          PRIMARY KEY (`gameID`),
          INDEX(whiteUsername),
          INDEX(blackUsername)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """
};





    private void configureDatabase() throws RuntimeException {
        DatabaseManager.createDatabase();

        try (java.sql.Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: ", ex);
        }
    }
}
