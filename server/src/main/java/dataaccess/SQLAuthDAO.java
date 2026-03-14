package dataaccess;

import model.AuthData;

public class SQLAuthDAO implements AuthDAO{
    public void addAuth(AuthData authData) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("INSERT INTO auth (authtoken, username) VALUES (?, ?)")){
                preparedStatement.setString(1, authData.getAuthToken());
                preparedStatement.setString(2, authData.getUsername());
                var rs = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE authToken = ?")){
                preparedStatement.setString(1, authToken);
                var rs = preparedStatement.executeUpdate();                
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public boolean validateAuth(String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("SELECT 1 FROM auth WHERE authToken = ?")){
                preparedStatement.setString(1, authToken);
                var rs = preparedStatement.executeQuery();
                return rs.next();
                
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public String getUsername(String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE authToken = ?")){
                preparedStatement.setString(1, authToken);
                var rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    return null;
                }
                String returnString = rs.getString("username");
                return returnString;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public void clearData() throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("DELETE FROM auth")){
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }

    public int size() throws DataAccessException{
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM auth")){
                var rs = preparedStatement.executeQuery();

                if(rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (Exception e){
            throw new DataAccessException("Error: ", e);
        }
    }

    
}
