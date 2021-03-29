package dao;


import entities.*;
import util.HibernateSessionFactoryAnnotationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao {
    SessionFactory sessionFactory = HibernateSessionFactoryAnnotationUtil.getSessionFactory();

    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            List<User> userList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return userList;
        }
    }

    public User getUserById(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            user = session.get(User.class, user.getUserId());
            session.getTransaction().commit();
            return user;
        }
    }

    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public void deleteUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User deleteUser = session.get(User.class, 1);
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Order> query = builder.createQuery(Order.class);
            Root<Order> root = query.from(Order.class);
            List<Order> ordersList = session.createQuery(query).getResultList();
            for (Order order : ordersList) {
                if (order.getUser().getUserId() == user.getUserId()) {
                    order.setUser(deleteUser);
                    session.update(order);
                }
            }
            session.getTransaction().commit();
        }
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        }
    }

    public User getUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            List<User> userList = session.createQuery(query).getResultList();
            for (User users : userList) {
                if ((users.getFirstName() == user.getFirstName())&&(users.getLastName() == user.getLastName())&&(users.getAddress() == user.getAddress())) {
                    user = users;
                    break;
                }
            }
            session.getTransaction().commit();
            return user;
        }
    }
}
