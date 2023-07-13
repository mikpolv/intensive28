package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;

public interface ResistorDao<T extends Resistor, ID extends Number> extends CrudDao<T, ID> {}
