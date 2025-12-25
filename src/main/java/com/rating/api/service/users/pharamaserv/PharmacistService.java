package com.rating.api.service.users.pharamaserv;

import com.rating.api.domain.*;
import com.rating.api.dto.register.RegisterPharmacistRequest;
import com.rating.api.dto.register.admindto.pharmacist.AddPharmacistToPharmacyRequest;
import com.rating.api.dto.register.pharamaciest.OrderRequest;
import com.rating.api.repository.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PharmacistService {
  private final PasswordEncoder passwordEncoder;
  private final PharmacistRepo pharmacistRepo;
  @Autowired PharmacyRepo pharmacyRepo;
  @Autowired MedicineRepo medicineRepo;
  @Autowired MedBatchRepo medBatchRepo;
  @Autowired OrderRepo orderRepo;
  @Autowired OrderListRepo orderListRepo;

  PharmacistService(PasswordEncoder passwordEncoder, PharmacistRepo pharmacistRepo) {
    this.passwordEncoder = passwordEncoder;
    this.pharmacistRepo = pharmacistRepo;
  }

  public void register(RegisterPharmacistRequest registerPharmacistRequest) {
    Pharmacist pharmacist = new Pharmacist();
    pharmacist.setName(registerPharmacistRequest.name());
    pharmacist.setEmail(registerPharmacistRequest.email());
    pharmacist.setIsAdmin(registerPharmacistRequest.isAdmin());
    pharmacist.setPharmacy(registerPharmacistRequest.pharmacy());
    pharmacist.setType(registerPharmacistRequest.type());
    pharmacist.setPassword(
        passwordEncoder.encode(
            registerPharmacistRequest.password())); // here is where the password is getting hashed

    pharmacistRepo.save(pharmacist);
  }

  public void addPharmacistToPharmacy(
      AddPharmacistToPharmacyRequest addPharmacistToPharmacyRequest) {
    Pharmacist pharmacist =
        pharmacistRepo
            .getPharmacistsByEmail(addPharmacistToPharmacyRequest.email())
            .orElseThrow(); // first extract the pharmacist
    pharmacist.setPharmacy(
        pharmacistRepo
            .getPharmacistsById(addPharmacistToPharmacyRequest.pharmacyId())
            .get()
            .getPharmacy()); // let hibernate handel the table joining & we know the pharmacy exits
    // so
    // no need for check logic here
  }

  @Transactional
  public void putOrder(OrderRequest orderRequest) {
    Pharmacy pharmacy =
        pharmacyRepo.findPharmacyByPharmacyId(orderRequest.pharmacyID()).orElseThrow();
    Pharmacist pharmacist =
        pharmacistRepo.getPharmacistsById(orderRequest.pharmacistID()).orElseThrow();
    Long balance = pharmacy.getBalance();
    Map<Long, Integer> orderList = orderRequest.orderListFromPharmacist();

    Order order = new Order();
    order.setPharmacy(pharmacy);
    order.setPharmacist(pharmacist);
    order.setOrderDate(OffsetDateTime.now());

    AtomicLong atomicBalance = new AtomicLong(balance); // to make it usable inside a lambda

    orderList.forEach(
        (key, value) -> {
          Medicine medicine = medicineRepo.findMedicineById(key).orElseThrow();
          Integer holderValue = value;

          if (medicine.getPrice() * value > atomicBalance.get()) {
            throw new RuntimeException(
                "Not enouph Balence"); // must be changed in the future to customised exption
          }

          if ((medBatchRepo.getAmountPresent(key, LocalDate.now()).orElseThrow().intValue()
              < value)) {
            throw new RuntimeException("not enouph in stoke ");
          }

          atomicBalance.set(
              atomicBalance.get()
                  - (medicine.getPrice()
                      * value)); // new value has to be updated for the pharmacy balence
          int pageNumber = 0; // to tracch the number of elements we need per page
          while (value > 0) {
            Pageable pageable = PageRequest.of(pageNumber, 10);
            List<MedBatch> medBatches =
                medBatchRepo.findEnoughBatches(
                    key, LocalDate.now(), pageable); // ge thte page of our need
            for (MedBatch medBatch : medBatches) {
              if (medBatch.getAmountPresent().intValue()
                  >= value) { // this handel when the first batch statsfies the need also break the
                // while loop
                medBatch.setAmountPresent(medBatch.getAmountPresent() - value);
                value = 0;
              }
              if (medBatch.getAmountPresent().intValue()
                  < value) { // this handles when the current batch is smaller than the needed one
                value = value - medBatch.getAmountPresent().intValue();

                medBatch.setAmountPresent(
                    (long)
                        0); // the while loop will contnie if the batch in the list didn't satisfy
                // it
              }
            }
            pageNumber++;
          }
          // we need to create the order List object now
          OrderList orderList1 = new OrderList();
          orderList1.setAmountOrdered(holderValue);
          orderList1.setMedicine(medicine);
          orderList1.setOrder(order);
        });

    pharmacy.setBalance(atomicBalance.get());
  }
}
