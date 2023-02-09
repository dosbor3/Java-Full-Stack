package com.sg.bullsandcows.service;

import com.sg.bullsandcows.models.*;

import java.util.List;

public interface GameService {
    /**
     * This method will generate an answer and store it in the game table of the gamedb, and sets the
     * finished boolean value to false. Return a 201 CREATED message and the gameId
     * @return an int gameId
     */
    Game beginGame();

    /**
     * This method retrieves the guess and gameId passed in as JSON and calculate the results of the guess.  Based on the
     * calculated results, mark the game finished if the guess is correct.  This method will return the Round object with
     * the results filled in
     * @return Round object with calculated results filled in
     */
    Round makeAGuess(Round round);

    /**
     * This method will check the game status, and based on the status, will return a list of Games
     * if the status is in progress, the answer field will be hidden, otherwise it will return all
     * properties of the Game object
     * @return A list of Game Objects
     */
    List<Game> getAllGames();

    /**
     * This method will retrieve a game based on the specified gameId, if the game status is in progress,
     * the answer field will be hidden
     * @return
     */
    Game getAGame(int id);

    /**
     * This method will return all rounds sorted by timestamp for a given gameId
     * @return A list of Round objects sorted by timestamp
     */
    List<Round> getAllRoundsSorted(int gameId);

}
