package com.mikpolv.intensive28.homework.spring.persistence.dao.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.WireDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringWireDao implements WireDao<Wire, Integer> {
  private final SessionFactory sessionFactory;

  public SpringWireDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void save(Wire wire) {
    Session session = sessionFactory.getCurrentSession();
    if (wire.getId() == null) {
      session.persist(wire);
    } else {
      Optional<Wire> optionalWire = getById(wire.getId());
      if (optionalWire.isPresent()) {
        optionalWire.get().setName(wire.getName());
        optionalWire.get().setPartNumber(wire.getPartNumber());
        optionalWire.get().setAwg(wire.getAwg());
        optionalWire.get().setOutsideDiameter(wire.getOutsideDiameter());
        optionalWire.get().setDistributors(wire.getDistributors());
        optionalWire.get().setBrand(wire.getBrand());
      }
    }
  }

  @Override
  public Iterable<Wire> findAll() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("FROM Product", Wire.class).getResultList();
  }

  @Override
  public void delete(Wire wire) {
    Session session = sessionFactory.getCurrentSession();
    session.remove(wire);
  }

  @Override
  public Optional<Wire> getById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Wire.class, id));
  }

  @Override
  public boolean existsById(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    return Optional.ofNullable(session.get(Wire.class, id)).isPresent();
  }
}
