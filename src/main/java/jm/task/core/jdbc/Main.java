package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl(); //создаем объект класса UserDaoHibernateImpl
        userDaoHibernate.createUsersTable();                                //создаем таблицу пользователей, если таблицы нет
        userDaoHibernate.saveUser("Ann", "Treugol", (byte) 59);  //добавлем первого пользователя методом saveUser
        userDaoHibernate.saveUser("Petr", "Petrovich", (byte) 68); //добавлем второго пользователя
        userDaoHibernate.saveUser("Lol", "Balabol", (byte) 13);      //добавлем третьего пользователя
        userDaoHibernate.saveUser("Tom", "Jerry", (byte) 114);         //добавлем четвертого пользователя
        userDaoHibernate.getAllUsers();             //выводим в консоль всех пользователей
        userDaoHibernate.cleanUsersTable();         //очищаем таблицу
        userDaoHibernate.dropUsersTable();          //удаляем таблицу
        Util.getSessionFactoryClose();              //вызываем метод, закрывающий фабрику соединений, если соединение не закрыто


//        UserService userService = new UserServiceImpl();        //создаем объект интерфейса UserServiceImpl
//        userService.createUsersTable();                         //создаем таблицу вызовом метода createUsersTable
//        userService.saveUser("Ann", "Treugol", (byte) 59);  //добавлем первого пользователя методом saveUser
//        userService.saveUser("Petr", "Petrovich", (byte) 68); //добавлем второго пользователя
//        userService.saveUser("Lol", "Balabol", (byte) 13);      //добавлем третьего пользователя
//        userService.saveUser("Tom", "Jerry", (byte) 4);         //добавлем четвертого пользователя
//        userService.removeUserById(3);        //проверяем добавление по индексу
//               userService.getAllUsers();      // вызываем всех пользователей из Эррэй листа
//        userService.cleanUsersTable();      //удаляем данные из таблицы
//        userService.dropUsersTable();       //удаляем таблицу
//        Util.connectionClose();             //закрываем соединение методом написнным в Util
    }
}
