package client;

import java.util.List;
import java.util.Objects;

public class TeamStats extends Stats{
    private String teamName;
    private List<String> drivers;

    public TeamStats(){
        super();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }

    public static TeamStats getTeamStats(String season, String name){
        TeamStats stats = new TeamStats();
        APIConnection.getTeamStats(season, name, stats);
        return stats;
    }

    @Override
    public String toString() {
        return "TeamStats:" +
                "\n teamName " + teamName +
                "\n drivers= " + drivers
                 + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TeamStats teamStats = (TeamStats) o;
        return Objects.equals(teamName, teamStats.teamName) && Objects.equals(drivers, teamStats.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teamName, drivers);
    }
}
