package com.atutueva.kalah.controller;

import com.atutueva.kalah.dto.GameResponse;
import com.atutueva.kalah.service.GameService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
class GameControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameService gameService;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNewGame() throws Exception {

        mockMvc.perform(post("/game")
                        .contentType(MediaType.APPLICATION_JSON))

                // status is correct
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                // id present
                .andExpect(jsonPath("$.id").isNotEmpty())

                // Player 1's next turn
                .andExpect(jsonPath("$.state.status").value("PlAYER1_TURN"))

                // player1 Board
                .andExpect(jsonPath("$.state.player1Board.pits[0].index").value(0))
                .andExpect(jsonPath("$.state.player1Board.pits[0].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[1].index").value(1))
                .andExpect(jsonPath("$.state.player1Board.pits[1].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[2].index").value(2))
                .andExpect(jsonPath("$.state.player1Board.pits[2].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[3].index").value(3))
                .andExpect(jsonPath("$.state.player1Board.pits[3].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[4].index").value(4))
                .andExpect(jsonPath("$.state.player1Board.pits[4].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[5].index").value(5))
                .andExpect(jsonPath("$.state.player1Board.pits[5].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.kalah").value(0))

                // player2 Board
                .andExpect(jsonPath("$.state.player2Board.pits[0].index").value(0))
                .andExpect(jsonPath("$.state.player2Board.pits[0].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[1].index").value(1))
                .andExpect(jsonPath("$.state.player2Board.pits[1].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[2].index").value(2))
                .andExpect(jsonPath("$.state.player2Board.pits[2].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[3].index").value(3))
                .andExpect(jsonPath("$.state.player2Board.pits[3].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[4].index").value(4))
                .andExpect(jsonPath("$.state.player2Board.pits[4].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[5].index").value(5))
                .andExpect(jsonPath("$.state.player2Board.pits[5].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.kalah").value(0));
    }

    @Test
    void getGameById() throws Exception {
        GameResponse newGame = gameService.createGame();
        String path = "/game/" + newGame.getId();

        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))

                // status is correct
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                // id present
                .andExpect(jsonPath("$.id").isNotEmpty())

                // Player 1's next turn
                .andExpect(jsonPath("$.state.status").value("PlAYER1_TURN"))

                // player1 Board
                .andExpect(jsonPath("$.state.player1Board.pits[0].index").value(0))
                .andExpect(jsonPath("$.state.player1Board.pits[0].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[1].index").value(1))
                .andExpect(jsonPath("$.state.player1Board.pits[1].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[2].index").value(2))
                .andExpect(jsonPath("$.state.player1Board.pits[2].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[3].index").value(3))
                .andExpect(jsonPath("$.state.player1Board.pits[3].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[4].index").value(4))
                .andExpect(jsonPath("$.state.player1Board.pits[4].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[5].index").value(5))
                .andExpect(jsonPath("$.state.player1Board.pits[5].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.kalah").value(0))

                // player2 Board
                .andExpect(jsonPath("$.state.player2Board.pits[0].index").value(0))
                .andExpect(jsonPath("$.state.player2Board.pits[0].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[1].index").value(1))
                .andExpect(jsonPath("$.state.player2Board.pits[1].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[2].index").value(2))
                .andExpect(jsonPath("$.state.player2Board.pits[2].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[3].index").value(3))
                .andExpect(jsonPath("$.state.player2Board.pits[3].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[4].index").value(4))
                .andExpect(jsonPath("$.state.player2Board.pits[4].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[5].index").value(5))
                .andExpect(jsonPath("$.state.player2Board.pits[5].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.kalah").value(0));
    }


    @Test
    void makeMove() throws Exception {
        GameResponse newGame = gameService.createGame();
        String path = "/game/" + newGame.getId() + "?pitIndex=1";

        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON))

                // status is correct
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                // id present
                .andExpect(jsonPath("$.id").isNotEmpty())

                // Player 2's next turn
                .andExpect(jsonPath("$.state.status").value("PlAYER2_TURN"))

                // player1 Board
                .andExpect(jsonPath("$.state.player1Board.pits[0].index").value(0))
                .andExpect(jsonPath("$.state.player1Board.pits[0].value").value(6))
                .andExpect(jsonPath("$.state.player1Board.pits[1].index").value(1))
                .andExpect(jsonPath("$.state.player1Board.pits[1].value").value(0))
                .andExpect(jsonPath("$.state.player1Board.pits[2].index").value(2))
                .andExpect(jsonPath("$.state.player1Board.pits[2].value").value(7))
                .andExpect(jsonPath("$.state.player1Board.pits[3].index").value(3))
                .andExpect(jsonPath("$.state.player1Board.pits[3].value").value(7))
                .andExpect(jsonPath("$.state.player1Board.pits[4].index").value(4))
                .andExpect(jsonPath("$.state.player1Board.pits[4].value").value(7))
                .andExpect(jsonPath("$.state.player1Board.pits[5].index").value(5))
                .andExpect(jsonPath("$.state.player1Board.pits[5].value").value(7))
                .andExpect(jsonPath("$.state.player1Board.kalah").value(1))

                // player2 Board
                .andExpect(jsonPath("$.state.player2Board.pits[0].index").value(0))
                .andExpect(jsonPath("$.state.player2Board.pits[0].value").value(7))
                .andExpect(jsonPath("$.state.player2Board.pits[1].index").value(1))
                .andExpect(jsonPath("$.state.player2Board.pits[1].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[2].index").value(2))
                .andExpect(jsonPath("$.state.player2Board.pits[2].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[3].index").value(3))
                .andExpect(jsonPath("$.state.player2Board.pits[3].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[4].index").value(4))
                .andExpect(jsonPath("$.state.player2Board.pits[4].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.pits[5].index").value(5))
                .andExpect(jsonPath("$.state.player2Board.pits[5].value").value(6))
                .andExpect(jsonPath("$.state.player2Board.kalah").value(0));
    }

    @Test
    void makeInvalidMove() throws Exception {
        String invalidId = UUID.randomUUID().toString();

        String path = "/game/" + invalidId + "?pitIndex=1";

        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON))

                // status is correct
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                // message
                .andExpect(jsonPath("$.message").value("Game wit id=" + invalidId + " Not Found"));
    }
}
