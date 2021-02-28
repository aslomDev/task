package com.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.xml.soap.Text;
import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 14)
    private String name;

    @Column(name = "country", length = 13)
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 50)
    private String phone;


}
