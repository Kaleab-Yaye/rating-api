package com.rating.api.db;

import static org.assertj.core.api.Assertions.assertThat;

import com.rating.api.domain.MockUser;
import com.rating.api.repository.MockUserRepo;
import com.rating.api.service.MockUserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class DataBaseTest {
  @ServiceConnection
  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

  @Autowired MockUserRepo mockUserRepo;
  @Autowired MockUserService mockUserService;

  @Test
  public void testEntityCreation() {
    MockUser mockUser = new MockUser();
    mockUser.setName("kaleab");
    mockUser.setRated(4);

    MockUser savedUser = mockUserRepo.save(mockUser);
    Long savedUSerId = savedUser.getId();

    // now we see if we can access that database

    Optional<MockUser> retrievedOptionalUser = mockUserRepo.findById(savedUSerId);
    MockUser retrievedUSer = retrievedOptionalUser.get();
    retrievedUSer.setName("abebe");
    mockUserService.updateUser(retrievedUSer);

    // now for the assertion test

    Optional<MockUser> toBeAssertedMockUser = mockUserRepo.findById(savedUSerId);
    assertThat(toBeAssertedMockUser.get().getName()).isEqualTo("abebe");
  }
}
