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
        List<String> drivers = stats.getDrivers();
        int numberOfPodiums =0;
        int numberOfDNF =0;
        int numberOfDNS =0;
        int numberOfDSQ = 0;
        for(String driver : drivers){
            String driverId = driver.split(" ")[1].toLowerCase();
            DriverStats driverStats = DriverStats.getDriverStats(season , driverId);
            numberOfPodiums += driverStats.getNumberOfPodiums();
            numberOfDNF += driverStats.getNumberOfDNF();
            numberOfDSQ += driverStats.getNumberOfDSQ();
            numberOfDNS += driverStats.getNumberOfDNS();
        }
        stats.setNumberOfDNF(numberOfDNF);
        stats.setNumberOfDNS(numberOfDNS);
        stats.setNumberOfDSQ(numberOfDSQ);
        stats.setNumberOfPodiums(numberOfPodiums);
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
