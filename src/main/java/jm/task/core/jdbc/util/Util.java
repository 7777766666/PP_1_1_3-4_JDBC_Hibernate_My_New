package jm.task.core.jdbc.util;
//package net.javaguides.hibernate.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";    //драйвер для работы с нашей базой данных MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/pp777"; //путь к конкретной нашей базе данных, готовой
    private static final String USER = "root";                  //логин пользователя базы данных
    private static final String PASSWORD = "root";              //пароль от базы данных
    private static Connection connection;

    private static SessionFactory sessionFactory;           //создаем переменную сессионной фабрики сессий для Хайбернейта

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties propertiesSetting = new Properties();                          //создаем класс настроек
                propertiesSetting.put(Environment.DRIVER, DRIVER);    //добавляем драйвер
                propertiesSetting.put(Environment.URL, URL);   //добавляем УРЛ
                propertiesSetting.put(Environment.USER, USER);                              //добавляем пользователя
                propertiesSetting.put(Environment.PASS, PASSWORD);                              //добавляем пароль
                propertiesSetting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");   //добавляем диалект MySQL
                propertiesSetting.put(Environment.SHOW_SQL, "true");
                propertiesSetting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                setting.put(Environment.HBM2DDL_AUTO, "create-drop");   // это удаление и создание таблицы автоматечически
                propertiesSetting.put(Environment.HBM2DDL_AUTO, "");   // теперь таблица не будет ужаляться и создаваться автоматически
                configuration.setProperties(propertiesSetting);
                configuration.addAnnotatedClass(User.class); //добавляем "аннотацию", в какой класс извлекаем таблицу

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                System.out.println("Session Factory is the best");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Ошибка соединения Session Factory");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() {                         //создаем соединение с базой данных

        try {
            Class.forName(DRIVER);                              //вызываем драйвер для работы с MySQL (если старая база до 8 джавы)
            connection = DriverManager.getConnection(URL, USER, PASSWORD);      //вводим из прайвет полей пароли, логин и путь
            //       System.out.println("connection is very good");                  //в случае успешного подключения
        } catch (ClassNotFoundException | SQLException e) {                 //ловим ошибки
            System.out.println("ERROR!!!! Connection is epsent");           //в случае не подключения выводим
        }
        return connection;                          //возвращаем соединение для вызова геттером
    }

    public static void getSessionFactoryClose() {   //метод, закрывающий фабрику сессий, если фабрика не закрыта
        if (Util.getSessionFactory() != null) {     // если фабрика сессий не закрыта
            Util.getSessionFactory().close();       //закрываем фабрику сессий
        }
    }

    public static void connectionClose() {      //метод для закрытия соединения
        if (Util.getConnection() != null) {     //если соединение не равно null
            try {                               //пробуем
                Util.getConnection().close();     //закрываем соединение
                //           System.out.println("Закрыл соединение");    //временная строка говорящая о том, что соединение закрыто
            } catch (SQLException e) {                      // ловим исключение
                System.out.println("Connectoion clouse Error"); //выводим в консоль строку, что ошибка при закрытии соединения
                e.printStackTrace();                //выводим в консоль стэкТрэйс
            }
        }
    }
}