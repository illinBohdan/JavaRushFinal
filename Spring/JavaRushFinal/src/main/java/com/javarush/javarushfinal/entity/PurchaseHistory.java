package com.javarush.javarushfinal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part")
    private Part part;
    private Integer quantity;
    private LocalDateTime purchaseDate;

}
