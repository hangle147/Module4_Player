package com.example.players.service;

import com.example.players.aop.CheckPlayerLimit;
import com.example.players.entity.Player;
import com.example.players.repository.IPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlayerService implements IPlayerService {
    @Autowired
    private IPlayerRepository playerRepository;

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Page<Player> searchPlayers(String keyword, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        boolean hasKeyword = (keyword != null && !keyword.isBlank());
        boolean hasStart = (startDate != null);
        boolean hasEnd = (endDate != null);

        if (!hasKeyword && !hasStart && !hasEnd) {
            return playerRepository.findAll(pageable);
        }

        if (hasKeyword && hasStart && hasEnd) {
            return playerRepository.findAllByNameContainingIgnoreCaseAndDayOfBirthBetween(keyword, startDate, endDate, pageable);
        }

        if (hasKeyword && !hasStart && !hasEnd) {
            return playerRepository.findAllByNameContainingIgnoreCase(keyword, pageable);
        }

        if (!hasKeyword && hasStart && hasEnd) {
            return playerRepository.findAllByDayOfBirthBetween(startDate, endDate, pageable);
        }

        if (hasKeyword && hasStart) {
            return playerRepository.findAllByNameContainingIgnoreCaseAndDayOfBirthAfter(keyword, startDate, pageable);
        }
        if (hasKeyword && hasEnd) {
            return playerRepository.findAllByNameContainingIgnoreCaseAndDayOfBirthBefore(keyword, endDate, pageable);
        }

        if (hasStart) {
            return playerRepository.findAllByDayOfBirthAfter(startDate, pageable);
        }
        if (hasEnd) {
            return playerRepository.findAllByDayOfBirthBefore(endDate, pageable);
        }

        return playerRepository.findAll(pageable);
    }

    @Override
    public Player findById(int id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean add(Player player) {
        return playerRepository.save(player) != null;
    }

    @Override
    public void delete(int id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Long countStatus(String status) {
        return playerRepository.countByStatus(status);
    }

    @CheckPlayerLimit
    public Player updateStatus(Player player) {
        if ("DU_BI".equals(player.getStatus())) {
            player.setStatus("DA_DANG_KI");
        } else if ("DA_DANG_KI".equals(player.getStatus())) {
            player.setStatus("DU_BI");
        }
        return playerRepository.save(player);
    }
}
