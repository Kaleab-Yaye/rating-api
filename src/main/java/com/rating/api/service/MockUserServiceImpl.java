package com.rating.api.service;

import com.rating.api.domain.MockUser;
import com.rating.api.repository.MockUserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockUserServiceImpl implements MockUserService{

    MockUserRepo mockUserRepo;

    @Autowired
    MockUserServiceImpl(MockUserRepo mockUserRepo){
        this.mockUserRepo = mockUserRepo;
    }

    @Override
    public void updateUser(MockUser mockUser){

        mockUserRepo.save(mockUser);

    }
}
