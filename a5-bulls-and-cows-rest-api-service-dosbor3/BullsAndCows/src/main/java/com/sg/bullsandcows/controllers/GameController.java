package com.sg.bullsandcows.controllers;


import com.sg.bullsandcows.models.*;
import com.sg.bullsandcows.data.*;
import java.util.*;

import com.sg.bullsandcows.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Component
public class GameController {
    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    // Starts a game, generates an answer, and sets the correct status.
    // Should return a 201 CREATED message as well as the created gameId.
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int create() {
        Game game = service.beginGame();
        int gameId = game.getId();
        return gameId ;
    }

    //Makes a guess by passing the guess and gameId in as JSON. The program must calculate
    //the results of the guess and mark the game finished if the guess is correct. It returns
    //the Round object with the results filled in.
    @PostMapping("/guess/{guess}/{gameId}")
    public Round guess(@PathVariable int guess, @PathVariable int gameId) {
        Round round = new Round();
        round.setGuess(guess);
        round.setGameId(gameId);
        return service.makeAGuess(round);
    }

    //Returns a list of all games. Be sure in-progress games do not display their answer.
    @GetMapping("/game")
    public List<Game> allGames() {
        return service.getAllGames();
    }

    //Returns a list of rounds for the specified game sorted by time.
    @GetMapping("/rounds/{gameId}")
    public List<Round> allRounds(@PathVariable int gameId){
        return service.getAllRoundsSorted(gameId);
    }

    //Returns a specific game based on ID. Be sure in-progress games do not display their answer.
    @GetMapping("/game/{gameId}")
    public Game findGameById(@PathVariable int id) {
        return service.getAGame(id);
    }
}
