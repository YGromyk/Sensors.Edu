package fxapp;

import fxapp.socket.ClientThread;
import fxapp.utils.ColorsHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {
    private int width = 600;
    private int height = 600;
    private int margin = 10;

    private LineChart linechart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series xDataSeries;
    private XYChart.Series yDataSeries;
    private XYChart.Series zDataSeries;

    @Override
    public void start(Stage stage) {
        ScrollPane root = new ScrollPane(initChart());
        root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        //initColors();
        Scene scene = new Scene(root, width, height);
        stage.setTitle("SensorsApp");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        new Thread(this::connectSocket).start();
    }


    private LineChart initChart() {
        xAxis = new NumberAxis(System.currentTimeMillis() - (5 * 1000), System.currentTimeMillis() + (2 * 1000), 1000);
        yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Value");
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number t) {
                return new SimpleDateFormat("HH:mm:ss").format(new Date(t.longValue()));
            }

            @Override
            public Number fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });


        //Creating the line chart
        linechart = new LineChart(xAxis, yAxis);
        linechart.setAnimated(false);
        linechart.setCreateSymbols(false);
        linechart.setPrefSize(width - margin, height - margin);


        //Prepare XYChart.Series objects by setting data
        xDataSeries = new XYChart.Series();
        yDataSeries = new XYChart.Series();
        zDataSeries = new XYChart.Series();

        xDataSeries.setName("X");
        yDataSeries.setName("Y");
        zDataSeries.setName("Z");

        //Setting the data to Line chart
        linechart.getData().add(xDataSeries);
        linechart.getData().add(yDataSeries);
        linechart.getData().add(zDataSeries);

        return linechart;
        //Creating a scene object
    }


    private void initColors() {
        Node xLine = xDataSeries.getNode().lookup(".chart-series-line");
        Node yLine = yDataSeries.getNode().lookup(".chart-series-line");
        Node zLine = zDataSeries.getNode().lookup(".chart-series-line");

        Node xText = xDataSeries.getNode().lookup("-chart-legend-item-symbol");
        Node yText = yDataSeries.getNode().lookup("-chart-legend-item-symbol");
        Node zText = zDataSeries.getNode().lookup("-chart-legend-item-symbol");


        String redRGB = ColorsHelper.getRGBFromColor(Color.RED);
        String greenRGB = ColorsHelper.getRGBFromColor(Color.GREEN);
        String blueRGB = ColorsHelper.getRGBFromColor(Color.BLUE);

        xLine.setStyle("-fx-stroke: rgba(" + redRGB + ", 1.0);");
        yLine.setStyle("-fx-stroke: rgba(" + greenRGB + ", 1.0);  -fx-text-fill: rgba(" + greenRGB + ", 1.0);");
        zLine.setStyle("-fx-stroke: rgba(" + blueRGB + ", 1.0);  -fx-text-fill: rgba(" + blueRGB + ", 1.0);");

        xText.setStyle("-fx-text-fill: rgba(" + redRGB + ", 1.0);");
        yText.setStyle("-fx-text-fill: rgba(" + greenRGB + ", 1.0);");
        zText.setStyle("-fx-text-fill: rgba(" + blueRGB + ", 1.0);");

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void connectSocket() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        final int portNumber = 81;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException exception) {
            return;
        }

        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ClientThread client = new ClientThread(socket);
            client.setOnEventListener(eventDTO -> {
                        long timeStamp = System.currentTimeMillis();
                        Platform.runLater(() -> {
                            xDataSeries.getData().add(new XYChart.Data<>(timeStamp, eventDTO.getX()));
                            yDataSeries.getData().add(new XYChart.Data<>(timeStamp, eventDTO.getY()));
                            zDataSeries.getData().add(new XYChart.Data<>(timeStamp, eventDTO.getZ()));
                        });
                        xAxis.setUpperBound(timeStamp + (2 * 1000));
                        xAxis.setLowerBound(timeStamp - (5 * 1000));
                        System.out.println(eventDTO.toString());
                    }
            );
            client.start();
        }
    }
}
