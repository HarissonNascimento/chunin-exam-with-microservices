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
public class Buyer implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    private Vehicle vehicle;

    @NotNull(message = "The field 'numberPhone' is mandatory")
    @Column(nullable = false, length = 14)
    private String numberPhone;

    @NotNull(message = "The field 'name' is mandatory")
    @Column(nullable = false, length = 70)
    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean isContacted;

}
