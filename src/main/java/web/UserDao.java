package web;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String userQuery = "UPDATE user SET name=?, email=?, country=? WHERE id=?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud_servlet", "root", "D!lemma628");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }


    //saveUser
    public void saveUser(User user) {
        Connection connection = getConnection();
        String userQuery = "insert into user(name, email, country) values(?,?,?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("User saved successfully!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //selectAllUsers
    public List<User> selectAllUsers() {
        Connection connection = getConnection();
        String query = "select * from user;";
        List<User> users = new ArrayList<>();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    //selectUserById
    public User selectUserById(int id) {
        Connection connection = getConnection();
        String query = "select id, name, email, country from user where id=?;";
        User user = null;

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return user;
    }


    //updateUserById
    public void updateUserById(User user) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setInt(4, user.getId());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //deleteUserById
    public void deleteUserById(int id) {
        Connection connection = getConnection();
        String userQuery = "delete from user where id=?;";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(userQuery);
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("User deleted successfully!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
