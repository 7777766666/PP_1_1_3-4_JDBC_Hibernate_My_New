package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


    public class UserServiceImpl implements UserService {       //имплементим интерфейс UrerService
UserDao userDao = new UserDaoJDBCImpl();        //создаем объект класса UserDaoJDBCImpl для вызова методов UserDaoJDBCImpl

        public void createUsersTable() {  //вызываем метод создания таблицы пользователей на объекте класса UserDaoJDBCImpl
            userDao.createUsersTable();
        }

        public void dropUsersTable() {
            userDao.dropUsersTable();
        }       //вызывем метод, удаляющий таблицу

        public void saveUser(String name, String lastName, byte age) {  //вызываем метод, добавляющего пользователя в базу данных
            userDao.saveUser(name, lastName, age);
            System.out.println("User с именем – " + name + " добавлен в базу данных");  //добавляем строку при добавлении таблицы
        }

        public void removeUserById(long id) {
            userDao.removeUserById(id);
        }   //метод удаляет пользователя по id

        public List<User> getAllUsers() {       //вывод всех пользователей из эррей листа в консоль
            List<User> users =  userDao.getAllUsers();      //создаем лист пользователей
            for (User xxx : users) {                       //выводим всех пользователей в консоль
                System.out.println(xxx);
            }
            return users;       //возвращаем эррей лист пользователей
        }

        public void cleanUsersTable() {
            userDao.cleanUsersTable();
        }   // удаляем пользователей из таблицы
    }