package com.mikpolv.intensive28.homework.spring.persistence.dao.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.ProductDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringProductDao implements ProductDao<Product, Integer> {

  private final SessionFactory sessionFactory;

  @Autowired
  public SpringProductDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Iterable<Product> findAllProducts() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("FROM Product", Product.class).getResultList();
  }

  @Override
  public Optional<Product> getById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Product.class, id));
  }
}
