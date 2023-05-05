package jm.task.core.jdbc.service;



import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDaoHibernateImpl test = new UserDaoHibernateImpl();

    public void createUsersTable() {
        test.createUsersTable();

    }

    public void dropUsersTable() {
        test.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        test.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        test.removeUserById(id);

    }

    public List<User> getAllUsers() {
        List<User> users = test.getAllUsers();
        return users;

    }

    public void cleanUsersTable() {
        test.cleanUsersTable();

    }
}