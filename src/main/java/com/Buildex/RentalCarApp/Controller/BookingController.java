package com.Buildex.RentalCarApp.Controller;

import com.Buildex.RentalCarApp.Entities.Booking;
import com.Buildex.RentalCarApp.Services.BookingService;
import com.Buildex.RentalCarApp.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PutMapping("/{id}/{isPaid}")
    public ResponseEntity<Booking> updatePaymentStatus(@PathVariable Long id, @PathVariable boolean isPaid) {
        Optional<Booking> existingBooking = bookingService.getBookingById(id);
        if (existingBooking.isPresent()) {
            existingBooking.get()
                    .setPaid(isPaid);
            return ResponseEntity.ok(bookingService.setBooking(existingBooking.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingModel booking) {
        Booking savedBooking = bookingService.saveBooking(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }


    @GetMapping
    public List<Booking> getAllBookings() {
        System.out.println("Fetching all bookings...");
        List<Booking> bookings = bookingService.getAllBookings();
        System.out.println("Bookings fetched: " + bookings);
        return bookings;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error deleting booking with ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}