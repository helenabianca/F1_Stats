package client;

import java.util.Objects;

public class Stats {
    private int points;
    private int position;
    private int numberOfWins;
    private int numberOfPodiums;
    private double averagePointsPerRace;
    private double averageFinishPosition;
    private int numberOfDNF;
    private int numberOfDNS;
    private int numberOfDSQ;

    public Stats() {
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public int getNumberOfPodiums() {
        return numberOfPodiums;
    }

    public void setNumberOfPodiums(int numberOfPodiums) {
        this.numberOfPodiums = numberOfPodiums;
    }

    public double getAveragePointsPerRace() {
        return averagePointsPerRace;
    }

    public void setAveragePointsPerRace(double averagePointsPerRace) {
        this.averagePointsPerRace = averagePointsPerRace;
    }

    public double getAverageFinishPosition() {
        return averageFinishPosition;
    }

    public void setAverageFinishPosition(double averageFinishPosition) {
        this.averageFinishPosition = averageFinishPosition;
    }

    public int getNumberOfDSQ() {
        return numberOfDSQ;
    }

    public void setNumberOfDSQ(int numberOfDSQ) {
        this.numberOfDSQ = numberOfDSQ;
    }

    public int getNumberOfDNF() {
        return numberOfDNF;
    }

    public void setNumberOfDNF(int numberOfDNF) {
        this.numberOfDNF = numberOfDNF;
    }

    public int getNumberOfDNS() {
        return numberOfDNS;
    }

    public void setNumberOfDNS(int numberOfDNS) {
        this.numberOfDNS = numberOfDNS;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "\n numberOfWins= " + numberOfWins +
                "\n numberOfPodiums= " + numberOfPodiums +
                "\n averagePointsPerRace= " + averagePointsPerRace +
                "\n averageFinishPosition= " + averageFinishPosition +
                "\n numberOfDNF= " + numberOfDNF +
                "\n numberOfDNS= " + numberOfDNS +
                "\n numberOfDSQ= " + numberOfDSQ +
                "\n points= " + points +
                "\n position= " + position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return numberOfWins == stats.numberOfWins && numberOfPodiums == stats.numberOfPodiums && Double.compare(averagePointsPerRace, stats.averagePointsPerRace) == 0 && Double.compare(averageFinishPosition, stats.averageFinishPosition) == 0 && numberOfDNF == stats.numberOfDNF && numberOfDNS == stats.numberOfDNS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfWins, numberOfPodiums, averagePointsPerRace, averageFinishPosition, numberOfDNF, numberOfDNS);
    }
}
