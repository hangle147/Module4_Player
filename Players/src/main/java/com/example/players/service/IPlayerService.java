package com.example.players.service;

import com.example.players.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IPlayerService {
    List<Player> findAll();
    Page<Player> searchPlayers(String keyword, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Player findById(int id);
    boolean add(Player player);
    void delete(int id);
    Long countStatus(String status);
}
