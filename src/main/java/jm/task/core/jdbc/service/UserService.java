package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserService {
    void createUsersTable();    //создаем таблицу Юзеров

    void dropUsersTable();      //удаляем таблицу пользователей

    void saveUser(String name, String lastName, byte age);  //сохраняем данные пользователя

    void removeUserById(long id);   //удаляем пользователя по id

    List<User> getAllUsers();   //выводим всех пользователей из эррэй листа

    void cleanUsersTable();  //удалем данные из таблицы
}
