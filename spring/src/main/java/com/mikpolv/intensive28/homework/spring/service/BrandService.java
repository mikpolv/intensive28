package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.dto.BrandRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService<T extends Brand, ID extends Number> {
  /**
   * Returns collection of brand records.
   *
   * @return collection of records
   */
  List<BrandRecord> getBrands();

  /**
   * Returns optional brand record by specified id.
   *
   * @param id - specified id
   * @return - optional brand record
   */
  Optional<BrandRecord> getBrandById(ID id);

  /**
   * Creates new brand.
   *
   * @param brand - brand to create
   */
  void createBrand(T brand);

  /**
   * Updates brand
   *
   * @param id - specified id of brand to update.
   * @param brand - updated brand
   */
  void updateBrand(ID id, T brand);

  /**
   * Deletes brand by id.
   *
   * @param id - specified id of brand to update
   */
  void deleteBrand(ID id);
}
