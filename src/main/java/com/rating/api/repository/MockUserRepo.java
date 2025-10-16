package com.rating.api.repository;

import com.rating.api.domain.MockUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MockUserRepo extends JpaRepository<MockUser, Long> {
}
