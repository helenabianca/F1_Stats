package client;

import java.util.Objects;

public class DriverStats extends Stats{
    private String name;
    private String teamName;
    private int driverNumber;
    private boolean success;

    public DriverStats() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

    public boolean getSuccess(){
        return success;
    }
    /**
     * retrieves the data for a driver during a season
     * @param season specified season
     * @param driverId specified driverId
     * @return stats for that driver
     */
    public DriverStats getDriverStats(String season, String driverId) {
        DriverStats driver = new DriverStats();
        boolean b1 = APIConnection.getDriverBasicStats(season, driverId, driver);
        boolean b2 = APIConnection.getOtherStats(season, driverId, driver);
        System.out.println(b1);
        System.out.println(b2);
        this.success= b1 && b2;
        System.out.println(this.success);
        return driver;
    }

    @Override
    public String toString() {
        return "DriverStats:" +
                "\n name= " + name +
                "\n teamName= " + teamName +
                "\n driverNumber= " + driverNumber + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DriverStats that = (DriverStats) o;
        return Double.compare(driverNumber, that.driverNumber) == 0 && Objects.equals(name, that.name) && Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, teamName, driverNumber);
    }
}
