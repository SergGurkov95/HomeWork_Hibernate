package dao;

import entities.Category;
import entities.Product;
import util.HibernateSessionFactoryAnnotationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CategoryDao {
    private SessionFactory sessionFactory = HibernateSessionFactoryAnnotationUtil.getSessionFactory();

    public void createCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    public List<Category> getCategories() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> query = builder.createQuery(Category.class);
            Root<Category> root = query.from(Category.class);
            List<Category> categoryList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return categoryList;
        }
    }

    public Category getCategoryByName(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> query = builder.createQuery(Category.class);
            Root<Category> root = query.from(Category.class);
            query.select(root).where(builder.equal(root.get("categoryName"), category.getCategoryName()));
            List<Category> categoryList = session.createQuery(query).getResultList();
            if (categoryList.size() != 0) {
                session.getTransaction().commit();
                return categoryList.get(0);
            } else{
                session.getTransaction().commit();
                return null;
            }
        }
    }
}
