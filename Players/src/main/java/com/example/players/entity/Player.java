package com.example.players.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate dayOfBirth;
    private String experience;
    private String position;
    private String avatar;
    private String status;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}

