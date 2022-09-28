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
    public void createUsersTable() {
        try (Connection connection = Util.getConnection())
        {
            Statement statement = connection.createStatement();
            String SQL = "CREATE TABLE IF NOT EXISTS users(" +
                    "id LONG," +
                    "name varchar(45)," +
                    "lastName varchar(45)," +
                    "age INT)";
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection())
        {
            Statement statement = connection.createStatement();
            String SQL = "DROP TABLE IF EXISTS users";
            statement.execute(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection() )
        {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO users VALUES(1, ?, ?, ?)");
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
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection())
        {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("SELECT * FROM users"))
        {
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
    @Transactional
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection())
        {
            Statement statement = connection.createStatement();
            String SQL = "TRUNCATE TABLE users";
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
