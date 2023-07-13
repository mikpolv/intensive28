package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;

public interface DistributorDao<T extends Distributor, ID extends Number> extends CrudDao<T, ID> {
    boolean existsByName(String name);
}
