package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Name1", "LastName1", (byte) 20);
        userService.saveUser("Name2", "LastName2", (byte) 25);
        userService.saveUser("Name3", "LastName3", (byte) 30);
        userService.saveUser("Name4", "LastName4", (byte) 35);
//        userService.removeUserById(3);
        List<User> users = userService.getAllUsers();
        System.out.println(users.size());
        for (User user : users) {
            System.out.println(user);
        }
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
