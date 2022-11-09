package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;


import static jm.task.core.jdbc.util.Util.getConnection;


public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = getConnection();       //вызываем соединение с базой данных, получаем соединение

    public UserDaoJDBCImpl() {      //пустой конструктор на вход

    }

       public void createUsersTable() {                                     //метод создающий таблицу, ничего не возвращаем
        try (Statement statement = connection.createStatement()) {          //трай с ресурсами + пробуем установить соединение
            String sql = "CREATE TABLE IF NOT EXISTS users " + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(140), last_name VARCHAR(145), age INT)";
            statement.executeUpdate(sql);   //создать таблицу, если она не существует (id авто инкремент, имя 140 символов, фамилия до 145 символов, возраст int
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы пользователей");
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {      //удаляем таблицу пользователей
        try (Statement statement = connection.createStatement()) {  //устанавливаем соединение в трай с ресурсами
            String sql = "DROP TABLE IF EXISTS users";      //удалить таблицу, если она существует
            statement.executeUpdate(sql);   //запускаем выполнение преперад стэйтмента по строке (в троке команда)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {      //добавляем пользователя с именем, фамилией и возрастом (байт) в таблицу
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {  //защита от SQL инъекции
            preparedStatement.setString(1, name);    //первое поле после автоинкремента
            preparedStatement.setString(2, lastName);    //второе поле после автоинкремента
            preparedStatement.setByte(3, age);       //третье поле, не считая автоинкремента
            preparedStatement.executeUpdate();      //записываем/обновляем сохраненные нами данные
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {       //удаляем пользователя по id (лонг)
        String sql = "DELETE FROM users WHERE id = ?";      //удаляем пользователя из таблицы пользователей, где id = ?
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {   //удаляем первый столбец таблицы объекта пользователь, с индексом
            preparedStatement.setLong(1, id);   //первое значение - столбец удаляемого элеиента, второе значение это id Пользователя
            preparedStatement.executeUpdate();      //выполним запись
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {           //выводим на экран таблицу пользователей
        List<User> users = new ArrayList<>();   //создаем эррей лист

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {  //возвращаем ResultSet (переменную, полученную)
            while(resultSet.next()) {                   //считываем данные по ячейкам таблицы (примерно как компоратор)до тех пор пока есть записи в ячейках
                User user = new User(resultSet.getString("name"),   //считываем значение и возвращаем строку состоящую из (значения колонки name)
                        resultSet.getString("last_name"), resultSet.getByte("age")); // считываем и возвращаем геттер Строку, состоящую из колонки lastName, из резулта считываем байт из колонки age
                user.setId(resultSet.getLong("id"));
                users.add(user); //добавляем пользователя в эррей лист пользователей
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;       //возвращаем эррей лист пользовтаелей
    }

        public void cleanUsersTable() {     //удаляем содержимое таблицы
        try (Statement statement = connection.createStatement()) {  //пытаемся установить соединение в трай с ресурсами
            statement.executeUpdate("TRUNCATE TABLE users");    // удаляем пользователей из пользователей
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}