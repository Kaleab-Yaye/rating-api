package com.rating.api.repository;

import com.rating.api.domain.MockUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockUserRepo extends JpaRepository<MockUser, Long> {}
