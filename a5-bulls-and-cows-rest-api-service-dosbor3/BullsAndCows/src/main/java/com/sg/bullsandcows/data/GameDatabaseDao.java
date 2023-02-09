package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class GameDatabaseDao implements GameDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /*
    This method is set up to return a Game object, the idea being this is that the Game we pass in does
    not have an ID and the one returned will have the ID that the database assigns it. Because
    of this, we will see a new annotation here, @Transactional: which  means every query run against
    the database in this method is part of a single transaction, so if any of the queries fail, the
    program will roll back all of them. It also means they all run together with no other queries
    getting in the way, which is what we are taking advantage of to get the assigned ID out.

    We have to create our query String and use the "update" method to run it with the necessary data.
    After running the query, we need to run a special query to get the ID from the database.
    LAST_INSERT_ID() is a function built into MySQL that returns the ID of the most recent row inserted
    into the database. By including the query as part of the transaction, the INSERT the transaction performs
    is the one we will get the ID for.
    We can now put that ID into the Game object and return Game.
     */
    @Override
    @Transactional
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer, finished) VALUES(?,?)";
        jdbcTemplate.update(INSERT_GAME,
                game.getAnswer(),
                game.isFinished());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setId(newId);

        return game;
    }

    //Same description as addGame(Game game){}
    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(guess, time_of_guess, result, gameId) VALUES(?,?,?,?)";
        jdbcTemplate.update(INSERT_ROUND,
                round.getGuess(),
                round.getTime_of_guess(),
                round.getResult(),
                round.getGameId());


        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);

        return round;
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES_FINISHED = "SELECT id, answer, finished FROM game;";
        return jdbcTemplate.query(SELECT_ALL_GAMES_FINISHED, new GameMapper());
    }

    @Override
    public List<Round> getAllRounds(int id) {
        //final String SELECT_ALL_ROUNDS = "SELECT id, guess, time_of_guess, result, gameId FROM round WHERE gameId = ? ";
        final String SELECT_ALL_ROUNDS = "SELECT id, guess, time_of_guess, result, gameId FROM round WHERE gameId = ? ORDER BY time_of_guess ASC";

        return jdbcTemplate.query(SELECT_ALL_ROUNDS, new RoundMapper(), id);
    }
    @Override
    public Game findGameById(int id) {
        final String SELECT_GAME_BY_ID_FINISHED = "SELECT id, answer, finished "
                + "FROM game WHERE id = ?;";
        return jdbcTemplate.queryForObject(SELECT_GAME_BY_ID_FINISHED, new GameMapper(), id);
    }
    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET finished = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_GAME,
                game.isFinished());
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setAnswer(rs.getInt("answer"));
            game.setFinished((rs.getBoolean("finished")));
            return game;
        }
    }

    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setGuess(rs.getInt("guess"));
            round.setTime_of_guess(rs.getTimestamp("time_of_guess").toLocalDateTime());
            round.setResult((rs.getString("result")));
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }
}
