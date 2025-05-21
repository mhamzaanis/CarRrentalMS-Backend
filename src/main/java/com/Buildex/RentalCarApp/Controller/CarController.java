package com.Buildex.RentalCarApp.Controller;

import com.Buildex.RentalCarApp.Entities.Booking;
import com.Buildex.RentalCarApp.Entities.Car;
import com.Buildex.RentalCarApp.Repositories.BookingRepository;
import com.Buildex.RentalCarApp.Repositories.CarRepository;
import com.Buildex.RentalCarApp.Services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public CarController(CarService carService, CarRepository carRepository, BookingRepository bookingRepository) {
        System.out.println("Initializing CarController with carRepository: " + (carRepository != null));
        System.out.println("Initializing CarController with bookingRepository: " + (bookingRepository != null));
        this.carService = carService;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        if (carRepository == null || bookingRepository == null) {
            System.err.println("One or more repositories are null during CarController initialization!");
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateCarAvailability(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));
        car.setAvailable(payload.get("isAvailable"));
        carRepository.save(car);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        Optional<Car> existingCar = carService.getCarById(id);
        if (existingCar.isPresent()) {
            Car car = existingCar.get();
            car.setNoPlate(updatedCar.getNoPlate());
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setPricePerDay(updatedCar.getPricePerDay());
            car.setAvailable(updatedCar.isAvailable());
            return ResponseEntity.ok(carService.saveCar(car));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.saveCar(car);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/noPlate/{noPlate}")
    public ResponseEntity<Car> getCarByNoPlate(@PathVariable String noPlate) {
        return carService.getCarByNoPlate(noPlate)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public List<Car> getAvailableCars() {
        if (carRepository == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "CarRepository is not initialized");
        }
        List<Car> availableCars = carRepository.findByAvailable(true);

        // Fetch all bookings
        if (bookingRepository == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "BookingRepository is not initialized");
        }
        List<Booking> allBookings = bookingRepository.findAll();

        // Find car IDs with active bookings (end date on or after today)
        List<Long> bookedCarIds = allBookings.stream()
                .filter(b -> b.getCar() != null && b.getEndDate() != null &&
                        (b.getEndDate().isAfter(LocalDate.now()) || b.getEndDate().isEqual(LocalDate.now())))
                .map(b -> b.getCar().getId())
                .collect(Collectors.toList());

        // Exclude cars with active bookings
        return availableCars.stream()
                .filter(car -> !bookedCarIds.contains(car.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<Car> getAllCars() {
        System.out.println("Fetching all cars...");
        List<Car> cars = carService.getAllCars();
        System.out.println("Cars fetched: " + cars);
        return cars;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}