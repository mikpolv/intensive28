package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.dto.DistributorRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;

import java.util.List;
import java.util.Optional;

public interface
/**
 * Returns collection of distributor records.
 *
 * @return collection of records
 */
DistributorService<T extends Distributor, ID extends Number> {
  List<DistributorRecord> getDistributors();
  /**
   * Returns optional distributor record by specified id.
   *
   * @param id - specified id
   * @return - optional distributor record
   */
  Optional<DistributorRecord> getDistributorById(ID id);
  /**
   * Creates new distributor.
   *
   * @param distributor - distributor to create
   */
  void createDistributor(T distributor);
  /**
   * Updates distributor
   *
   * @param id - specified id of distributor to update.
   * @param distributor - updated distributor
   */
  void updateDistributor(ID id, T distributor);

  /**
   * Deletes distributor by id.
   *
   * @param id - specified id of distributor to update
   */
  void deleteDistributor(ID id);
}
