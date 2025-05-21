package com.Buildex.RentalCarApp.Services;

import com.Buildex.RentalCarApp.Entities.Booking;
import com.Buildex.RentalCarApp.Entities.Car;
import com.Buildex.RentalCarApp.Entities.User;
import com.Buildex.RentalCarApp.Repositories.BookingRepository;
import com.Buildex.RentalCarApp.Repositories.CarRepository;
import com.Buildex.RentalCarApp.Repositories.UserRepository;
import com.Buildex.RentalCarApp.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    @Autowired private CarRepository carRepo;
    @Autowired private UserRepository userRepo;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public Booking saveBooking(BookingModel bookings) {
        Booking booking = new Booking();
        Optional<Car> oCar = carRepo.findById(bookings.getCarId());
        if (oCar.isPresent() && oCar.get().isAvailable()) {
            booking.setCar(oCar.get());
            Optional<User> oUser = userRepo.findById(bookings.getUserId());
            oUser.ifPresent(booking::setUser);
            booking.setStartDate(bookings.getStartDate());
            booking.setEndDate(bookings.getEndDate());
            booking.setPaid(bookings.isPaid());
            booking.setTotalAmount(bookings.getTotalAmount());
            oCar.get().setAvailable(false);
            carRepo.save(oCar.get());
            return bookingRepository.save(booking);
        } else {
            throw new IllegalStateException("Car is not available for booking.");
        }
    }

    public Booking setBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findByIdWithRelations(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllWithRelations();
    }

    @Transactional
    public void deleteBooking(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findByIdWithRelations(id);
        if (!bookingOpt.isPresent()) {
            throw new IllegalArgumentException("Booking with ID " + id + " does not exist.");
        }
        Booking booking = bookingOpt.get();
        Car car = booking.getCar();
        if (car != null) {
            car.setAvailable(true);
            carRepo.save(car);
        }
        bookingRepository.deleteById(id);
    }
}