package com.mikpolv.intensive28.homework.spring.persistence.dao.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringBrandDao implements BrandDao<Brand, Integer> {
  private final SessionFactory sessionFactory;

  @Autowired
  public SpringBrandDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void save(Brand brand) {
    Session session = sessionFactory.getCurrentSession();
    if (brand.getId() == null) {
      session.persist(brand);
    } else {
      Optional<Brand> optionalBrand = getById(brand.getId());
      optionalBrand.ifPresent(value -> value.setName(brand.getName()));
    }
  }

  @Override
  public Optional<Brand> getById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Brand.class, id));
  }

  @Override
  public boolean existsById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Brand.class, id)).isPresent();
  }

  @Override
  public Iterable<Brand> findAll() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("FROM Brand", Brand.class).getResultList();
  }

  @Override
  public void delete(Brand brand) {
    Session session = sessionFactory.getCurrentSession();
    session.remove(brand);
  }

  @Override
  public boolean existsByName(String brandName) {
    return (getByName(brandName).isPresent());
  }

  @Override
  public Optional<Brand> getByName(String brandName) {
    Session session = sessionFactory.getCurrentSession();
    Brand b =
        session
            .createQuery("SELECT b FROM Brand b WHERE name = :name", Brand.class)
            .setParameter("name", brandName)
            .uniqueResult();
    if (b != null) {
      return Optional.of(b);
    }
    return Optional.empty();
  }
}
