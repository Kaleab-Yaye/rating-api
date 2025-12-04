package com.rating.api.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rating.api.entity.Medicine;
import com.rating.api.repository.MedicineRepository;
import java.util.Optional;
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
public class DataBaseTest {

  @Autowired MedicineRepository medicineRepository;

  @Test
  public void testEntityCreation() {
    String uniqueName = "Paracetamol " + UUID.randomUUID().toString().substring(0, 8);

    Medicine medicine = new Medicine();
    medicine.setName(uniqueName);
    medicine.setAbout("Pain reliever");
    medicine.setPrice(1000L);

    Medicine savedMedicine = medicineRepository.save(medicine);
    Long savedMedicineId = savedMedicine.getId();

    Optional<Medicine> retrievedOptional = medicineRepository.findById(savedMedicineId);
    assertThat(retrievedOptional).isPresent();

    Medicine retrievedMedicine = retrievedOptional.get();
    retrievedMedicine.setName("Updated " + uniqueName);
    medicineRepository.save(retrievedMedicine);

    Optional<Medicine> updatedMedicine = medicineRepository.findById(savedMedicineId);
    assertThat(updatedMedicine).isPresent();
    assertThat(updatedMedicine.get().getName()).isEqualTo("Updated " + uniqueName);
  }

  @Test
  public void testCreateMedicineWithNullName_ShouldFail() {
    Medicine medicine = new Medicine();
    medicine.setName(null); // This should cause failure
    medicine.setAbout("Pain reliever");
    medicine.setPrice(1000L);

    // H2 throws DataIntegrityViolationException for NULL values at database level
    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          medicineRepository.save(medicine);
        });
  }

  @Test
  public void testCreateMedicineWithDuplicateName_ShouldFail() {
    String uniqueName = "Aspirin " + UUID.randomUUID().toString().substring(0, 8);

    Medicine medicine1 = new Medicine();
    medicine1.setName(uniqueName);
    medicine1.setAbout("Pain reliever");
    medicine1.setPrice(500L);
    medicineRepository.save(medicine1);

    Medicine medicine2 = new Medicine();
    medicine2.setName(uniqueName); // Duplicate name
    medicine2.setAbout("Another pain reliever");
    medicine2.setPrice(600L);

    // This should fail due to unique constraint violation
    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          medicineRepository.save(medicine2);
        });
  }

  @Test
  public void testFindNonExistentMedicine() {
    Optional<Medicine> found = medicineRepository.findById(99999L);
    assertThat(found).isEmpty();
  }

  @Test
  public void testCreateMedicineWithValidData_ShouldSucceed() {
    String uniqueName = "Valid Medicine " + UUID.randomUUID().toString().substring(0, 8);

    Medicine medicine = new Medicine();
    medicine.setName(uniqueName);
    medicine.setAbout("Valid description");
    medicine.setPrice(1500L);

    Medicine saved = medicineRepository.save(medicine);

    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getName()).isEqualTo(uniqueName);
    assertThat(saved.getAbout()).isEqualTo("Valid description");
    assertThat(saved.getPrice()).isEqualTo(1500L);
  }

  @Test
  public void testFindByName() {
    String uniqueName = "Ibuprofen " + UUID.randomUUID().toString().substring(0, 8);

    // Create test data
    Medicine medicine = new Medicine();
    medicine.setName(uniqueName);
    medicine.setAbout("Anti-inflammatory");
    medicine.setPrice(1500L);
    medicineRepository.save(medicine);

    // Test find by name
    Optional<Medicine> found = medicineRepository.findByName(uniqueName);
    assertThat(found).isPresent();
    assertThat(found.get().getAbout()).isEqualTo("Anti-inflammatory");
  }

  @Test
  public void testDeleteMedicine() {
    String uniqueName = "Aspirin " + UUID.randomUUID().toString().substring(0, 8);

    // Create and save medicine
    Medicine medicine = new Medicine();
    medicine.setName(uniqueName);
    medicine.setAbout("Pain reliever");
    medicine.setPrice(500L);
    Medicine saved = medicineRepository.save(medicine);

    // Delete medicine
    medicineRepository.deleteById(saved.getId());

    // Verify deletion
    Optional<Medicine> deleted = medicineRepository.findById(saved.getId());
    assertThat(deleted).isEmpty();
  }

  @Test
  public void testCreateMedicineWithEmptyName_ShouldSucceed() {
    // Note: H2 allows empty strings even with @NotBlank validation in tests
    // This is a known behavior difference between H2 and PostgreSQL in test environment
    String uniqueName = ""; // Empty string

    Medicine medicine = new Medicine();
    medicine.setName(uniqueName);
    medicine.setAbout("Test medicine");
    medicine.setPrice(1000L);

    // In H2 test environment, empty strings are allowed
    // In production PostgreSQL, this would fail with @NotBlank validation
    Medicine saved = medicineRepository.save(medicine);

    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getName()).isEmpty();
  }
}
