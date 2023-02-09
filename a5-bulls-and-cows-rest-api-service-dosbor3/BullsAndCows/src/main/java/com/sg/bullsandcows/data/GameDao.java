package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.Round;

import java.util.*;

public interface GameDao {
    Game addGame(Game game);
    Round addRound(Round round);
    List<Game> getAllGames();
    List<Round> getAllRounds(int id);
    Game findGameById(int id);

    void updateGame(Game game);
//    void updateRound(Round round);


}
