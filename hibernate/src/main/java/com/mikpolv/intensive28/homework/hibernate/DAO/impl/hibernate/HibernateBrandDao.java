package com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.exceptions.DAOException;
import com.mikpolv.intensive28.homework.hibernate.exceptions.NotExistException;
import com.mikpolv.intensive28.homework.hibernate.DAO.BrandDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Brand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class HibernateBrandDao implements BrandDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBrandDao.class);
  private final SessionFactory sessionFactory;

  public HibernateBrandDao(Supplier<SessionFactory> sessionFactorySupplier) {
    sessionFactory = sessionFactorySupplier.get();
  }

  @Override
  public List<Brand> getBrands() throws DAOException {
    List<Brand> brandList;
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<Brand> criteria = builder.createQuery(Brand.class);
      criteria.from(Brand.class);
      brandList = session.createQuery(criteria).getResultList();
      session.getTransaction().commit();
      return brandList;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Get brands failed", e);
    }
  }

  @Override
  public int create(Brand brand) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Brand name is provided {}", brand.getBrandName());
      session.beginTransaction();
      session.persist(brand);
      session.getTransaction().commit();
      return brand.getId();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Creation brand failed", e);
    }
  }

  @Override
  public Brand read(Integer id) throws DAOException {

    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Brand id is provided {}", id);
      session.beginTransaction();
      Brand brand = session.get(Brand.class, id);
      session.getTransaction().commit();
      if (brand == null) {
        throw new NotExistException("Such brand id does not exist");
      }
      return brand;
    } catch (NotExistException e) {
      LOGGER.error(e.getMessage());
      throw new NotExistException("Such brand id does not exist", e);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Read brand failed", e);
    }
  }

  @Override
  public boolean update(Brand brand) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Brand id is provided {}", brand.getId());
      session.beginTransaction();
      if (session.get(Brand.class, brand.getId()) == null) {
        LOGGER.warn("Brand does not exist");
        return false;
      }
      session.merge(brand);
      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Update brand failed", e);
    }
  }

  @Override
  public boolean delete(Integer id) throws DAOException {
    try (Session session = sessionFactory.openSession()) {
      LOGGER.info("Brand id is provided {}", id);
      session.beginTransaction();
      Brand brand = session.get(Brand.class, id);
      if (brand == null) {
        LOGGER.info("No such brand id= " + id);
        return false;
      }
      session.remove(brand);
      session.getTransaction().commit();
      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new DAOException("Deletion brand failed", e);
    }
  }
}
