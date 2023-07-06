package com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.exceptions.DAOException;
import com.mikpolv.intensive28.homework.hibernate.exceptions.NotExistException;
import com.mikpolv.intensive28.homework.hibernate.DAO.SupplierDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Product;
import com.mikpolv.intensive28.homework.hibernate.enteties.Supplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateSupplierDao implements SupplierDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBrandDao.class);
  private final SessionFactory sessionFactory;

  public HibernateSupplierDao(java.util.function.Supplier<SessionFactory> sessionFactorySupplier) {
    sessionFactory = sessionFactorySupplier.get();
  }

  @Override
  public int create(Supplier supplier) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Supplier is provided name = {}", supplier.getSupplierName());
      session.beginTransaction();
      session.persist(supplier);
      session.getTransaction().commit();
      return supplier.getId();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Create supplier failed", e);
    }
  }

  @Override
  public Supplier read(Integer id) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Supplier id is provided {}", id);
      session.beginTransaction();
      Supplier supplier = session.get(Supplier.class, id);
      session.getTransaction().commit();
      if (supplier == null) {
        throw new NotExistException("Such supplier id does not exist");
      }
      return supplier;
    } catch (NotExistException e) {
      LOGGER.error(e.getMessage());
      throw new NotExistException("Such supplier id does not exist", e);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Read supplier failed", e);
    }
  }

  @Override
  public boolean update(Supplier supplier) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Supplier id is provided {}", supplier.getId());
      session.beginTransaction();
      if (session.get(Supplier.class, supplier.getId()) == null) {
        LOGGER.warn("Supplier does not exist");
        return false;
      }
      session.merge(supplier);
      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Update supplier failed", e);
    }
  }

  @Override
  public boolean delete(Integer id) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Supplier id is provided {}", id);
      session.beginTransaction();
      Supplier supplier = session.get(Supplier.class, id);
      if (supplier == null) {
        LOGGER.info("No such supplier id = {}", id);
        return false;
      }
      session.remove(supplier);

      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Delete supplier failed", e);
    }
  }
  /** Return list of product suppliers */
  @Override
  public List<Supplier> getProductSuppliers(Integer productId) throws DAOException {
    List<Supplier> suppliersList;
    {
      try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        String hql = "select s from Supplier s join s.products p where p.id = :productId";
        Query<Supplier> query =
            session.createQuery(hql, Supplier.class).setParameter("productId", productId);
        suppliersList = query.list();
        session.getTransaction().commit();
        return suppliersList;
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
        throw new DAOException("Get product suppliers failed", e);
      }
    }
  }

  /**
   * Return list of product for specified supplier by id;
   *
   * @param supplierId suppliers id
   * @return Product list for specified supplier id.
   */
  public List<Product> getProductsBySupplier(int supplierId) throws DAOException {
    List<Product> productList;
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      String hql = "select p from Product p join fetch p.suppliers s where s.id = :supplierId";
      Query<Product> query =
          session.createQuery(hql, Product.class).setParameter("supplierId", supplierId);
      productList = query.list();
      session.getTransaction().commit();
      return productList;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Get products by supplier failed", e);
    }
  }
}
