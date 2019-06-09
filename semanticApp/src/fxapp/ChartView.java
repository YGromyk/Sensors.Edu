package fxapp;

import fxapp.utils.ChartStringConverter;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

@SuppressWarnings("WeakerAccess")
public class ChartView extends LineChart<NumberAxis, NumberAxis> {
    public NumberAxis getxAxis() {
        return xAxis;
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }

    private NumberAxis xAxis;
    @SuppressWarnings("FieldCanBeLocal")
    private NumberAxis yAxis;

    @SuppressWarnings("FieldCanBeLocal")
    private XYChart.Series<NumberAxis, NumberAxis> xDataSeries;
    @SuppressWarnings("FieldCanBeLocal")
    private XYChart.Series<NumberAxis, NumberAxis> yDataSeries;
    @SuppressWarnings("FieldCanBeLocal")
    private XYChart.Series<NumberAxis, NumberAxis> zDataSeries;

    private long tickUnit = 1000L;

    @SuppressWarnings("WeakerAccess")
    public ChartView(NumberAxis axis, NumberAxis axis2) {
        super((Axis) axis, (Axis) axis2);
        xAxis = axis;
        initChart();
    }

    private void initChart() {
        long currentTime = System.currentTimeMillis();
        yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Value");
        xAxis.setTickLabelFormatter(new ChartStringConverter());


        //Creating the line chart
        this.setAnimated(false);
        this.setCreateSymbols(false);


        //Prepare XYChart.Series objects by setting data
        xDataSeries = new XYChart.Series<>();
        yDataSeries = new XYChart.Series<>();
        zDataSeries = new XYChart.Series<>();

        xDataSeries.setName("X");
        yDataSeries.setName("Y");
        zDataSeries.setName("Z");

        //Setting the data to Line chart
        this.getData().add(xDataSeries);
        this.getData().add(yDataSeries);
        this.getData().add(zDataSeries);
    }

    public void updateData(
            XYChart.Data<NumberAxis, NumberAxis> x,
            XYChart.Data<NumberAxis, NumberAxis> y,
            XYChart.Data<NumberAxis, NumberAxis> z
    ) {
        xDataSeries.getData().add(x);
        yDataSeries.getData().add(y);
        zDataSeries.getData().add(z);
    }

    public long getTickUnit() {
        return tickUnit;
    }

    @SuppressWarnings("unused")
    public void setTickUnit(long tickUnit) {
        this.tickUnit = tickUnit;
    }

}
