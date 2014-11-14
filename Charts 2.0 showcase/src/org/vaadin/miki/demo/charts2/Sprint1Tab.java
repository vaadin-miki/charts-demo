package org.vaadin.miki.demo.charts2;

import java.util.Random;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Frame;
import com.vaadin.addon.charts.model.FramePanel;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Options3d;
import com.vaadin.addon.charts.model.Pane;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.PlotOptionsPyramid;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.PlotOptionsSolidGauge;
import com.vaadin.addon.charts.model.RangeSeries;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.ZoomType;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Shows the results of Sprint 1 in a tab.
 * 
 * @author miki, guillermo
 *
 */
public class Sprint1Tab extends CustomComponent {

    /**
     * Constructs the tab with tabsheet and other tabs.
     */
    public Sprint1Tab() {
        TabSheet contents = new TabSheet();

        contents.addTab(this.getPyramidTab(), "Pyramid chart");
        contents.addTab(this.get3DTab(), "3D charts");
        contents.addTab(this.getSolidGaugeTab(), "Solid gauge");
        contents.addTab(this.getHeatMapTab(), "Heat map");

        this.setCompositionRoot(contents);

    }

    private Component getPyramidTab() {
        DataSeries dataSeries = new DataSeries("Unique users");
        dataSeries.add(new DataSeriesItem("Website visits", 15654));
        dataSeries.add(new DataSeriesItem("Downloads", 4064));
        dataSeries.add(new DataSeriesItem("Requested price list", 1987));
        dataSeries.add(new DataSeriesItem("Invoice sent", 976));
        dataSeries.add(new DataSeriesItem("Finalized", 846));

        Chart chart = new Chart();

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Sales pyramid");
        conf.getLegend().setEnabled(false);

        PlotOptionsPyramid options = new PlotOptionsPyramid();

        options.setWidthAsPercentage(70); // With default (90%), long labels in
                                          // this example may be cut
        // options.setWidth(400); // in pixels

        Labels dataLabels = new Labels();
        dataLabels.setFormat("<b>{point.name}</b> ({point.y:,.0f})");
        dataLabels.setSoftConnector(false);
        dataLabels.setColor(new SolidColor("black"));
        options.setDataLabels(dataLabels);

        dataSeries.setPlotOptions(options);
        conf.addSeries(dataSeries);

        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(chart);

        return layout;

    }

    private Chart get3DChartCols() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Monthly Average Rainfall");
        conf.setSubTitle("Source: WorldClimate.com");

        XAxis x = new XAxis();
        x.setCategories("Jan", "Feb", "Mar", "Apr");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Rainfall (mm)");
        conf.addyAxis(y);

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setBackgroundColor("#FFFFFF");
        legend.setHorizontalAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(100);
        legend.setY(70);
        legend.setFloating(true);
        legend.setShadow(true);
        conf.setLegend(legend);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y +' mm'");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);
        plot.setBorderWidth(0);
        plot.setGroupZPadding(10);
        conf.setPlotOptions(plot);

        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(15);
        options3d.setBeta(15);
        options3d.setDepth(50);
        options3d.setViewDistance(200);
        Frame frame = new Frame();
        frame.setBack(new FramePanel(SolidColor.GREY, 5));
        frame.setBottom(new FramePanel(SolidColor.GREY, 10));
        frame.setSide(new FramePanel(SolidColor.DARKGRAY, 5));
        options3d.setFrame(frame);
        conf.getChart().setOptions3d(options3d);

        conf.addSeries(new ListSeries("Tokyo", 49.9, 71.5, 106.4, 129.2));
        conf.addSeries(new ListSeries("New York", 83.6, 78.8, 98.5, 93.4));
        conf.addSeries(new ListSeries("London", 48.9, 38.8, 39.3, 41.4));
        conf.addSeries(new ListSeries("Berlin", 42.4, 33.2, 34.5, 39.7));

