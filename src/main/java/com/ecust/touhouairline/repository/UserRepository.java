package com.ecust.touhouairline.repository;

import com.ecust.touhouairline.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByUserNameAndPassword(String UserName, String Password);
}
