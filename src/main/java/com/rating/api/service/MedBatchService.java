package com.rating.api.service;

import com.rating.api.entity.MedBatch;
import com.rating.api.entity.Medicine;
import com.rating.api.repository.MedBatchRepository;
import com.rating.api.repository.MedicineRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MedBatchService {
  private final MedBatchRepository medBatchRepository;
  private final MedicineRepository medicineRepository;

  public MedBatchService(
      MedBatchRepository medBatchRepository, MedicineRepository medicineRepository) {
    this.medBatchRepository = medBatchRepository;
    this.medicineRepository = medicineRepository;
  }

  public MedBatch createMedBatch(MedBatch medBatch) {
    Medicine medicine =
        medicineRepository
            .findById(medBatch.getMedicine().getId())
            .orElseThrow(() -> new RuntimeException("Medicine not found"));
    medBatch.setMedicine(medicine);
    return medBatchRepository.save(medBatch);
  }

  public Optional<MedBatch> getBatchById(Long id) {
    return medBatchRepository.findById(id);
  }

  public List<MedBatch> getBatchesByMedicineId(Long medicineId) {
    return medBatchRepository.findValidBatchesByMedicineId(medicineId, LocalDate.now());
  }

  public List<MedBatch> getExpiredBatches() {
    return medBatchRepository.findByExpiryBefore(LocalDate.now());
  }

  public List<MedBatch> getBatchesExpiringBetween(LocalDate startDate, LocalDate endDate) {
    return medBatchRepository.findBatchesExpiringBetween(startDate, endDate);
  }

  public MedBatch updateBatchQuantity(Long batchId, Long newQuantity) {
    MedBatch batch =
        medBatchRepository
            .findById(batchId)
            .orElseThrow(() -> new RuntimeException("Batch not found with id: " + batchId));
    batch.setAmountPresent(newQuantity);
    return medBatchRepository.save(batch);
  }

  public void deleteBatch(Long id) {
    MedBatch batch =
        medBatchRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
    medBatchRepository.delete(batch);
  }
}
