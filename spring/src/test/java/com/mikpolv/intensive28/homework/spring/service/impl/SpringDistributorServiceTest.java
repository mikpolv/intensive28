package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.DistributorDao;
import com.mikpolv.intensive28.homework.spring.persistence.dto.DistributorRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

class SpringDistributorServiceTest {
  @InjectMocks private SpringDistributorService testInstance;
  @Mock private DistributorDao<Distributor, Integer> testInstanceDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getDistributors() {
    List<Distributor> distributors = new ArrayList<>();
    String distributorContactToCreate1 = "NewDistributorContact1";
    String distributorContactToCreate2 = "NewDistributorContact2";
    String distributorContactToCreate3 = "NewDistributorContact3";
    String distributorNameToCreate1 = "NewDistributor1";
    String distributorNameToCreate2 = "NewDistributor2";
    String distributorNameToCreate3 = "NewDistributor3";
    int distributorIdToCreate1 = 1;
    int distributorIdToCreate2 = 2;
    int distributorIdToCreate3 = 3;
    Distributor distributorToCreate1 =
        new Distributor(
            distributorIdToCreate1, distributorNameToCreate1, distributorContactToCreate1);
    Distributor distributorToCreate2 =
        new Distributor(
            distributorIdToCreate2, distributorNameToCreate2, distributorContactToCreate2);
    Distributor distributorToCreate3 =
        new Distributor(
            distributorIdToCreate3, distributorNameToCreate3, distributorContactToCreate3);
    distributors.add(distributorToCreate1);
    distributors.add(distributorToCreate2);
    distributors.add(distributorToCreate3);
    when(testInstanceDao.findAll()).thenReturn(distributors);
    List<DistributorRecord> br = testInstance.getDistributors();
    for (int i = 0; i < distributors.size(); i++) {
      assertEquals(distributors.get(i).getId(), (br.get(i).id()));
    }
  }

  @Test
  void getDistributorById_shouldGetById() {
    String distributorNameToCreate1 = "NewDistributor1";
    String distributorContactToCreate1 = "NewDistributorContact1";
    int distributorIdToCreate1 = 1;
    Distributor distributorToCreate1 =
        new Distributor(
            distributorIdToCreate1, distributorNameToCreate1, distributorContactToCreate1);
    when(testInstanceDao.getById(distributorIdToCreate1))
        .thenReturn(Optional.of(distributorToCreate1));
    assertEquals(
        distributorIdToCreate1, testInstance.getDistributorById(distributorIdToCreate1).get().id());
  }

  @Test
  void createDistributor_ShouldCreateDistributor() {
    String distributorNameToCreate1 = "NewDistributor1";
    Distributor distributorToCreate1 = new Distributor();
    distributorToCreate1.setName(distributorNameToCreate1);
    when(testInstanceDao.existsByName(distributorNameToCreate1)).thenReturn(false);
    doNothing().when(testInstanceDao).save(distributorToCreate1);
    testInstance.createDistributor(distributorToCreate1);
    verify(testInstanceDao, times(1)).save(distributorToCreate1);
  }

  @Test
  void updateDistributor_shouldUpdateDistributor() {
    int distributorIdToUpdate1 = 1;
    String distributorNameToUpdate1 = "UpdatedDistributor1";
    String distributorContactToUpdate1 = "UpdatedDistributorContact1";
    Distributor distributorToUpdate1 =
        new Distributor(
            distributorIdToUpdate1, distributorNameToUpdate1, distributorContactToUpdate1);
    doNothing().when(testInstanceDao).save(distributorToUpdate1);
    when(testInstanceDao.existsById(distributorIdToUpdate1)).thenReturn(true);
    testInstance.updateDistributor(distributorIdToUpdate1, distributorToUpdate1);
    verify(testInstanceDao, times(1)).save(distributorToUpdate1);
  }

  @Test
  void deleteDistributor_ShouldDeleteDistributorById() {
    int distributorIdToDelete1 = 1;
    String distributorNameToDelete1 = "NewDistributor1";
    String distributorContactToDelete1 = "NewDistributorContact1";
    Distributor distributorToCreate1 =
        new Distributor(
            distributorIdToDelete1, distributorNameToDelete1, distributorContactToDelete1);
    when(testInstanceDao.getById(distributorIdToDelete1))
        .thenReturn(Optional.of(distributorToCreate1));
    willDoNothing().given(testInstanceDao).delete(distributorToCreate1);
    testInstance.deleteDistributor(distributorIdToDelete1);
    verify(testInstanceDao, times(1)).delete(distributorToCreate1);
  }
}
