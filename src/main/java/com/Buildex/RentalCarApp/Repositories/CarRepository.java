package com.Buildex.RentalCarApp.Repositories;

import com.Buildex.RentalCarApp.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByAvailable(boolean available);
    Optional<Car> findByNoPlate(String noPlate);
}
