package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS  %s ( id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT, PRIMARY KEY ( id ))";
    private static final String DROP_USERS_TABLE_SQL = "drop table if exists ";
    private static final String CLEAR_USERS_TABLE_SQL = "DELETE FROM ";

    private final SessionFactory ses = Util.getSession();
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = ses.getCurrentSession()) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(String.format(CREATE_USERS_TABLE_SQL, user.getClass().getSimpleName())).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            transaction.rollback();
        }
    }
    @Override
    public void dropUsersTable() {
        try (Session session = ses.getCurrentSession()) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(DROP_USERS_TABLE_SQL + user.getClass().getSimpleName()).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            transaction.rollback();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = ses.getCurrentSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь с именем: \"" + name + " " + lastName + "\" добавлен в таблицу");
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = ses.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь с id " + id + "удалён");
        } catch (Exception e) {
            transaction.rollback();
        }
    }
    @Override
    public List < User > getAllUsers() {
        List < User > result = new ArrayList < > ();
        try (Session session = ses.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            result.addAll(session.createQuery("FROM " + user.getClass().getSimpleName()).getResultList());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return result;
    }
    @Override
    public void cleanUsersTable() {
        try (Session session = ses.getCurrentSession()) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAR_USERS_TABLE_SQL + user.getClass().getSimpleName()).executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
