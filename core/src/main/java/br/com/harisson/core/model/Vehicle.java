package br.com.harisson.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vehicle implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "The field 'price' is mandatory")
    @Column(nullable = false, length = 22)
    private Double price;

    @NotNull(message = "The field 'year' is mandatory")
    @Column(nullable = false, length = 4)
    private int year;

    @NotNull(message = "The field 'vehicleType' is mandatory")
    @Column(nullable = false, length = 45)
    private String vehicleType;

    @NotNull(message = "The field 'fuelType' is mandatory")
    @Column(nullable = false, length = 45)
    private String fuelType;

    @NotNull(message = "The field 'transmissionType' is mandatory")
    @Column(nullable = false, length = 45)
    private String transmissionType;

    @NotNull(message = "The field 'vehicleManufacturer' is mandatory")
    @Column(nullable = false, length = 45)
    private String vehicleManufacturer;

    @NotNull(message = "The field 'model' is mandatory")
    @Column(nullable = false, length = 45)
    private String model;

    @NotNull(message = "The field 'description' is mandatory")
    @Column(nullable = false, length = 200)
    private String description;

    @Column(length = 60)
    private String imagesFolderDirectory;

    @Column(length = 60)
    private String thumbnailName;

    @Column
    private Long buyerId;

    @Column(columnDefinition = "boolean default false")
    private boolean isSold;
}
