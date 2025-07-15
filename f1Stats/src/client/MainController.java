package client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MainController {
    @FXML
    private Pane pane;
    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private Button statsButton;

    @FXML
    private Button comparisonButton;

    @FXML
    private Button predictionButton;

    @FXML
    private TextField textName1;

    @FXML
    private TextField textName2;

    @FXML
    private TextField textSeason;

    @FXML
    private Button searchButton;

    @FXML
    private Button searchButton2;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label text1;
    @FXML
    private Label text2;

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

    @FXML
    private TableColumn<TableRow, String> value2Column;

    @FXML
    private TableView<TableRow> championship;

    @FXML
    private TableColumn<TableRow, String> position;

    @FXML
    private TableColumn<TableRow, String> name;


    public void initialize() {
        typeChoiceBox.getItems().addAll("Driver", "Team");
        typeChoiceBox.setValue("Driver");
        labelColumn.setCellValueFactory(cellData -> cellData.getValue().labelProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        value2Column.setCellValueFactory(cellData -> cellData.getValue().valueProperty2());
        enableClickedStyle(statsButton);
        enableClickedStyle(comparisonButton);
    }

    private void visible(){
        welcomeLabel.setVisible(false);
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        l4.setVisible(false);
        line1.setVisible(false);
        line2.setVisible(false);
        line3.setVisible(false);
        line4.setVisible(false);
        pane.setVisible(true);
        typeChoiceBox.setVisible(true);
        textName1.setVisible(true);
        textSeason.setVisible(true);
    }

    @FXML
    private void statsButtonClicked() {
        searchButton.setVisible(true);
        textName2.setVisible(false);
        searchButton2.setVisible(false);
        visible();
    }

    @FXML
    private void comparisonButtonClicked() {
        searchButton2.setVisible(true);
        searchButton.setVisible(false);
        textName2.setVisible(true);
        visible();
    }

    @FXML
    private void searchButtonClicked() {
        String type = typeChoiceBox.getValue();
        String season = textSeason.getText();
        String name = textName1.getText();

        if(type.equals("Driver")) {
            showDriverStats(DriverStats.getDriverStats(season,name));
        }
        else{
            showTeamStats(TeamStats.getTeamStats(season,name));
        }
    }

    @FXML
    private void searchButton2Clicked(){
        String type = typeChoiceBox.getValue();
        String season = textSeason.getText();
        String name1 = textName1.getText();
        String name2 = textName2.getText();
        if(type.equals("Driver")) {
            showDriverStatsComp(DriverStats.getDriverStats(season,name1),DriverStats.getDriverStats(season,name2));
        }
        else{
            showTeamStatsComp(TeamStats.getTeamStats(season,name1),TeamStats.getTeamStats(season,name2));
        }

    }

    public void showTeamStatsComp(TeamStats teamStats1,TeamStats teamStats2) {
        statsTable.getColumns().clear();
        statsTable.getItems().clear();
        statsTable.setVisible(true);
        TableColumn<TableRow, String> labelColumn = new TableColumn<>("Label");
        TableColumn<TableRow, String> valueColumn = new TableColumn<>("Value");
        TableColumn<TableRow, String> value2Column = new TableColumn<>("Value2");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        value2Column.setCellValueFactory(new PropertyValueFactory<>("value2"));
        statsTable.getColumns().addAll(labelColumn, valueColumn,value2Column);
        statsTable.getItems().addAll(
                new TableRow("Name", teamStats1.getTeamName(), teamStats2.getTeamName()),
                new TableRow("Drivers", teamStats1.getDrivers().toString(), teamStats2.getDrivers().toString()),
                new TableRow("Championship position", String.valueOf(teamStats1.getPosition()),String.valueOf(teamStats2.getPosition())),
                new TableRow("Points", String.valueOf(teamStats1.getPoints()),String.valueOf(teamStats2.getPoints())),
                new TableRow("Number of wins", String.valueOf(teamStats1.getNumberOfWins()),String.valueOf(teamStats2.getNumberOfWins())),
                new TableRow("Number of Podiums", String.valueOf(teamStats1.getNumberOfPodiums()),String.valueOf(teamStats2.getNumberOfPodiums())),
                new TableRow("Number of DNFs", String.valueOf(teamStats1.getNumberOfDNF()),String.valueOf(teamStats2.getNumberOfDNF())),
                new TableRow("Number of DSQs", String.valueOf(teamStats1.getNumberOfDSQ()),String.valueOf(teamStats2.getNumberOfDSQ())),
                new TableRow("Number of DNSs", String.valueOf(teamStats1.getNumberOfDNS()),String.valueOf(teamStats2.getNumberOfDNS()))
        );
    }

    public void showDriverStatsComp(DriverStats driverStats1, DriverStats driverStats2) {
        statsTable.getColumns().clear();
        statsTable.getItems().clear();
        statsTable.setVisible(true);
        TableColumn<TableRow, String> labelColumn = new TableColumn<>("Label");
        TableColumn<TableRow, String> valueColumn = new TableColumn<>("Value");
        TableColumn<TableRow, String> value2Column = new TableColumn<>("Value2");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        value2Column.setCellValueFactory(new PropertyValueFactory<>("value2"));
        statsTable.getColumns().addAll(labelColumn, valueColumn,value2Column);
        statsTable.getItems().addAll(
                new TableRow("Name", driverStats1.getName(), driverStats2.getName()),
                new TableRow("Driver Number", String.valueOf(driverStats1.getDriverNumber()), String.valueOf(driverStats2.getDriverNumber())),
                new TableRow("Team Name", driverStats1.getTeamName(), driverStats2.getTeamName()),
                new TableRow("Championship position", String.valueOf(driverStats1.getPosition()), String.valueOf(driverStats2.getPosition())),
                new TableRow("Points", String.valueOf(driverStats1.getPoints()), String.valueOf(driverStats2.getPoints())),
                new TableRow("Number of wins", String.valueOf(driverStats1.getNumberOfWins()), String.valueOf(driverStats2.getNumberOfWins())),
                new TableRow("Number of podiums", String.valueOf(driverStats1.getNumberOfPodiums()), String.valueOf(driverStats2.getNumberOfPodiums())),
                new TableRow("Average finish position", String.valueOf(driverStats1.getAverageFinishPosition()), String.valueOf(driverStats2.getAverageFinishPosition())),
                new TableRow("Average points per race", String.valueOf(driverStats1.getAveragePointsPerRace()), String.valueOf(driverStats2.getAveragePointsPerRace())),
                new TableRow("Number of DNFs", String.valueOf(driverStats1.getNumberOfDNF()), String.valueOf(driverStats2.getNumberOfDNF())),
                new TableRow("Number of DNSs", String.valueOf(driverStats1.getNumberOfDNS()), String.valueOf(driverStats2.getNumberOfDNS())),
                new TableRow("Number of DSQs", String.valueOf(driverStats1.getNumberOfDSQ()), String.valueOf(driverStats2.getNumberOfDSQ()))
        );
    }

    /**
     * show stats for a driver
     * @param driverStats stats
     */
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

    /**
     * show stats for a team
     * @param teamStats stats
     */
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
                new TableRow("Drivers", teamStats.getDrivers().get(0) + ", " + teamStats.getDrivers().get(1)),
                new TableRow("Championship position", String.valueOf(teamStats.getPosition())),
                new TableRow("Points", String.valueOf(teamStats.getPoints())),
                new TableRow("Number of wins", String.valueOf(teamStats.getNumberOfWins())),
                new TableRow("Number of Podiums", String.valueOf(teamStats.getNumberOfPodiums())),
                new TableRow("Number of DNFs", String.valueOf(teamStats.getNumberOfDNF())),
                new TableRow("Number of DSQs", String.valueOf(teamStats.getNumberOfDSQ())),
                new TableRow("Number of DNSs", String.valueOf(teamStats.getNumberOfDNS()))
        );
    }

    /**
     * action done when the button is clicked
     * @param button the button clicked
     */
    private void enableClickedStyle(Button button) {
        button.setOnMouseClicked(e -> {
            if (!button.getStyleClass().contains("clicked")) {
                button.getStyleClass().add("clicked");
            }
        });
    }
}
