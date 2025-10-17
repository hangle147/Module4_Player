package com.example.players.dto;

import com.example.players.entity.Team;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PlayerDto {
    private int id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfBirth;
    private String experience;
    private String position;
    private String avatar;
    private Team team;
    private String status;

    public PlayerDto() {
    }

    public PlayerDto(int id, String name, LocalDate dayOfBirth, String experience, String position, String avatar, Team team, String status) {
        this.id = id;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.experience = experience;
        this.position = position;
        this.avatar = avatar;
        this.team = team;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
