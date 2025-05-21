package com.Buildex.RentalCarApp.Services;

import com.Buildex.RentalCarApp.Entities.Car;
import com.Buildex.RentalCarApp.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Optional<Car> getCarByNoPlate(String noPlate) {
        return carRepository.findByNoPlate(noPlate);
    }

    public List<Car> getAvailableCars() {
        return carRepository.findByAvailable(true);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}