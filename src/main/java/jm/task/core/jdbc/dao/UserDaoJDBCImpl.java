package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection())
        {
            Statement statement = connection.createStatement();
            String SQL = "CREATE TABLE IF NOT EXISTS users_1_1_3_4(" +
                    "id LONG," +
                    "name varchar(45)," +
                    "lastName varchar(45)," +
                    "age INT)";
            statement.execute("BEGIN ");
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection())
        {
            Statement statement = connection.createStatement();
            String SQL = "DROP TABLE IF EXISTS users_1_1_3_4";
            statement.execute("BEGIN ");
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = Util.getConnection())
        {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO users_1_1_3_4 VALUES(id, ?, ?, ?)");
            preparedStatement.execute("BEGIN ");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) throws SQLException {
        try (Connection connection = Util.getConnection())
        {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("DELETE FROM users_1_1_3_4 WHERE id = ?");
            preparedStatement.execute("BEGIN ");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_1_1_3_4");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection())
        {
            Statement statement = connection.createStatement();
            String SQL = "TRUNCATE TABLE users_1_1_3_4";
            statement.execute("BEGIN ");
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
