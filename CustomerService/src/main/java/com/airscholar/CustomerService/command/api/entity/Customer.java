package com.airscholar.CustomerService.command.api.entity;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
//    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cardDetails",  referencedColumnName = "Id")
    private CardDetails cardDetails;
}
