package com.example.players.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PlayerStatusService {

    @Value("${player_status}")
    private String statusString;

    public List<String> getAllStatuses() {
        return Arrays.asList(statusString.split(","));
    }
}

