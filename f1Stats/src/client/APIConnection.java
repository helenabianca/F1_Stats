package client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class APIConnection {
    public static void getDriverBasicStats(String season, String driverId, DriverStats driverStats) {
        try{
            String urlString = "http://ergast.com/api/f1/" + season + "/drivers/" + driverId +"/driverStandings.json";
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
                JSONObject obj = (JSONObject) jsonObject.get("MRData");
                JSONObject standingsTable = (JSONObject) obj.get("StandingsTable");


                JSONArray standingList = (JSONArray) standingsTable.get("StandingsLists");
                if(standingList.isEmpty()){
                    System.out.println("No data found for "+ driverId + " in " + season);
                    return;
                }
                JSONObject object = (JSONObject) standingList.get(0);
                JSONArray driverStandings = (JSONArray) object.get("DriverStandings");
                JSONObject o =  (JSONObject) driverStandings.get(0);
                String position = (String) o.get("position");
                String points = (String) o.get("points");
                String wins = (String) o.get("wins");
                JSONObject driverInfo = (JSONObject) o.get("Driver");
                String number = (String) driverInfo.get("permanentNumber");
                String firstName = (String) driverInfo.get("givenName");
                String lastName = (String) driverInfo.get("familyName");
                JSONArray constructor = (JSONArray) o.get("Constructors");
                JSONObject c = (JSONObject) constructor.get(0);
                String teamName = (String) c.get("name");

                driverStats.setName(firstName+ " " + lastName);
                driverStats.setPosition(Integer.parseInt(position));
                driverStats.setPoints(Integer.parseInt(points));
                driverStats.setNumberOfWins(Integer.parseInt(wins));
                driverStats.setDriverNumber(Integer.parseInt(number));
                driverStats.setTeamName(teamName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getOtherStats(String season, String driverId, DriverStats driverStats) {
        try{
            String urlString = "http://ergast.com/api/f1/" + season + "/drivers/" + driverId +"/results.json";
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
                JSONObject obj = (JSONObject) jsonObject.get("MRData");
                String total = (String) obj.get("total");
                int totalNumberOfRaces = Integer.parseInt(total);
                JSONObject raceTable = (JSONObject) obj.get("RaceTable");


                JSONArray races = (JSONArray) raceTable.get("Races");
                if(races.isEmpty()){
                    System.out.println("No data found for "+ driverId + " in " + season);
                    return;
                }
                int points=0;
                Map<Integer,Integer> positions = new HashMap<Integer,Integer>();
                int numberOfPodiums=0;
                int numberOfDNF=0;
                int numberOfDNS=0;
                int numberOfDSQ=0;
                for(Object o : races){
                    JSONObject race = (JSONObject) o;
                    JSONArray results = (JSONArray) race.get("Results");
                    JSONObject first = (JSONObject) results.get(0);
                    String positionText = (String) first.get("positionText");
                    if(!positionText.contains("D") && !positionText.contains("R") && !positionText.contains("W")){
                        String position = (String) first.get("position");
                        int positionValue = Integer.parseInt(position);
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
                        switch (positionText){
                            case "D": numberOfDSQ++; break;
                            case "R": numberOfDNF++; break;
                            case "W": numberOfDNS++; break;
                        }
                    }
                    String pointsText = (String) first.get("points");
                    points += Integer.parseInt(pointsText);

                }

                driverStats.setAveragePointsPerRace((double) points/totalNumberOfRaces);
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
                driverStats.setAverageFinishPosition((double) averagePosition/totalNumberOfRaces);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getTeamStats(String season, String teamId, TeamStats teamStats) {
        try{
            String urlString = "http://ergast.com/api/f1/" + season + "/constructors/" + teamId +"/constructorStandings.json";
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
                JSONObject obj = (JSONObject) jsonObject.get("MRData");
                JSONObject standingsTable = (JSONObject) obj.get("StandingsTable");

                JSONArray standingList = (JSONArray) standingsTable.get("StandingsLists");
                if(standingList.isEmpty()){
                    System.out.println("No data found for "+ teamId + " in " + season);
                    return;
                }
                JSONObject object = (JSONObject) standingList.get(0);
                JSONArray standings = (JSONArray) object.get("ConstructorStandings");
                JSONObject s = (JSONObject) standings.get(0);
                teamStats.setPoints(Integer.parseInt((String) s.get("points")));
                teamStats.setNumberOfWins(Integer.parseInt((String) s.get("wins")));
                teamStats.setPosition(Integer.parseInt((String) s.get("position")));
                JSONObject constructors = (JSONObject) s.get("Constructor");
                teamStats.setTeamName(constructors.get("name").toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
