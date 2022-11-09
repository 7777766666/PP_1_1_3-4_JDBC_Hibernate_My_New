package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable();       // 1 = создание таблицы юзеров

    void dropUsersTable();  // удалить таблцу пользователей

    void saveUser(String name, String lastName, byte age);  // 2 = добавим пользователя с 4мя параметрами + выод в консоль после каждого добавления

    void removeUserById(long id);       // 5  = удалим одного пользователя по id

    List<User> getAllUsers();       // 3 = вернем список всех полттьзователей + переопределить toString в классе User

    void cleanUsersTable();         // 4 = удалим всю таблицу (всех пользователей)
}
