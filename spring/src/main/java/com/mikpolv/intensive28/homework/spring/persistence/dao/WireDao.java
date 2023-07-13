package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;

public interface WireDao<T extends Wire, ID extends Number> extends CrudDao<T, ID> {}
