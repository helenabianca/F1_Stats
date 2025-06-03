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

    /**
     * retrieves the data for a team during a season
     * @param season specified season
     * @param name specified team name
     * @return data for that team
     */
    public static TeamStats getTeamStats(String season, String name){
        TeamStats stats = new TeamStats();
        APIConnection.getTeamStats(season, name, stats);
        APIConnection.getDriversForTheTeam(season, name, stats);
        String driver1 = stats.getDrivers().get(0).split(" ")[1].toLowerCase();
        String driver2 = stats.getDrivers().get(1).split(" ")[1].toLowerCase();
        DriverStats driver1Stats = DriverStats.getDriverStats(season , driver1);
        DriverStats driver2Stats = DriverStats.getDriverStats(season , driver2);
        System.out.println(driver1Stats);
        System.out.println(driver2Stats);
        stats.setNumberOfDNF(driver1Stats.getNumberOfDNF() + driver2Stats.getNumberOfDNF());
        stats.setNumberOfDNS(driver1Stats.getNumberOfDNS() + driver2Stats.getNumberOfDNS());
        stats.setNumberOfDSQ(driver1Stats.getNumberOfDSQ() + driver2Stats.getNumberOfDSQ());
        stats.setNumberOfPodiums(driver1Stats.getNumberOfPodiums() + driver2Stats.getNumberOfPodiums());
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
