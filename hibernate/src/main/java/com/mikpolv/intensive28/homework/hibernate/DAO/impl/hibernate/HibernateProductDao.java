package com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.exceptions.DAOException;
import com.mikpolv.intensive28.homework.hibernate.exceptions.NotExistException;
import com.mikpolv.intensive28.homework.hibernate.DAO.ProductDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class HibernateProductDao implements ProductDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBrandDao.class);
  private final SessionFactory sessionFactory;

  public HibernateProductDao(Supplier<SessionFactory> sessionFactorySupplier) {
    sessionFactory = sessionFactorySupplier.get();
  }

  @Override
  public int create(Product product) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Product is provided  name = {}", product.getProductName());
      session.beginTransaction();
      session.persist(product);
      session.getTransaction().commit();
      return product.getId();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Creat product failed", e);
    }
  }

  @Override
  public Product read(Integer id) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Product id is provided {}", id);
      session.beginTransaction();
      String hql = "select p from Product p left join fetch p.suppliers where p.id = :productId";
      Query<Product> query = session.createQuery(hql, Product.class).setParameter("productId", id);
      Product product = query.getSingleResult();
      session.getTransaction().commit();
      return product;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new NotExistException("Such product id does not exist", e);
    }
  }

  @Override
  public boolean update(Product product) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Product id is provided {}", product.getId());
      session.beginTransaction();
      if (session.get(Product.class, product.getId()) == null) {
        LOGGER.warn("Product does not exist");
        return false;
      }
      session.merge(product);
      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Update product failed", e);
    }
  }

  @Override
  public boolean delete(Integer id) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Product id is provided {}", id);
      session.beginTransaction();
      Product product = session.get(Product.class, id);
      if (product == null) {
        LOGGER.info("No such product id = {}", id);
        return false;
      }
      session.remove(product);
      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Update product failed", e);
    }
  }

  /** Returns list of all products with lazy initialization exception */
  public List<Product> getProductsLazyException() throws DAOException {
    List<Product> productList;
    {
      try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        criteria.from(Product.class);
        productList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        return productList;
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
        throw new DAOException("Get product failed", e);
      }
    }
  }
  /** Returns list of all products without lazy initialization exception */
  public List<Product> getProducts() throws DAOException {
    List<Product> productList;
    {
      try (Session session = sessionFactory.openSession()) {

        session.beginTransaction();

        String hql = "select p from Product p left join fetch p.suppliers";
        Query<Product> query = session.createQuery(hql, Product.class);
        productList = query.list();
        session.getTransaction().commit();
        return productList;
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
        throw new DAOException("Get product failed", e);
      }
    }
  }
  /** Returns list of all products with Brands N+1 */
  public List<Product> getProductsN1() throws DAOException {
    List<Product> productList;
    {
      try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        String hql = "select p from Product p left join fetch p.suppliers";
        Query<Product> query = session.createQuery(hql, Product.class);
        productList = query.list();
        for (Product product : productList) {
          LOGGER.info("The Product '{}' brand:'{}'", product.getProductName(), product.getBrand());
        }
        session.getTransaction().commit();
        return productList;
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
        throw new DAOException("Get product failed", e);
      }
    }
  }

  /** Returns list of all products without N+1 */
  public List<Product> getProductsN1Solved() throws DAOException {
    List<Product> productList;
    {
      try (Session session = sessionFactory.openSession()) {
        // session.beginTransaction();
        String hql = "select p from Product p left join fetch p.suppliers left join fetch p.brand";
        Query<Product> query = session.createQuery(hql, Product.class);
        productList = query.list();
        for (Product product : productList) {
          LOGGER.info("The Product '{}' brand:'{}'", product.getProductName(), product.getBrand());
        }
        // session.getTransaction().commit();
        return productList;
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
        throw new DAOException("Get product failed", e);
      }
    }
  }
}
