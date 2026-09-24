package com.example.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_table")
public class AppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appID;
    private String name;
    private Long amount;
}
