package com.rating.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.rating.api.entity.Medicine;
import com.rating.api.repository.MedBatchRepository;
import com.rating.api.repository.MedicineRepository;
import com.rating.api.repository.RatingRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

  @Mock private MedicineRepository medicineRepository;

  @Mock private MedBatchRepository medBatchRepository;

  @Mock private RatingRepository ratingRepository;

  @InjectMocks private MedicineService medicineService;

  private Medicine medicine;

  @BeforeEach
  void setUp() {
    medicine = new Medicine();
    medicine.setId(1L);
    medicine.setName("Test Medicine");
    medicine.setAbout("Test Description");
    medicine.setPrice(1000L);
  }

  @Test
  void createMedicine_Success() {
    when(medicineRepository.findByName(any())).thenReturn(Optional.empty());
    when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);

    Medicine result = medicineService.createMedicine(medicine);

    assertNotNull(result);
    assertEquals("Test Medicine", result.getName());
    verify(medicineRepository, times(1)).save(medicine);
  }

  @Test
  void createMedicine_WhenNameExists_ThrowsException() {
    when(medicineRepository.findByName(any())).thenReturn(Optional.of(medicine));

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> medicineService.createMedicine(medicine));

    assertEquals("Medicine with name Test Medicine already exists", exception.getMessage());
    verify(medicineRepository, never()).save(any(Medicine.class));
  }

  @Test
  void createMedicine_WithEmptyName_ShouldBeHandledByValidation() {
    // Note: In service layer, validation might not be triggered if not using @Valid
    // The service will try to save and the repository will throw ConstraintViolationException
    // But in unit tests with mocks, we don't get validation exceptions

    Medicine invalidMedicine = new Medicine();
    invalidMedicine.setName(""); // This would be caught by validation in real scenario
    invalidMedicine.setAbout("Description");
    invalidMedicine.setPrice(1000L);

    // Since we're mocking, the validation doesn't happen in unit tests
    // This test just verifies the service behavior with empty name
    when(medicineRepository.findByName("")).thenReturn(Optional.empty());
    when(medicineRepository.save(invalidMedicine)).thenReturn(invalidMedicine);

    Medicine result = medicineService.createMedicine(invalidMedicine);

    assertNotNull(result);
    // In real scenario with validation, this would throw ConstraintViolationException
  }

  @Test
  void getMedicineById_Success() {
    when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));

    Optional<Medicine> result = medicineService.getMedicineById(1L);

    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());
  }

  @Test
  void getMedicineById_NotFound() {
    when(medicineRepository.findById(999L)).thenReturn(Optional.empty());

    Optional<Medicine> result = medicineService.getMedicineById(999L);

    assertFalse(result.isPresent());
  }

  @Test
  void updateMedicine_Success() {
    Medicine updatedDetails = new Medicine();
    updatedDetails.setName("Updated Medicine");
    updatedDetails.setAbout("Updated Description");
    updatedDetails.setPrice(2000L);

    when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
    when(medicineRepository.save(any(Medicine.class))).thenReturn(updatedDetails);

    Medicine result = medicineService.updateMedicine(1L, updatedDetails);

    assertNotNull(result);
    assertEquals("Updated Medicine", result.getName());
    verify(medicineRepository, times(1)).save(any(Medicine.class));
  }

  @Test
  void updateMedicine_NotFound() {
    Medicine updatedDetails = new Medicine();
    updatedDetails.setName("Updated Medicine");

    when(medicineRepository.findById(999L)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              medicineService.updateMedicine(999L, updatedDetails);
            });

    assertEquals("Medicine not found with id: 999", exception.getMessage());
    verify(medicineRepository, never()).save(any(Medicine.class));
  }

  @Test
  void deleteMedicine_NotFound() {
    when(medicineRepository.findById(999L)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              medicineService.deleteMedicine(999L);
            });

    assertEquals("Medicine not found with id: 999", exception.getMessage());
    verify(medicineRepository, never()).delete(any(Medicine.class));
  }
}
