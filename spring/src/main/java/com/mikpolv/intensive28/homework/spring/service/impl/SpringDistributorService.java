package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.exception.AlreadyExistException;
import com.mikpolv.intensive28.homework.spring.persistence.dao.DistributorDao;
import com.mikpolv.intensive28.homework.spring.persistence.dto.DistributorRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;
import com.mikpolv.intensive28.homework.spring.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpringDistributorService implements DistributorService<Distributor, Integer> {
  private final DistributorDao<Distributor, Integer> distributorDao;

  @Autowired
  public SpringDistributorService(DistributorDao<Distributor, Integer> distributorDao) {
    this.distributorDao = distributorDao;
  }

  @Override
  public List<DistributorRecord> getDistributors() {
    List<DistributorRecord> listOfRecords = new ArrayList<>();

    for (Distributor d : distributorDao.findAll()) {
      listOfRecords.add(new DistributorRecord(d.getId(), d.getName(), d.getContact()));
    }
    return listOfRecords;
  }

  @Override
  public Optional<DistributorRecord> getDistributorById(Integer id) {
    Optional<Distributor> optionalDistributor = distributorDao.getById(id);
    if (optionalDistributor.isPresent()) {
      Distributor distributor = optionalDistributor.get();
      return Optional.of(
          new DistributorRecord(
              distributor.getId(), distributor.getName(), distributor.getContact()));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void createDistributor(Distributor distributor) {
    if (!distributorDao.existsByName(distributor.getName())) {
      distributorDao.save(distributor);
    } else {
      throw new AlreadyExistException(
          "Distributor with name " + distributor.getName() + " already exist");
    }
  }

  @Override
  @Transactional
  public void updateDistributor(Integer id, Distributor distributor) {
    if (distributorDao.existsById(id)) {
      distributor.setId(id);
      distributorDao.save(distributor);
    }
  }

  @Override
  @Transactional
  public void deleteDistributor(Integer id) {
    Optional<Distributor> optionalDistributor = distributorDao.getById(id);
    optionalDistributor.ifPresent(value -> distributorDao.delete(optionalDistributor.get()));
  }
}
