package com.edu.ifpb.poo.brasileiraoscraping;

public class Team {
    private final String position;
    private final String name;
    private final String points;
    private final String gamesPlayed;
    private final String wins;
    private final String draws;
    private final String losses;
    private final String goalsFor;
    private final String goalsAgainst;
    private final String goalDifference;
    private final String performance;
    private final String lastGames;

    public Team(String position, String name, String points, String gamesPlayed, String wins, String draws, String losses, String goalsFor, String goalsAgainst, String goalDifference, String performance, String lastGames) {
        this.position = position;
        this.name = name;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.performance = performance;
        this.lastGames = lastGames;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return points;
    }

    public String getGamesPlayed() {
        return gamesPlayed;
    }

    public String getWins() {
        return wins;
    }

    public String getDraws() {
        return draws;
    }

    public String getLosses() {
        return losses;
    }

    public String getGoalsFor() {
        return goalsFor;
    }

    public String getGoalsAgainst() {
        return goalsAgainst;
    }

    public String getGoalDifference() {
        return goalDifference;
    }

    public String getPerformance() {
        return performance;
    }

    public String getLastGames() {
        return lastGames;
    }

    @Override
    public String toString() {
        return String.format(
                "%-5s %-20s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-6s %s",
                position, name, points, gamesPlayed, wins, draws, losses, goalsFor,
                goalsAgainst, goalDifference, performance, lastGames
        );
    }
}