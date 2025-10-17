package com.example.players.service;

import com.example.players.entity.Team;
import com.example.players.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements ITeamService {
    @Autowired
    private ITeamRepository teamRepository;

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Page<Team> findAllByName(String name, Pageable pageable) {
        return teamRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Team findById(int id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Override
    public boolean add(Team team) {
        return teamRepository.save(team) != null;
    }

    @Override
    public void delete(int id) {
        teamRepository.deleteById(id);
    }
}
