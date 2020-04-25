package me.map.lab3.elementeGui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AlertBoxPieChart {
    public static void display(String titluFereastra, ObservableList<PieChart.Data> pieChartData, String mesaj, String bonus)
    {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titluFereastra);
        window.setMinWidth(250);

        Label label = new Label(mesaj);

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        final PieChart chart = new PieChart(pieChartData);
        Tooltip container = new Tooltip();
        container.setGraphic(caption);
        chart.setTitle(titluFereastra);
        Button closeButton = new Button ("Inchide");
        closeButton.setOnAction(e-> window.close());
        chart.getData().forEach((data) ->
        {
            data.getNode().
                    addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
                    {
                        if (container.isShowing())
                        {
                            container.hide();
                        }
                        caption.setText(String.valueOf(data.getPieValue())+bonus);
                        container.show(window, e.getScreenX(), e.getScreenY());
                    });
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,chart,closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
