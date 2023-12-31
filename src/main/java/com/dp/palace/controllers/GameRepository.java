package com.dp.palace.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.dp.palace.model.GameData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {
    private static final String FILE_PATH = "game_data.json";

    public static void saveGameData(UUID gameId, GameData gameData) {
        try {
            Map<String, GameData> dataMap = new HashMap<>();
            byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));

            if (jsonData.length > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                dataMap = objectMapper.readValue(jsonData, Map.class);
            }

            dataMap.put(gameId.toString(), gameData);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOutput = objectMapper.writeValueAsString(dataMap);

            Files.write(Paths.get(FILE_PATH), jsonOutput.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameData loadGameData(UUID gameId) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));

            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, GameData>> typeReference = new TypeReference<Map<String, GameData>>() {
            };
            Map<String, GameData> dataMap = objectMapper.readValue(jsonData, typeReference);

            GameData gameData = (GameData) dataMap.get(gameId.toString());
            return gameData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
