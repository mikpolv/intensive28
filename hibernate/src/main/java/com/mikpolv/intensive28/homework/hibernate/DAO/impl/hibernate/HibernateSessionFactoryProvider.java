package com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.enteties.Resistor;
import com.mikpolv.intensive28.homework.hibernate.enteties.Wire;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import enteties.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSessionFactoryProvider {
  private static final SessionFactory sessionFactory;

  static {
    try {

      try {
        Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      Configuration configuration = new Configuration();
      configuration.addAnnotatedClass(Product.class);
      configuration.addAnnotatedClass(com.mikpolv.intensive28.homework.jdbc.enteties.Brand.class);
      configuration.addAnnotatedClass(com.mikpolv.intensive28.homework.jdbc.enteties.Supplier.class);
      configuration.addAnnotatedClass(Wire.class);
      configuration.addAnnotatedClass(Resistor.class);

      ServiceRegistry serviceRegistry =
          new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
      sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    } catch (Throwable ex) {
      System.err.println("Session Factory could not be created." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
