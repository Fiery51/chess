package dataaccess;

import org.mindrot.jbcrypt.BCrypt;

import model.UserData;

public class SQLUserDAO implements UserDAO{




    public UserData findUser(String username) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("SELECT password, email FROM users WHERE USERNAME = ?")){
                preparedStatement.setString(1, username);
                var rs = preparedStatement.executeQuery();
                if(!rs.next()){
                    return null;
                }

                UserData returnUser = new UserData(
                    username,
                    rs.getString("password"),
                    rs.getString("email"));
                
                return returnUser;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }



    public void addUser(UserData userData) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)")){
                preparedStatement.setString(1, userData.getUsername());
                //password hashing
                String securePassword = hashPassword(userData.getPassword());
                preparedStatement.setString(2, securePassword);
                preparedStatement.setString(3, userData.getEmail());
                var rs = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }



    public void clearData() throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement("DELETE FROM users")){
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ", e);
        }
    }


    
    public int size() throws DataAccessException{
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM users")){
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

    String hashPassword(String clearTextPassword) {
       String hashedPassword = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
       return hashedPassword;
    }
}
