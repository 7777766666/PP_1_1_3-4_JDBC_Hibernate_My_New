package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;


public class UserServiceImpl implements UserService {       //имплементим интерфейс UrerService

    UserDao userDao = new UserDaoJDBCImpl();        //создаем объект класса UserDaoJDBCImpl для вызова методов UserDaoJDBCImpl
    UserDao userDaoHibernate = new UserDaoHibernateImpl();  ////создаем объект класса UserDaoHibernateImpl для вызова методов UserDaoJDBCImpl

    public void createUsersTable() {  //вызываем метод создания таблицы пользователей на объекте класса UserDaoJDBCImpl
        userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }       //вызывем метод, удаляющий таблицу

    public void saveUser(String name, String lastName, byte age) {  //вызываем метод, добавляющего пользователя в базу данных
        userDaoHibernate.saveUser(name, lastName, age);
        //   System.out.println("User с именем – " + name + " добавлен в базу данных");  //добавляем строку при добавлении таблицы
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }   //метод удаляет пользователя по id

    //        public List<User> getAllUsers() {       //вывод всех пользователей из эррей листа в консоль
//        userDao.getAllUsers(); }
    public List<User> getAllUsers() {       //вывод всех пользователей из эррей листа в консоль
        List<User> users = userDaoHibernate.getAllUsers();      //создаем лист пользователей
        for (User xxx : users) {                       //выводим всех пользователей в консоль

        }
        return users;       //возвращаем эррей лист пользователей
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }   // удаляем пользователей из таблицы


}
