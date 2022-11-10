package jm.task.core.jdbc;

import java.util.stream.*;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();        //создаем объект интерфейса UserServiceImpl
        userService.createUsersTable();                         //создаем таблицу вызовом метода createUsersTable
        userService.saveUser("Ann", "Treugol", (byte) 59);  //добавлем первого пользователя методом saveUser
        userService.saveUser("Petr", "Petrovich", (byte) 68); //добавлем второго пользователя
        userService.saveUser("Lol", "Balabol", (byte) 13);      //добавлем третьего пользователя
        userService.saveUser("Tom", "Jerry", (byte) 4);         //добавлем четвертого пользователя
        //     userService.removeUserById(3);        //проверяем добавление по индексу
        userService.getAllUsers();      // вызываем всех пользователей из Эррэй листа
        userService.cleanUsersTable();      //удаляем данные из таблицы
        userService.dropUsersTable();       //удаляем таблицу
        Util.connectionClose();             //закрываем соединение методом написнным в Util

    }
}
