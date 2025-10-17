package com.example.players.service;

import com.example.players.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeamService {
    List<Team> findAll();
    Page<Team> findAllByName(String name, Pageable pageable);
    Team findById(int id);
    boolean add(Team team);
    void delete(int id);
}
