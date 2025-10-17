package com.example.players.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PositionService {
    @Value("${positions}")
    private String positionString;

    public List<String> getAllPositions() {
        return Arrays.asList(positionString.split(","));
    }
}
