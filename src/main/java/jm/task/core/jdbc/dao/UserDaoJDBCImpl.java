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
    Connection connection = null;
    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement())
        {
            connection = Util.getConnection();
            String SQL = "CREATE TABLE IF NOT EXISTS users(" +
                    "id LONG," +
                    "name varchar(45)," +
                    "lastName varchar(45)," +
                    "age INT)";
            statement.execute("BEGIN ");
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

    }
    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement())
        {
            Connection connection = Util.getConnection();
            String SQL = "DROP TABLE IF EXISTS users";
            statement.execute("BEGIN ");
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement("INSERT INTO users VALUES(1, ?, ?, ?)") )
        {
            Connection connection = Util.getConnection();
            preparedStatement.execute("BEGIN ");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        }catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement("DELETE FROM users WHERE id = ?"))
        {
            Connection connection = Util.getConnection();
            preparedStatement.execute("BEGIN ");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
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
    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement())
        {
            Connection connection = Util.getConnection();
            String SQL = "TRUNCATE TABLE users";
            statement.execute("BEGIN ");
            statement.execute(SQL);
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}
