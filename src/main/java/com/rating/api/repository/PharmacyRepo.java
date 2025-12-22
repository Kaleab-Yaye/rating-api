package com.rating.api.repository;

import com.rating.api.domain.Pharmacy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepo extends CrudRepository<Pharmacy, Integer> {}
