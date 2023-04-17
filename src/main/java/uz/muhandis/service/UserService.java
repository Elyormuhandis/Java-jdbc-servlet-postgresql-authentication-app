package uz.muhandis.service;

import uz.muhandis.model.Result;
import uz.muhandis.model.User;

import java.sql.*;

public class UserService {
    /**
     * private String url = "jdbc:postgresql://localhost:5432/jdbc";
     * private String dbUser = "postgres";
     * private String dbPassword = "123";
     */

    private String dbUrl = "jdbc:postgresql://localhost:5432/authjdbcservletdb";
    private String dbUser = "postgres";
    private String dbPassword = "123";


    public Result registerUser(User user) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        {
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from users where username= ?;");
                preparedStatement.setString(1, user.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
                if (count > 0) return new Result("This login already exist!", false);


                preparedStatement = connection.prepareStatement("select count(*) from users where phonenumber= ? ;");
                preparedStatement.setString(1, user.getPhoneNumber());
                resultSet = preparedStatement.executeQuery();
                count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
                if (count > 0) return new Result("This phone number have been registered!", false);
                preparedStatement = connection.prepareStatement("insert into users (firstname, lastname, username, phonenumber, password) " +
                        " values(?, ?, ?, ?, ?)");
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPhoneNumber());
                preparedStatement.setString(5, user.getPassword());
                System.out.println(preparedStatement.executeUpdate());
                return new Result("You has been registerated successfully", true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Result("Registration has been denied, try again", false);
    }

    public Result login(User user) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement statement = connection.createStatement()) {
            String query = "select * from users where username='" + user.getUsername() + "' and password='" + user.getPassword() + "';";
            return queryExecutor(query, statement);

        } catch (SQLException e) {
            e.printStackTrace();
            return new Result("Connection error", false);
        }

    }
    public Result loadUserByCookie(String username) throws ClassNotFoundException {
        if (username.equals("") && username.isEmpty()) return new Result("Login not found", false);
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement statement = connection.createStatement()) {
            String query = "select * from users where username='" + username +"';";
            return queryExecutor(query, statement);

        } catch (SQLException e) {
            e.printStackTrace();
            return new Result("Connection error", false);
        }

    }

    private Result queryExecutor(String query, Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return new Result(
                    "You are logged in",
                    true,
                    new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    ));
        } else return new Result("Wrong login or password", false);
    }
}
