package com.sg.bullsandcows.models;

import java.util.Objects;

public class Game {
    private int id;
    private int answer;
    private boolean finished;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && answer == game.answer && finished == game.finished;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer, finished);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", answer=" + answer +
                ", finished=" + finished +
                '}';
    }
}
