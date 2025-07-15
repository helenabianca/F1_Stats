package client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRow {
    private StringProperty label;
    private StringProperty value;
    private StringProperty value2;

    public TableRow(String label, String value, String value2) {
        this.label = new SimpleStringProperty(label);
        this.value = new SimpleStringProperty(value);
        this.value2 = new SimpleStringProperty(value2);
    }

    public TableRow(String label, String value) {
        this.label = new SimpleStringProperty(label);
        this.value = new SimpleStringProperty(value);
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getValue() {
        return value.get();
    }
    public String getValue2() {
        return value2.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public StringProperty valueProperty() {
        return value;
    }

    public StringProperty labelProperty() {
        return label;
    }
    public StringProperty valueProperty2() {
        return value2;
    }

}
