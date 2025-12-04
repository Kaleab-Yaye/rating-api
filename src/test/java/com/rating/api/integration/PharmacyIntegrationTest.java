package com.rating.api.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.rating.api.entity.Pharmacy;
import com.rating.api.repository.PharmacyRepository;
import jakarta.validation.ConstraintViolationException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PharmacyIntegrationTest {

  @Autowired private PharmacyRepository pharmacyRepository;

  @Test
  void createAndRetrievePharmacy() {
    // Use unique name for each test run
    String uniqueName = "Test Pharmacy " + UUID.randomUUID().toString().substring(0, 8);

    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName(uniqueName);
    pharmacy.setStreetAddress("123 Test St");
    pharmacy.setCity("Test City");
    pharmacy.setRegion("Test Region");
    pharmacy.setPostalCode("12345");
    pharmacy.setBalance(1000L);

    Pharmacy saved = pharmacyRepository.save(pharmacy);

    assertNotNull(saved.getId());
    assertEquals(uniqueName, saved.getName());

    Pharmacy retrieved = pharmacyRepository.findById(saved.getId()).orElse(null);
    assertNotNull(retrieved);
    assertEquals(saved.getName(), retrieved.getName());
  }

  @Test
  void createPharmacyWithNullName_ShouldFail() {
    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName(null); // Missing required field
    pharmacy.setStreetAddress("123 Test St");
    pharmacy.setCity("Test City");
    pharmacy.setRegion("Test Region");

    // This should fail due to validation constraint violation
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          pharmacyRepository.save(pharmacy);
        });
  }

  @Test
  void createPharmacyWithNullCity_ShouldFail() {
    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName("Test Pharmacy " + UUID.randomUUID().toString().substring(0, 8));
    pharmacy.setStreetAddress("123 Test St");
    pharmacy.setCity(null); // Missing required field
    pharmacy.setRegion("Test Region");

    // This should fail due to validation constraint violation
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          pharmacyRepository.save(pharmacy);
        });
  }

  @Test
  void createPharmacyWithDuplicateName_ShouldFail() {
    String uniqueName = "Unique Pharmacy " + UUID.randomUUID().toString().substring(0, 8);

    Pharmacy pharmacy1 = new Pharmacy();
    pharmacy1.setName(uniqueName);
    pharmacy1.setStreetAddress("123 Test St");
    pharmacy1.setCity("Test City");
    pharmacy1.setRegion("Test Region");
    pharmacy1.setPostalCode("12345");
    pharmacy1.setBalance(1000L);
    pharmacyRepository.save(pharmacy1);

    Pharmacy pharmacy2 = new Pharmacy();
    pharmacy2.setName(uniqueName); // Duplicate name
    pharmacy2.setStreetAddress("456 Another St");
    pharmacy2.setCity("Another City");
    pharmacy2.setRegion("Another Region");
    pharmacy2.setPostalCode("67890");
    pharmacy2.setBalance(2000L);

    // This should fail due to unique constraint violation
    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          pharmacyRepository.save(pharmacy2);
        });
  }

  @Test
  void createPharmacyWithEmptyName_ShouldFail() {
    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName(""); // Empty name
    pharmacy.setStreetAddress("123 Test St");
    pharmacy.setCity("Test City");
    pharmacy.setRegion("Test Region");

    // This should fail due to validation constraint violation
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          pharmacyRepository.save(pharmacy);
        });
  }

  @Test
  void createPharmacyWithAllRequiredFields_ShouldSucceed() {
    String uniqueName = "Complete Pharmacy " + UUID.randomUUID().toString().substring(0, 8);

    Pharmacy pharmacy = new Pharmacy();
    pharmacy.setName(uniqueName);
    pharmacy.setStreetAddress("123 Complete St");
    pharmacy.setCity("Complete City");
    pharmacy.setRegion("Complete Region");
    // postalCode and balance are optional
    // postalCode can be null, balance has default value

    Pharmacy saved = pharmacyRepository.save(pharmacy);

    assertNotNull(saved.getId());
    assertEquals(uniqueName, saved.getName());
    assertEquals("Complete City", saved.getCity());
    assertEquals("Complete Region", saved.getRegion());
    assertNotNull(saved.getBalance()); // Should have default value
  }
}
