package dao;

import entities.*;
import util.HibernateSessionFactoryAnnotationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class OrderDao {
    SessionFactory sessionFactory = HibernateSessionFactoryAnnotationUtil.getSessionFactory();



    public void createOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            Date date = new Date();
            Timestamp rightDate = new Timestamp(date.getTime() / 1000 * 1000);
            order.setDate(rightDate);
            session.beginTransaction();
            order.setOrderStatus(session.get(OrderStatus.class, order.getOrderStatus().getStatusId()));
            session.save(order);
            session.getTransaction().commit();
        }
    }

    public void updateOrderStatus(Order order, OrderStatus orderStatus){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            orderStatus = session.get(OrderStatus.class, orderStatus.getStatusId());
            order = session.get(Order.class, order.getOrderId());
            order.setOrderStatus(orderStatus);
            session.update(order);
            session.getTransaction().commit();
        }
    }

    public Order getOrderById(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            order = session.get(Order.class, order.getOrderId());
            session.getTransaction().commit();
            return order;
        }
    }
}
