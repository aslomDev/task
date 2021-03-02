package com.task.repository;

import com.task.entity.Customer;
import com.task.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByPhone(String phone);
}
