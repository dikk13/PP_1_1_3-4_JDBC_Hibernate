package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    @Transactional
    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement())
        {
            String SQL = "CREATE TABLE IF NOT EXISTS users(" +
                    "id LONG," +
                    "name varchar(45)," +
                    "lastName varchar(45)," +
                    "age INT)";
            statement.execute(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Transactional
    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement())
        {
            String SQL = "DROP TABLE IF EXISTS users";
            statement.execute(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("INSERT INTO users VALUES(1, ?, ?, ?)"))
        {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("DELETE FROM users WHERE id = ?"))
        {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Transactional
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
        try (Statement statement = Util.getConnection().createStatement())
        {
            String SQL = "TRUNCATE TABLE users";
            statement.execute(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
