package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;

public interface DistributorDao<T extends Distributor, ID extends Number> extends CrudDao<T, ID> {
    /**
     * Checks if the distributor with specified name is exist.
     *
     * @param name - distributor name
     * @return true if exist
     */
    boolean existsByName(String name);
}