        chart.drawChart(conf);
        return chart;
    }

    private Chart get3DChartPie() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Browser market shares at a specific website, 2010");

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
        Labels dataLabels = new Labels();
        dataLabels.setEnabled(true);
        dataLabels.setColor(new SolidColor(0, 0, 0));
        dataLabels.setConnectorColor(new SolidColor(0, 0, 0));
        dataLabels
                .setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);

        plotOptions.setDepth(45);

        conf.setPlotOptions(plotOptions);

        final DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Firefox", 45.0));
        series.add(new DataSeriesItem("IE", 26.8));
        DataSeriesItem chrome = new DataSeriesItem("Chrome", 12.8);
        chrome.setSliced(true);
        chrome.setSelected(true);
        series.add(chrome);
        series.add(new DataSeriesItem("Safari", 8.5));
        series.add(new DataSeriesItem("Opera", 6.2));
        series.add(new DataSeriesItem("Others", 0.7));
        conf.setSeries(series);

        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(60);
        conf.getChart().setOptions3d(options3d);

        chart.addPointClickListener(new PointClickListener() {

            @Override
            public void onClick(PointClickEvent event) {
                Notification.show("Click: "
                        + series.get(event.getPointIndex()).getName());
            }
        });

        chart.setWidth("100%");
        chart.setHeight("450px");
        chart.drawChart(conf);

        return chart;

    }

    private Chart get3DChartScatter() {
        final Chart scatterChart = new Chart(ChartType.SCATTER);
        scatterChart.setId("chart");
        scatterChart.getConfiguration().getChart().setZoomType(ZoomType.XY);
        scatterChart.getConfiguration().disableCredits();
        scatterChart.getConfiguration().setTitle("Selections as area ranges");
        scatterChart.getConfiguration().setSubTitle(
                "Drag with mouse to draw areas");

        PlotOptionsScatter scatterOptions = new PlotOptionsScatter();
        scatterOptions.setAnimation(false);
        scatterOptions.setPointStart(1);
        DataSeries series = new DataSeries();
        series.setPlotOptions(scatterOptions);
        series.setName("Original");

        Random random = new Random(0);

        for (int i = 0; i < 20; i++) {
            DataSeriesItem dsi = new DataSeriesItem();
            dsi.setY(random.nextInt(10));
            dsi.setX(random.nextInt(10));
            dsi.setZ(random.nextInt(10));
            series.add(dsi);
        }

        scatterChart.getConfiguration().addSeries(series);

        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(20);
        options3d.setBeta(30);
        options3d.setDepth(200);
        Frame frame = new Frame();
        frame.setBottom(new FramePanel(SolidColor.GREY, 10));
        options3d.setFrame(frame);
        scatterChart.getConfiguration().getChart().setOptions3d(options3d);

        scatterChart.drawChart();
        return scatterChart;
    }

    private Component get3DTab() {
        VerticalLayout layout = new VerticalLayout(this.get3DChartCols(),
                this.get3DChartPie(), this.get3DChartScatter());
        layout.setSpacing(true);
        return layout;
    }

    private Component getSolidGaugeTab() {
        final Chart chart = new Chart();
        chart.setWidth("500px");

        final Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);

        Pane pane = configuration.getPane();
        pane.setCenterXY("50%", "85%");
        pane.setSize("140%");
        pane.setStartAngle(-90);
        pane.setEndAngle(90);

        Background bkg = new Background();
        bkg.setBackgroundColor(new SolidColor("#eeeeee"));
        bkg.setInnerRadius("60%");
        bkg.setOuterRadius("100%");
        bkg.setShape("arc");
        pane.setBackground(bkg);

        YAxis yaxis = configuration.getyAxis();
        yaxis.setLineWidth(0);
        yaxis.setTickPixelInterval(400);
        yaxis.setTickWidth(0);
        yaxis.setMin(0);
        yaxis.setMax(200);
        yaxis.setTitle("Speed");
        yaxis.getTitle().setY(-70);
        yaxis.getLabels().setY(16);
        yaxis.setMinColor("#eeeeee");
        yaxis.setMaxColor(SolidColor.BLACK);

        PlotOptionsSolidGauge plotOptions = new PlotOptionsSolidGauge();
        // plotOptions.getDataLabels().setY(5);
        // plotOptions.getDataLabels().setBorderWidth(0);
        // plotOptions.getDataLabels().setUseHTML(true);
        // plotOptions.getTooltip().setValueSuffix(" km/h");

        configuration.setPlotOptions(plotOptions);

        final ListSeries series = new ListSeries("Speed", 80);
        configuration.setSeries(series);

        chart.drawChart(configuration);

        final Slider slider = new Slider(0, 200);
        slider.setCaption("Chart value:");
        slider.setValue(80.0);
        slider.setImmediate(true);
        slider.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                series.updatePoint(0, slider.getValue().intValue());
            }
        });

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(chart);
        layout.addComponent(slider);

        return layout;

    }

    private Component getHeatMapTab() {
        Chart chart = new Chart();

        Configuration config = new Configuration();
        config.getChart().setType(ChartType.HEATMAP);
        config.getChart().setMarginTop(40);
        config.getChart().setMarginBottom(40);

        config.getTitle().setText("Sales per employee per weekday");

        config.getxAxis()
                .setCategories("Marta", "Mysia", "Misiek", "Maniek", "Miki",
                        "Guillermo", "Jonatan", "Zdzis³aw", "Antoni", "Zygmunt");
        config.getyAxis().setCategories("Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday");

        config.getColorAxis().setMin(0);
        config.getColorAxis().setMinColor(SolidColor.WHITE);
        config.getColorAxis().setMaxColor(SolidColor.CYAN);

        config.getLegend().setLayout(LayoutDirection.VERTICAL);
        config.getLegend().setHorizontalAlign(HorizontalAlign.RIGHT);
        config.getLegend().setMargin(0);
        config.getLegend().setVerticalAlign(VerticalAlign.TOP);
        config.getLegend().setY(25);
        config.getLegend().setSymbolHeight(320);

        RangeSeries rs = new RangeSeries("Sales per employee",
                this.getRawData());

        config.setSeries(rs);

        chart.drawChart(config);

        // System.err.println(config.toString());
        return new VerticalLayout(chart);
    }

    /**
     * Raw data to the heatmap chart, as in highcharts.org demo.
     * 
     * @return Array of arrays of numbers.
     */
    private Number[][] getRawData() {
        return new Number[][] { { 0, 0, 10 }, { 0, 1, 19 }, { 0, 2, 8 },
                { 0, 3, 24 }, { 0, 4, 67 }, { 1, 0, 92 }, { 1, 1, 58 },
                { 1, 2, 78 }, { 1, 3, 117 }, { 1, 4, 48 }, { 2, 0, 35 },
                { 2, 1, 15 }, { 2, 2, 123 }, { 2, 3, 64 }, { 2, 4, 52 },
                { 3, 0, 72 }, { 3, 1, 132 }, { 3, 2, 114 }, { 3, 3, 19 },
                { 3, 4, 16 }, { 4, 0, 38 }, { 4, 1, 5 }, { 4, 2, 8 },
                { 4, 3, 117 }, { 4, 4, 115 }, { 5, 0, 88 }, { 5, 1, 32 },
                { 5, 2, 12 }, { 5, 3, 6 }, { 5, 4, 120 }, { 6, 0, 13 },
                { 6, 1, 44 }, { 6, 2, 88 }, { 6, 3, 98 }, { 6, 4, 96 },
                { 7, 0, 31 }, { 7, 1, 1 }, { 7, 2, 82 }, { 7, 3, 32 },
                { 7, 4, 30 }, { 8, 0, 85 }, { 8, 1, 97 }, { 8, 2, 123 },
                { 8, 3, 64 }, { 8, 4, 84 }, { 9, 0, 47 }, { 9, 1, 114 },
                { 9, 2, 31 }, { 9, 3, 48 }, { 9, 4, 91 } };
    }

}
