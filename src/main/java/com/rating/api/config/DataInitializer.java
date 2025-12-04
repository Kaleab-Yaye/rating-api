package com.rating.api.config;

import com.rating.api.entity.*;
import com.rating.api.repository.*;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test") // Don't run in tests
public class DataInitializer {

  @Bean
  CommandLineRunner initDatabase(
      PharmacyRepository pharmacyRepo,
      MedicineRepository medicineRepo,
      MedBatchRepository batchRepo,
      PharmacistRepository pharmacistRepo,
      InventoryManagerRepository inventoryManagerRepo,
      OrderRepository orderRepo,
      RatingRepository ratingRepo,
      ReviewRepository reviewRepo) {
    return args -> {
      // Check if pharmacy already exists before creating
      if (pharmacyRepo.findByName("City Pharmacy").isEmpty()) {
        // Create a pharmacy
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("City Pharmacy");
        pharmacy.setStreetAddress("123 Main St");
        pharmacy.setCity("Addis Ababa");
        pharmacy.setRegion("Addis Ababa");
        pharmacy.setPostalCode("1000");
        pharmacy.setBalance(5000L);
        Pharmacy savedPharmacy = pharmacyRepo.save(pharmacy);

        // Create a pharmacist
        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setName("John Doe");
        pharmacist.setEmail("john.doe@pharmacy.com");
        pharmacist.setPharmacy(savedPharmacy);
        pharmacist.setIsAdmin(true);
        Pharmacist savedPharmacist = pharmacistRepo.save(pharmacist);

        // Create inventory manager
        InventoryManager manager = new InventoryManager();
        manager.setName("Inventory Admin");
        manager.setEmail("inventory@company.com");
        manager.setIsAdmin(true);
        inventoryManagerRepo.save(manager);

        // Create some medicines
        Medicine paracetamol = new Medicine();
        paracetamol.setName("Paracetamol 500mg");
        paracetamol.setAbout("Pain reliever and fever reducer");
        paracetamol.setPrice(150L);
        Medicine savedParacetamol = medicineRepo.save(paracetamol);

        Medicine amoxicillin = new Medicine();
        amoxicillin.setName("Amoxicillin 250mg");
        amoxicillin.setAbout("Antibiotic for bacterial infections");
        amoxicillin.setPrice(300L);
        Medicine savedAmoxicillin = medicineRepo.save(amoxicillin);

        // Create medicine batches
        MedBatch batch1 = new MedBatch();
        batch1.setMedicine(savedParacetamol);
        batch1.setAmountPresent(100L);
        batch1.setExpiry(LocalDate.now().plusMonths(24));
        batchRepo.save(batch1);

        MedBatch batch2 = new MedBatch();
        batch2.setMedicine(savedAmoxicillin);
        batch2.setAmountPresent(50L);
        batch2.setExpiry(LocalDate.now().plusMonths(12));
        batchRepo.save(batch2);

        // Create an order
        Order order = new Order();
        order.setPharmacist(savedPharmacist);
        order.setPharmacy(savedPharmacy);
        Order savedOrder = orderRepo.save(order);

        // Create a rating
        Rating rating = new Rating();
        rating.setPharmacist(savedPharmacist);
        rating.setPharmacy(savedPharmacy);
        rating.setMedicine(savedParacetamol);
        rating.setRating(4);
        Rating savedRating = ratingRepo.save(rating);

        // Create a review
        Review review = new Review();
        review.setPharmacist(savedPharmacist);
        review.setPharmacy(savedPharmacy);
        review.setMedicine(savedParacetamol);
        review.setRating(savedRating);
        review.setReviewText("Great medicine for pain relief!");
        reviewRepo.save(review);

        System.out.println("Sample data initialized successfully!");
      } else {
        System.out.println("Sample data already exists. Skipping initialization.");
      }
    };
  }
}
