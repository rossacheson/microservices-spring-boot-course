package com.rossacheson.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {
    @Id
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private Long customerId;
}
