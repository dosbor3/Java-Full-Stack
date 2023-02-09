package com.sg.bullsandcows.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Round {
    private int id;
    private int guess;
    private LocalDateTime time_of_guess;
    private String result;
    private int gameId;

    public Round() {
        this.time_of_guess = LocalDateTime.now();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public LocalDateTime getTime_of_guess() {
        return time_of_guess;
    }

    public void setTime_of_guess(LocalDateTime time_of_guess) {
        this.time_of_guess = time_of_guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getGameId() { return gameId; }

    public void setGameId(int gameId) { this.gameId = gameId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return guess == round.guess && time_of_guess.equals(round.time_of_guess) && result.equals(round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guess, time_of_guess, result);
    }

    @Override
    public String toString() {
        return "Round{" +
                "guess=" + guess +
                ", time_of_guess=" + time_of_guess +
                ", result='" + result + '\'' +
                '}';
    }
}
