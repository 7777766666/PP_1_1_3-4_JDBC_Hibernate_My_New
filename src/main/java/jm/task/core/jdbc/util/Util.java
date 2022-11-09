package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Util {
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";    //драйвер для работы с нашей базой данных MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/pp777"; //путь к конкретной нашей базе данных, готовой
    private static final String USER = "root";                  //логин пользователя базы данных
    private static final String PASSWORD = "root";              //пароль от базы данных
    private static Connection connection;

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
}
