package client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Line;

public class MainController {
    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private Button statsButton;

    @FXML
    private TextField textName;

    @FXML
    private TextField textSeason;

    @FXML
    private Button searchButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label l1;

    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;
    @FXML
    private Line line1;
    @FXML
    private Line line2;
    @FXML
    private Line line3;
    @FXML
    private Line line4;

    @FXML
    private TableView<TableRow> statsTable;

    @FXML
    private TableColumn<TableRow, String> labelColumn;

    @FXML
    private TableColumn<TableRow, String> valueColumn;


    public void initialize() {
        typeChoiceBox.getItems().addAll("Driver", "Team");
        typeChoiceBox.setValue("Driver");
        labelColumn.setCellValueFactory(cellData -> cellData.getValue().labelProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    @FXML
    private void statsButtonClicked() {
        typeChoiceBox.setVisible(true);
        textName.setVisible(true);
        textSeason.setVisible(true);
        searchButton.setVisible(true);
        welcomeLabel.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        l4.setVisible(false);
        line1.setVisible(false);
        line2.setVisible(false);
        line3.setVisible(false);
        line4.setVisible(false);
    }

    @FXML
    private void searchButtonClicked() {
        String type = typeChoiceBox.getValue();
        String season = textSeason.getText();
        String name = textName.getText();

        if(type.equals("Driver")) {
            showDriverStats(DriverStats.getDriverStats(season,name));
        }
        else{
            showTeamStats(TeamStats.getTeamStats(season,name));
        }
    }

    public void showDriverStats(DriverStats driverStats) {
        statsTable.getColumns().clear();
        statsTable.getItems().clear();
        statsTable.setVisible(true);
        TableColumn<TableRow, String> labelColumn = new TableColumn<>("Label");
        TableColumn<TableRow, String> valueColumn = new TableColumn<>("Value");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        statsTable.getColumns().addAll(labelColumn, valueColumn);
        statsTable.getItems().addAll(
                new TableRow("Name", driverStats.getName()),
                new TableRow("Driver Number", String.valueOf(driverStats.getDriverNumber())),
                new TableRow("Team Name", driverStats.getTeamName()),
                new TableRow("Championship position", String.valueOf(driverStats.getPosition())),
                new TableRow("Points", String.valueOf(driverStats.getPoints())),
                new TableRow("Number of wins", String.valueOf(driverStats.getNumberOfWins())),
                new TableRow("Number of podiums", String.valueOf(driverStats.getNumberOfPodiums())),
                new TableRow("Average finish position", String.valueOf(driverStats.getAverageFinishPosition())),
                new TableRow("Average points per race", String.valueOf(driverStats.getAveragePointsPerRace())),
                new TableRow("Number of DNFs", String.valueOf(driverStats.getNumberOfDNF())),
                new TableRow("Number of DNSs", String.valueOf(driverStats.getNumberOfDNS())),
                new TableRow("Number of DSQs", String.valueOf(driverStats.getNumberOfDSQ()))
        );
    }

    public void showTeamStats(TeamStats teamStats) {
        statsTable.getColumns().clear();
        statsTable.getItems().clear();
        statsTable.setVisible(true);
        TableColumn<TableRow, String> labelColumn = new TableColumn<>("Label");
        TableColumn<TableRow, String> valueColumn = new TableColumn<>("Value");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        statsTable.getColumns().addAll(labelColumn, valueColumn);
        statsTable.getItems().addAll(
                new TableRow("Name", teamStats.getTeamName()),
                new TableRow("Championship position", String.valueOf(teamStats.getPosition())),
                new TableRow("Points", String.valueOf(teamStats.getPoints())),
                new TableRow("Number of wins", String.valueOf(teamStats.getNumberOfWins())),
                new TableRow("Drivers", teamStats.getDrivers().get(0) + ", " + teamStats.getDrivers().get(1))
        );
    }
}
