package com.mikpolv.intensive28.homework.spring.persistence.dao.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.DistributorDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDistributorDao implements DistributorDao<Distributor, Integer> {

  private final SessionFactory sessionFactory;

  @Autowired
  public SpringDistributorDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void save(Distributor distributor) {
    Session session = sessionFactory.getCurrentSession();
    if (distributor.getId() == null) {
      session.persist(distributor);
    } else {
      Optional<Distributor> optionalDistributor = getById(distributor.getId());
      if (optionalDistributor.isPresent()) {
        optionalDistributor.get().setName(distributor.getName());
        optionalDistributor.get().setContact(distributor.getContact());
      }
    }
  }

  @Override
  public Optional<Distributor> getById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Distributor.class, id));
  }

  @Override
  public boolean existsById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Distributor.class, id)).isPresent();
  }

  @Override
  public Iterable<Distributor> findAll() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("SELECT d FROM Distributor d", Distributor.class).getResultList();
  }

  @Override
  public void delete(Distributor distributor) {
    Session session = sessionFactory.getCurrentSession();
    session.remove(distributor);
  }

  @Override
  public boolean existsByName(String distributorName) {
    Session session = sessionFactory.getCurrentSession();
    Distributor b =
        session
            .createQuery("SELECT d FROM Distributor d WHERE name = :name", Distributor.class)
            .setParameter("name", distributorName)
            .uniqueResult();
    return (b != null);
  }
}
