package com.epam.repository;

import com.epam.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
        List<User> findAllByRole(String role);
        Optional<User> findUserByUsername(String username);
        Boolean existsByUsername(String email);
}
