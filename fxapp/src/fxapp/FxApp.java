package fxapp;

import fxapp.socket.ClientThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxApp extends Application {
    @SuppressWarnings("FieldCanBeLocal")
    private int width = 1280;
    @SuppressWarnings("FieldCanBeLocal")
    private int height = 720;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();

        long currentTime = System.currentTimeMillis();
        long tickunit = 1000L;


        ChartView first = new ChartView(
                new NumberAxis(
                        currentTime - (5 * tickunit),
                        currentTime + (2 * tickunit),
                        tickunit
                ),
                new NumberAxis()
        );
        first.setTickUnit(tickunit);

        ChartView second = new ChartView(
                new NumberAxis(
                        currentTime - (5 * tickunit),
                        currentTime + (2 * tickunit),
                        tickunit
                ),
                new NumberAxis()
        );
        second.setTickUnit(tickunit);

        root.getChildren().addAll(first, second);

        MainController mainController = new MainController();
        mainController.getEventListeners().add(getChartListener(first));
        mainController.getEventListeners().add(getChartListener(second));

        Scene scene = new Scene(root, width, height);
        stage.setTitle("SensorsApp");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        new Thread(mainController::connectSocket).start();
    }

    private ClientThread.OnEventListener getChartListener(ChartView chartView) {
        return eventDTO -> {
            long timeStamp = System.currentTimeMillis();
            Platform.runLater(() -> {
                        chartView.updateData(
                                new XYChart.Data(timeStamp, eventDTO.getX()),
                                new XYChart.Data(timeStamp, eventDTO.getY()),
                                new XYChart.Data(timeStamp, eventDTO.getZ())
                        );
                        chartView.getxAxis().setUpperBound(timeStamp + (2 * chartView.getTickUnit()));
                        chartView.getxAxis().setLowerBound(timeStamp - (5 * chartView.getTickUnit()));
                    }
            );
            System.out.println(eventDTO.toString());
        };
    }
}
