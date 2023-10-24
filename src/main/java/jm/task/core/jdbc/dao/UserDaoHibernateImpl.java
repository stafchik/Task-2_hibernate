package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private static final String CREATE_USER_TABLE = """
            CREATE TABLE IF NOT EXISTS users(
            user_id bigint GENERATED ALWAYS AS IDENTITY,
            PRIMARY KEY (user_id),
            username VARCHAR(20) NOT NULL,
            lastName VARCHAR(25) NOT NULL,
            age smallint NOT NULL
            )""";
    private static final String DROP_USER_TABLE = """
            DROP TABLE users
            """;
    private static final String CLEAN_USERS_TABLE = """
            TRUNCATE users
            """;

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_USER_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Таблица не была создана");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP_USER_TABLE).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица выброшена");
        } catch (Exception e) {
            System.out.println("Таблица не выброшена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Object user = (Object) new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User добавлен");
        } catch (Exception e) {
            System.out.println("User не добавлен");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CLEAN_USERS_TABLE).executeUpdate();
            session.getTransaction().commit();
            System.out.println("User удален");
            session.close();
        } catch (Exception e) {
            System.out.println("User не был удален");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            System.out.println(list.toString());
            System.out.println("Users");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка с Users");
        }
        return list;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица была очищена");
        } catch (Exception e) {
            System.out.println("Возникли проблемы с очисткой таблицы");
        }
    }
}

