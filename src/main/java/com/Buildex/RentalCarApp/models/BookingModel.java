package com.Buildex.RentalCarApp.models;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BookingModel {
    private Long id;//booking id auto generated
    private Long userId;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isPaid;
    private double totalAmount;
}

