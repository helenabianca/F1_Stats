package client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class APIConnection {


    /**
     * the method retrieves the basic statistics for a specific driver during a season
     * @param season specified season
     * @param driverId specified driverId
     * @param driverStats DriverStats
     */
    public static void getDriverBasicStats(String season, String driverId, DriverStats driverStats) {
        try{
            String urlString = "https://f1api.dev/api/" + season + "/drivers-championship";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();
            if(responseCode!=200){
                throw new RuntimeException("Failed : HTTP error code : "+ responseCode);
            }
            else{
                StringBuilder output = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()){
                    output.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(output));
                JSONArray driversChamp = (JSONArray) jsonObject.get("drivers_championship");
                long numberOfRaces = (long) jsonObject.get("total");
                for(Object obj: driversChamp){
                    JSONObject driver = (JSONObject) obj;
                    if(!((String)driver.get("driverId")).equals(driverId)) {
                        continue;
                    }
                    long points = (long) driver.get("points");
                    driverStats.setPoints((int) points);
                    driverStats.setAveragePointsPerRace((double) points / numberOfRaces);
                    long position = (long) driver.get("position");
                    driverStats.setPosition((int)position);
                    long wins = (long) driver.get("wins");
                    driverStats.setNumberOfWins((int)wins);
                    JSONObject driverInfo =  (JSONObject) driver.get("driver");
                    String name = (String) driverInfo.get("name");
                    String surname = (String) driverInfo.get("surname");
                    driverStats.setName(name + " " + surname);
                    long number = (long) driverInfo.get("number");
                    driverStats.setDriverNumber((int)number);
                    JSONObject team = (JSONObject) driver.get("team");
                    String teamName = (String) team.get("teamName");
                    driverStats.setTeamName(teamName);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * the method retrieves the other statistics for a driver
     * @param season specified season
     * @param driverId specified driverId
     * @param driverStats DriverStats
     */
    public static void getOtherStats(String season, String driverId, DriverStats driverStats) {
        try{
            String urlString = "https://f1api.dev/api/" + season + "/drivers/" + driverId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();
            if(responseCode!=200){
                throw new RuntimeException("Failed : HTTP error code : "+ responseCode);
            }
            else{
                StringBuilder output = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()){
                    output.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(output));
                long numberOfRaces = (long) jsonObject.get("total");
                JSONArray results = (JSONArray) jsonObject.get("results");
                Map<Integer,Integer> positions = new HashMap<Integer,Integer>();
                int numberOfPodiums=0;
                int numberOfDNF=0;
                int numberOfDNS=0;
                int numberOfDSQ=0;
                for(Object o : results){
                    JSONObject race = (JSONObject) o;
                    JSONObject result = (JSONObject) race.get("result");
                    String positionText = (String) result.get("raceTime");
                    if(positionText.contains("+") || positionText.contains("1") || positionText.contains("-")){
                        long position =(long) result.get("finishingPosition");
                        int positionValue = (int)position;
                        if(!positions.containsKey(positionValue)){
                            positions.put(positionValue,1);
                        }
                        else{
                            positions.put(positionValue,positions.get(positionValue)+1);
                        }
                        if(positionValue<=3){
                            numberOfPodiums++;
                        }
                    }
                    else{
                        long grid = (long) result.get("gridPosition");
                        if(grid==0){
                            numberOfDNS++;
                            continue;
                        }
                        switch (positionText){
                            case "Disqualified": numberOfDSQ++; break;
                            default: numberOfDNF++; break;
                        }
                    }
                }

                driverStats.setNumberOfPodiums(numberOfPodiums);
                driverStats.setNumberOfDNF(numberOfDNF);
                driverStats.setNumberOfDNS(numberOfDNS);
                driverStats.setNumberOfDSQ(numberOfDSQ);
                int averagePosition = 0;
                for(Map.Entry i :positions.entrySet()){
                    if((int)i.getValue()!=0){
                        averagePosition += (int) i.getKey()* (int) i.getValue();
                    }
                }
                driverStats.setAverageFinishPosition((double) averagePosition/numberOfRaces);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * the method retrieves the statistics for a team during a season
     * @param season specified season
     * @param teamId specified teamId
     * @param teamStats TeamStats
     */
    public static void getTeamStats(String season, String teamId, TeamStats teamStats) {
        try{
            String urlString = "https://f1api.dev/api/" + season + "/teams/" + teamId + "/drivers";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();
            if(responseCode!=200){
                throw new RuntimeException("Failed : HTTP error code : "+ responseCode);
            }
            else {
                StringBuilder output = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    output.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(output));
                JSONObject team = (JSONObject) jsonObject.get("team");
                long position = (long) team.get("position");
                teamStats.setPosition((int) position);
                long points = (long) team.get("points");
                teamStats.setPoints((int) points);
                long wins = (long) team.get("wins");
                teamStats.setNumberOfWins((int) wins);
                String name = (String) team.get("teamName");
                teamStats.setTeamName(name);
                JSONArray drivers = (JSONArray) jsonObject.get("drivers");
                List<String> driverList = new ArrayList<>();
                for(Object d : drivers){
                    JSONObject dr = (JSONObject) d;
                    JSONObject driver = (JSONObject) dr.get("driver");
                    String name2 = (String) driver.get("name");
                    String surname = (String) driver.get("surname");
                    driverList.add(name2 + " " + surname);
                }
                teamStats.setDrivers(driverList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
