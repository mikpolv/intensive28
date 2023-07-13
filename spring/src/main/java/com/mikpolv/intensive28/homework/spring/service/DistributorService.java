package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;

import java.util.Optional;

public interface DistributorService<T extends Distributor, ID extends Number> {
  Iterable<Record> getDistributors();

  Optional<Record> getDistributorById(ID id);

  void createDistributor(T t);

  void updateDistributor(ID id, T distributor);

  void deleteDistributor(ID id);
}
