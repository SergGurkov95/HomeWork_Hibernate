package dao;

import entities.*;
import util.HibernateSessionFactoryAnnotationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductDao {
    private SessionFactory sessionFactory = HibernateSessionFactoryAnnotationUtil.getSessionFactory();

    public void createProduct(Product product){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    public List<Product> getAllProducts() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            List<Product> userList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return userList;
        }
    }

    public Product getProductById(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            product = session.get(Product.class, product.getProductId());
            session.getTransaction().commit();
            return product;
        }
    }

    public void updateProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        }
    }

    public Product getProductByName(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            List<Product> productList = session.createQuery(query).getResultList();
            for (Product products : productList) {
                if (products.getProductName().equals(product.getProductName())) {
                    product = products;
                    break;
                }
            }
            session.getTransaction().commit();
            return product;
        }
    }
}
