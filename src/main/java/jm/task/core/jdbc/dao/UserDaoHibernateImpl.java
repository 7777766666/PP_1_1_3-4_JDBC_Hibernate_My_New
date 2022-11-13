package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null; //JPA java Transaction API - координирует операции к базе данных Хайбернейта
    private static final SessionFactory sessionFactory = Util.getSessionFactory();   //вызываем соединение с фабрикой сессий


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {        //метод создающий таблицу пользовтаелей, в случае отсутствия
        try (Session session = Util.getSessionFactory().openSession()) { //создаем фабрику сессий
            session.beginTransaction();             //открываем сессию
            String sql = "CREATE TABLE IF NOT EXISTS user " +                        //создать таблицу пользователей, если нет
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +            //id BIGINT (аналог лонг), не налл, авто инкремент, праймери ключ
                    "name VARCHAR(150) NOT NULL, lastName VARCHAR(155) NOT NULL, " +    //имя/фамилия строка (150 символов, 255 максимум можно)
                    "age TINYINT NOT NULL)";                                            //возраст аналог байт, нот налл

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            //native SQL-запросы с использованием org.hibernate.SQLQuery, который создается методом createSQLQёuery(String) объекта сессии
            query.executeUpdate();  //записываем querry
            session.getTransaction().commit();  //закрываем сессию методом коммит
        } catch (Exception e) {
            System.out.println("cant create new Table User");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class);
            query.executeUpdate();
            //      int query = session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class).executeUpdate();  // можно в одну строку
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("ERROR, I cant drop this table");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    //    @Override     //сохраняем нового пользователя через   transaction = session.beginTransaction();
//    public void saveUser(String name, String lastName, byte age) {  // метод сохранения/добавления нового пользователя
//        try (Session session = sessionFactory.openSession()) {     //пытаемся получить сессию подключения к базе
//            transaction = session.beginTransaction();             //открываем новую сессию перемнной транзактион
//            User user = new User(name, lastName, age);            //моздаем нового пользователя с параметрами (имя, фамилия, возраст)
//            session.save(user);                                   //в сессии (не в базе данныХ0 сохраняем пользователя
//            transaction.commit();                                 //закрываем транзакцию сессию) коммитом
//        } catch (Exception e) {
//            System.out.println(" Impossible to save user");
//            System.out.println(" Impossible to save user");
    //            if (transaction != null) {                        //если транзакция не равна null (а мы в exception)
//                transaction.rollback();                       //то все откатываем, так как у нас ошибка
//            }
//        }
//    }
    @Override   //сохраняем нового пользователя через   session.beginTransaction();
    public void saveUser(String name, String lastName, byte age) {  // метод сохранения/добавления нового пользователя
        try (Session session = sessionFactory.openSession()) {     //пытаемся получить сессию подключения к базе
            session.beginTransaction();                     //открываем новую трансакционную сессию
            User user = new User(name, lastName, age);      //Создаем нового пользователя + параметры пользователя для конструктора
            session.save(user);                             //добавляем путь для какого класса аннотация addAnnotatedClass(User.class);
            session.getTransaction().commit();              //в сессии закрываем трнзакцию, через коммит подтверждаем сохранение (не ролл бэк)
            System.out.println("User с именем – " + name + " добавлен в базу данных ");
        } catch (Exception e) {
            System.out.println(" Impossible to save user");
            if (transaction != null) {                      //если транзакция не равна null (а мы в exception)
                transaction.rollback();                     //то все откатываем, так как у нас ошибка
            }
        }
    }

    @Override
    public void removeUserById(long id) {   //получаем id пользователя, которго надо удалить
        try (Session session = Util.getSessionFactory().openSession()) {    //пытаемся получить сессию, подключиться к базе
            transaction = session.beginTransaction();           //открываем сессию
            User user = session.get(User.class, (long) id);     //присваиваем пользователю значение, полученное из базы данных по полученному id
            session.delete(user);                               //создаем запрос на удаление пользователя, которого получили по id
            transaction.commit();                               //подтверждаем транзакцию (тут идёт фактическое удаление с диска)
        } catch (Exception e) {             //ловим исключения
            System.out.println("i cant del him");
            if (transaction != null)                            //если в трансакциях JPA что-тто-то есть
                transaction.rollback();                         //делаем откат
        }
    }

//
//    @Override
//    public void removeUserById(long id) {
//        try (Session session = Util.getSessionFactory().openSession()) {
//            long myId = (long) id;
//            session.beginTransaction();
//            User user = session.get(User.class, myId);
//            session.delete(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println("i cant del him");
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
//    }

    @Override
    public List<User> getAllUsers() {               //метод возвращающий получаем эррэй лист пользователей
        List<User> userList = new ArrayList<>();    //создаем эррэй лист пользователей
        try (Session session = Util.getSessionFactory().openSession()) {    //создаем фабрику сессий
            transaction = session.beginTransaction();   //начинаем новую сессию
            userList = session.createQuery("from User ").getResultList();   //присваиваем полям эррэй листа значения из базы данных
            for (User xxx : userList)           //для всех пользователей эррэй листа userList
                System.out.println(xxx);        //вывод в консоль
            transaction.commit();               //коммитим/закрываем сессию/транзакцию
        } catch (Exception e) {                 //ловим исключения
            System.out.println("Error List");
            if (transaction != null)
                transaction.rollback();         //если в трансакции JPA что-то есть, откатываем
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {         //вызываем метод стирания таблицы, ничего не возвращаем
        try (Session session = Util.getSessionFactory().openSession()) {    //вызываем соединение, открываем сессию
            transaction = session.beginTransaction();                       //открываем транзакции на сессии
            session.createQuery("delete User").executeUpdate();         //запрашиваем удаление
            session.getTransaction().commit();                              //подтверждаем удаление и реально удаляем
            //      System.out.println("dell all users from table");
        } catch (Exception e) {                                     //ловим ошибку
            System.out.println("I cant clean this table");
            if (transaction != null) {                             //если в JPA не налл
                transaction.rollback();                             //откатываем изменения
            }
        }
    }
//    @Override
//    public void cleanUsersTable(){
//        try (Session session = Util.getSessionFactory().openSession()){
//            session.beginTransaction();
//            session.createQuery("delete User ").executeUpdate();
//            session.getTransaction().commit();
//            System.out.println("Claene Table");
//        } catch (Exception e){
//            System.out.println("Error, when I clean table");
//            if (transaction != null)
//                transaction.rollback();
//        }
//    }


}
