package dao;

import entities.*;
import util.HibernateSessionFactoryAnnotationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderMapDao {

    SessionFactory sessionFactory = HibernateSessionFactoryAnnotationUtil.getSessionFactory();


    public void createOrderMap(OrderMap orderMap) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            orderMap.setOrder(session.get(Order.class, orderMap.getOrder().getOrderId()));
            orderMap.setProduct(session.get(Product.class, orderMap.getProduct().getProductId()));

            session.save(orderMap);
            session.getTransaction().commit();
        }
    }

    public List<OrderMap> getOrderMap(OrderMap orderMap) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderMap> query = builder.createQuery(OrderMap.class);
            Root<OrderMap> root = query.from(OrderMap.class);
            List<OrderMap> orderMapList = session.createQuery(query).getResultList();
            List<OrderMap> listWithRightOrders = new ArrayList<>();
            for (OrderMap orders : orderMapList) {
                if (orders.getOrder().getOrderId() == orderMap.getOrder().getOrderId()) {
                    listWithRightOrders.add(orders);
                }
            }
            session.getTransaction().commit();
            return listWithRightOrders;
        }
    }

    public void updateOrderMap(OrderMap orderMap) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(orderMap);
            session.getTransaction().commit();
        }
    }

    public void deleteOrderMap(OrderMap orderMap) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(orderMap);
            session.getTransaction().commit();
        }
    }

    public boolean orderMapCheck(OrderMap orderMap) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderMap> query = builder.createQuery(OrderMap.class);
            Root<OrderMap> root = query.from(OrderMap.class);
            List<OrderMap> orderMapList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            if (orderMapList.size() == 0) {
                return true;
            } else return false;
        }
    }

    public List<OrderMap> getAllOrderMap(OrderMap orderMap) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderMap> query = builder.createQuery(OrderMap.class);
            Root<OrderMap> root = query.from(OrderMap.class);
            List<OrderMap> orderMapList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return orderMapList;
        }
    }
}
