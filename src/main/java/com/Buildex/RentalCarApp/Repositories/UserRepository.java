package com.Buildex.RentalCarApp.Repositories;

import com.Buildex.RentalCarApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods (optional) can be added here
    User findByEmail(String email);
}
