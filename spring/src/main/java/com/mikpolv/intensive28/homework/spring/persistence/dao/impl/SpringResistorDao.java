package com.mikpolv.intensive28.homework.spring.persistence.dao.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.ResistorDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringResistorDao implements ResistorDao<Resistor, Integer> {
  private final SessionFactory sessionFactory;

  public SpringResistorDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void save(Resistor resistor) {
    Session session = sessionFactory.getCurrentSession();
    if (resistor.getId() == null) {
      session.persist(resistor);
    } else {
      Optional<Resistor> optionalResistor = getById(resistor.getId());
      if (optionalResistor.isPresent()) {
        optionalResistor.get().setName(resistor.getName());
        optionalResistor.get().setPartNumber(resistor.getPartNumber());
        optionalResistor.get().setResistance(resistor.getResistance());
        optionalResistor.get().setVoltage(resistor.getVoltage());
        optionalResistor.get().setDistributors(resistor.getDistributors());
        optionalResistor.get().setBrand(resistor.getBrand());
      }
    }
  }

  @Override
  public Iterable<Resistor> findAll() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("FROM Product", Resistor.class).getResultList();
  }

  @Override
  public void delete(Resistor resistor) {
    Session session = sessionFactory.getCurrentSession();
    session.remove(resistor);
  }

  @Override
  public Optional<Resistor> getById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Resistor.class, id));
  }

  @Override
  public boolean existsById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Resistor.class, id)).isPresent();
  }
}
