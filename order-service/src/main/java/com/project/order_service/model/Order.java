package com.project.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="t_order")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

    @OneToMany(
            cascade=CascadeType.ALL)
    private List<OderLineItems> OderLineItem;


}
