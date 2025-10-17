package com.example.players.repository;

import com.example.players.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Integer> {
    long countByStatus(String status);

    Page<Player> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Player> findAllByNameContainingIgnoreCaseAndDayOfBirthBetween(String name, LocalDate start, LocalDate end, Pageable pageable);
    Page<Player> findAllByNameContainingIgnoreCaseAndDayOfBirthAfter(String name, LocalDate start, Pageable pageable);
    Page<Player> findAllByNameContainingIgnoreCaseAndDayOfBirthBefore(String name, LocalDate end, Pageable pageable);

    Page<Player> findAllByDayOfBirthBetween(LocalDate start, LocalDate end, Pageable pageable);
    Page<Player> findAllByDayOfBirthAfter(LocalDate start, Pageable pageable);
    Page<Player> findAllByDayOfBirthBefore(LocalDate end, Pageable pageable);
}
