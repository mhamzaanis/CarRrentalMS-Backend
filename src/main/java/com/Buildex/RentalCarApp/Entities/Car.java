package com.Buildex.RentalCarApp.Entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private boolean available;
    @Column(nullable = false)
    private double pricePerDay;
    @Column(nullable = false)
    private String noPlate;
}