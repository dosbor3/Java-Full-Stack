package com.sg.bullsandcows.service;

import com.sg.bullsandcows.data.GameDao;
import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GameServiceImpl implements GameService {
    private GameDao dao;

    @Autowired
    public GameServiceImpl (GameDao dao) {
        this.dao = dao;
    }


    /**
     * This method will generate an answer and store it in the game table of the gamedb, and sets the
     * finished boolean value to false. Return a 201 CREATED message and the gameid
     *
     * @return an int gameId
     */
    @Override
    public Game beginGame() {
        int rand = 0;
        String strAnswer = "";
        Map<Integer, Integer> answerMap = new HashMap<Integer, Integer>();
        //generating 4 random integers
        for(int i = 0; i < 4; i++) {
            do {
                rand = (int)(Math.random() * 9 - 1) + 1;
            } while(answerMap.containsValue(rand));
            answerMap.put(i, rand);
            strAnswer += rand;
        }

        int answer = Integer.parseInt(strAnswer);
        System.out.println(strAnswer);
        System.out.println(answer);
        Game game = new Game();
        game.setAnswer(answer);
        game.setFinished(false);
        return dao.addGame(game);
    }

    /**
     * This method retrieves the guess and gameId passed in as JSON and calculate the results of the guess.  Based on the
     * calculated results, mark the game finished if the guess is correct.  This method will return the Round object with
     * the results filled in
     *
     * @return Round object with calculated results filled in
     */
    @Override
    public Round makeAGuess(Round round) {

       Game game = dao.findGameById(round.getGameId());
        int exactMatches = 0;
        int partialMatches = 0;
        String results = String.format("%se: %s:p",exactMatches, partialMatches);

        if (round.getGuess() == game.getAnswer()) {
            dao.updateGame(game);
            exactMatches = 4;
            partialMatches = 0;
        }
        else {
            String strAnswer = String.valueOf(game.getAnswer());
            String strGuess = String.valueOf(round.getGuess());
            char[] answerArray = new char[4];
            char[] guessArray = new char[4];
            answerArray = strAnswer.toCharArray();
            guessArray = strGuess.toCharArray();

            for(int j = 0; j < answerArray.length; j++){
                for(int k = 0; k < guessArray.length; k++){
                    if( answerArray[j] == guessArray[k]) {
                        if(j == k){
                            exactMatches++;
                        }
                        else {
                            partialMatches++;
                        }
                    }
                }
            }
        }
        results = String.format("e:%s:p:%s",exactMatches, partialMatches);

        round.setResult(results);
        dao.addRound(round);
        return round;
    }

    /**
     * This method will check the game status, and based on the status, will return a list of Games
     * if the status is in progress, the answer field will be hidden, otherwise it will return all
     * properties of the Game object
     *
     * @return A list of Game Objects
     */
    @Override
    public List<Game> getAllGames() {
        List<Game> filteredList = dao.getAllGames();
        filteredList.stream()
                .filter((g) -> !g.isFinished())
                .forEach((g) -> g.setAnswer(0000));
        return filteredList;
    }

    /**
     * This method will retrieve a game based on the specified gameId, if the game status is in progress,
     * the answer field will be hidden
     *
     * @return
     */
    @Override
    public Game getAGame(int id) {
        return dao.findGameById(id);
    }

    /**
     * This method will return all rounds sorted by timestamp for a given gameId
     *
     * @return A list of Round objects sorted by timestamp
     */
    @Override
    public List<Round> getAllRoundsSorted(int gameId) {
        return dao.getAllRounds(gameId);
    }
}
