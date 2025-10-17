package com.example.players.repository;

import com.example.players.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepository extends JpaRepository<Team, Integer> {
    Page<Team> findAllByNameContaining(String name, Pageable pageable);
}
