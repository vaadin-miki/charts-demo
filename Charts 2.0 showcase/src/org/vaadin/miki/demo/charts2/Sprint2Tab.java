package org.vaadin.miki.demo.charts2;

import java.util.Arrays;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.CrosshairStyle;
import com.vaadin.addon.charts.model.DashStyle;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Dial;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.PlotOptionsArea;
import com.vaadin.addon.charts.model.PlotOptionsGauge;
import com.vaadin.addon.charts.model.Stacking;
import com.vaadin.addon.charts.model.SubTitle;
import com.vaadin.addon.charts.model.TickPosition;
import com.vaadin.addon.charts.model.TickmarkPlacement;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.FontWeight;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.addon.charts.model.style.Theme;
import com.vaadin.addon.charts.themes.GrayTheme;
import com.vaadin.addon.charts.themes.GridTheme;
import com.vaadin.addon.charts.themes.HighChartsDefaultTheme;
import com.vaadin.addon.charts.themes.SkiesTheme;
import com.vaadin.addon.charts.themes.VaadinDarkTheme;
import com.vaadin.addon.charts.themes.VaadinLightTheme;
import com.vaadin.addon.charts.themes.VaadinTheme;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Tab with the results of sprint 2.
 * 
 * @author miki, guillermo
 *
 */
public class Sprint2Tab extends CustomComponent {

    public Sprint2Tab() {
        TabSheet sheet = new TabSheet();
        sheet.addTab(this.getThemesTab(), "New themes");
        sheet.addTab(this.getCrosshairTab(), "Custom crosshairs");
        sheet.addTab(this.getCustomGaugeTab(), "Customised gauge dials");
        this.setCompositionRoot(sheet);
    }

    private Component getThemesTab() {
        VerticalLayout content = new VerticalLayout();
        Chart chart;

        ChartOptions.get().setTheme(new HighChartsDefaultTheme());
        HorizontalLayout buttons = new HorizontalLayout();
        content.addComponent(buttons);

        Button vaadin = new Button("Vaadin", new ThemeChangeListener(
                VaadinTheme.class));
        vaadin.setId("vaadin-button");
        buttons.addComponent(vaadin);

        Button grid = new Button("Grid", new ThemeChangeListener(
                GridTheme.class));
        grid.setId("grid-button");
        buttons.addComponent(grid);

        Button skies = new Button("Skies", new ThemeChangeListener(
                SkiesTheme.class));
        skies.setId("skies-button");
        buttons.addComponent(skies);

        Button gray = new Button("Gray", new ThemeChangeListener(
                GrayTheme.class));
        gray.setId("gray-button");
        buttons.addComponent(gray);

        Button highchart = new Button("Highcharts", new ThemeChangeListener(
                HighChartsDefaultTheme.class));
        highchart.setId("hs-button");
        buttons.addComponent(highchart);

        Button vaadinLight = new Button("VaadinLight", new ThemeChangeListener(
                VaadinLightTheme.class));
        highchart.setId("vl-button");
        buttons.addComponent(vaadinLight);

        Button vaadinDark = new Button("VaadinDark", new ThemeChangeListener(
                VaadinDarkTheme.class));
        highchart.setId("vd-button");
        buttons.addComponent(vaadinDark);

        chart = new Chart(ChartType.AREA);

        Configuration conf = chart.getConfiguration();

        conf.setTitle(new Title(
                "Historic and Estimated Worldwide Population Distribution by Region"));
        conf.setSubTitle(new SubTitle("Source: Wikipedia.org"));

        XAxis xAxis = new XAxis();
        xAxis.setTickmarkPlacement(TickmarkPlacement.ON);
        xAxis.setCategories("1750", "1800", "1850", "1900", "1950", "1999",
                "2050");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle(new Title("Percent"));
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.series.name + ': ' +this.x +': '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+ Highcharts.numberFormat(this.y, 0, ',') +' millions)'");
        conf.setTooltip(tooltip);

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setStacking(Stacking.PERCENT);
        plotOptions.setLineColor(SolidColor.WHITE);
        plotOptions.setLineWidth(1);
        Marker marker = new Marker();
        marker.setLineColor(SolidColor.WHITE);
        marker.setLineWidth(1);
        plotOptions.setMarker(marker);
        conf.setPlotOptions(plotOptions);

        conf.addSeries(new ListSeries("Asia", 502, 635, 809, 947, 1402, 3634,
                5268));
        conf.addSeries(new ListSeries("Africa", 106, 107, 111, 133, 221, 767,
                1766));
        conf.addSeries(new ListSeries("Europe", 163, 203, 276, 408, 547, 729,
                628));
        conf.addSeries(new ListSeries("America", 18, 31, 54, 156, 339, 818,
                1201));
        conf.addSeries(new ListSeries("Ocenia", 2, 2, 2, 6, 13, 30, 46));

        chart.drawChart(conf);

        chart.setWidth("100%");
        chart.setHeight("400px");
        content.addComponent(chart);

        return content;

    }

    private Component getCrosshairTab() {

        final Chart chart = new Chart();

        Configuration config = chart.getConfiguration();

        config.getTooltip().setCrosshairs(
                new CrosshairStyle(10, SolidColor.BLACK, DashStyle.SOLID, 0),
                new CrosshairStyle(5, "#FFC0CB", DashStyle.DOT, 1));

        ListSeries ls = new ListSeries();
        ls.setName("Data");
        ls.setData(29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4,
                194.1, 95.6, 54.4);

        config.setSeries(ls);

        chart.drawChart(config);

        final ComboBox xStyle = new ComboBox("X axis style",
                Arrays.asList(DashStyle.values()));
        final ComboBox yStyle = new ComboBox("Y axis style",
                Arrays.asList(DashStyle.values()));

        final ComboBox xColor = new ComboBox("X axis colour", Arrays.asList(
                SolidColor.BLACK, SolidColor.BLUE, SolidColor.AQUA,
                SolidColor.GREEN, SolidColor.YELLOW, SolidColor.ORANGE,
                SolidColor.RED, SolidColor.DARKRED, SolidColor.WHITE));
        final ComboBox yColor = new ComboBox("Y axis colour", Arrays.asList(
                SolidColor.BLUEVIOLET, SolidColor.PURPLE, SolidColor.PINK,
                SolidColor.DARKMAGENTA, SolidColor.LIGHTSALMON,
                SolidColor.SEASHELL, SolidColor.MEDIUMSLATEBLUE,
                SolidColor.CHOCOLATE, SolidColor.STEELBLUE));

        for (ComboBox cb : new ComboBox[] { xStyle, yStyle, xColor, yColor }) {
            cb.setImmediate(true);
            cb.setNullSelectionAllowed(false);
        }
        xStyle.setValue(DashStyle.SOLID);
        yStyle.setValue(DashStyle.DOT);
        xColor.setValue(SolidColor.BLACK);
        yColor.setValue(SolidColor.PINK);

        final Slider xWidth = new Slider("X axis width", 1, 15);
        final Slider yWidth = new Slider("Y axis width", 1, 15);

        xWidth.setImmediate(true);
        yWidth.setImmediate(true);
        xWidth.setValue(10d);
        yWidth.setValue(5d);

        for (Component c : new Component[] { xStyle, yStyle, xColor, yColor,
                xWidth, yWidth }) {
            if (c instanceof Property.ValueChangeNotifier) {
                ((Property.ValueChangeNotifier) c)
                        .addValueChangeListener(new Property.ValueChangeListener() {

                            @Override
                            public void valueChange(
                                    Property.ValueChangeEvent event) {
                                chart.getConfiguration()
                                        .getTooltip()
                                        .setCrosshairs(
                                                new CrosshairStyle(xWidth
                                                        .getValue().intValue(),
                                                        (SolidColor) xColor
                                                                .getValue(),
                                                        (DashStyle) xStyle
                                                                .getValue(), 0),
                                                new CrosshairStyle(yWidth
                                                        .getValue().intValue(),
                                                        (SolidColor) yColor
                                                                .getValue(),
                                                        (DashStyle) yStyle
                                                                .getValue(), 0));
                                chart.drawChart();
                            }
                        });
            }
        }

        HorizontalLayout rowX = new HorizontalLayout(xWidth, xColor, xStyle);
        HorizontalLayout rowY = new HorizontalLayout(yWidth, yColor, yStyle);

        VerticalLayout layout = new VerticalLayout(chart, rowX, rowY);
        return layout;

    }

    private Component getCustomGaugeTab() {
        final Chart chart = new Chart();
        chart.setWidth("500px");
        chart.setHeight("200px");

        final Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.GAUGE);
        configuration.getChart().setPlotBackgroundColor(null);
        configuration.getChart().setPlotBackgroundImage(null);
        configuration.getChart().setPlotBorderWidth(0);
        configuration.getChart().setPlotShadow(false);
        configuration.setTitle("The Vaadin Charts customised clock");
        configuration.getCredits().setEnabled(false);

        GradientColor gradient1 = GradientColor.createRadial(0.5, -0.4, 1.9);
        gradient1.addColorStop(0.5, new SolidColor(255, 255, 255, 0.2));
        gradient1.addColorStop(0.5, new SolidColor(200, 200, 200, 0.2));

        Background[] background = new Background[2];
        background[0] = new Background();

        background[1] = new Background();
        background[1].setBackgroundColor(gradient1);
        background[1].setBorderWidth(1);
        background[1].setOuterRadius("107%");

        configuration.getPane().setBackground(background);

        YAxis yAxis = configuration.getyAxis();
        yAxis.getLabels().setDistance(-20);

        yAxis.setMin(0);
        yAxis.setMax(12);
        yAxis.setLineWidth(0);
        yAxis.setShowFirstLabel(false);
        yAxis.setMinorTickInterval("auto");
        yAxis.setMinorTickWidth(1);
        yAxis.setMinorTickLength(5);
        yAxis.setMinorTickPosition(TickPosition.INSIDE);
        yAxis.setMinorGridLineWidth(0);
        yAxis.setMinorTickColor(new SolidColor("#666"));
        yAxis.setTickInterval(1);
        yAxis.setTickWidth(2);
        yAxis.setTickPosition(TickPosition.INSIDE);
        yAxis.setTickLength(10);
        yAxis.setTickColor(new SolidColor("#666"));

        yAxis.setTitle(new Title("Powered by<br/>Vaadin Charts 2"));
        yAxis.getTitle().setStyle(new Style());
        yAxis.getTitle().getStyle().setColor(new SolidColor("#BBB"));
        yAxis.getTitle().getStyle().setFontWeight(FontWeight.BOLD);
        yAxis.getTitle().getStyle().setFontSize("8px");
        yAxis.getTitle().getStyle().setLineHeight("10px");
        yAxis.getTitle().setY(10);

        final DataSeries series = new DataSeries();
        final DataSeriesItem second = new DataSeriesItem();

        second.setId("second");
        second.setY(6);
        second.setDial(new Dial());
        second.getDial().setRadius("100%");
        second.getDial().setBaseWidth(1);
        second.getDial().setRearLength("20%");
        second.getDial().setBackgroundColor(SolidColor.AQUA);
        second.getDial().setBorderColor(SolidColor.RED);
        second.getDial().setBorderWidth(2);
        second.getDial().setTopWidth(15);

        series.add(second);

        PlotOptionsGauge plotOptionsGauge = new PlotOptionsGauge();
        plotOptionsGauge.setDataLabels(new Labels(false));
        configuration.setPlotOptions(plotOptionsGauge);

        configuration.setSeries(series);

        chart.drawChart(configuration);

        final Slider value = new Slider("Value", 0, 59);
        value.setImmediate(true);
        value.setValue(30d);

        final ComboBox bgColor = new ComboBox("Background colour",
                Arrays.asList(SolidColor.BLACK, SolidColor.BLUE,
                        SolidColor.AQUA, SolidColor.GREEN, SolidColor.YELLOW,
                        SolidColor.ORANGE, SolidColor.RED, SolidColor.DARKRED,
                        SolidColor.WHITE));
        bgColor.setImmediate(true);
        bgColor.setNullSelectionAllowed(false);
        bgColor.setValue(SolidColor.AQUA);

        final ComboBox borderColor = new ComboBox("Border colour",
                Arrays.asList(SolidColor.BLACK, SolidColor.BLUE,
                        SolidColor.AQUA, SolidColor.GREEN, SolidColor.YELLOW,
                        SolidColor.ORANGE, SolidColor.RED, SolidColor.DARKRED,
                        SolidColor.WHITE));
        borderColor.setImmediate(true);
        borderColor.setNullSelectionAllowed(false);
        borderColor.setValue(SolidColor.RED);

        final Slider borderWidth = new Slider("Border width", 0, 10);
        borderWidth.setValue(2d);
        borderWidth.setImmediate(true);

        final Slider topWidth = new Slider("Indicator top width", 0, 40);
        topWidth.setValue(15d);
        topWidth.setImmediate(true);

        final Slider baseWidth = new Slider("Indicator base width", 0, 40);
        baseWidth.setValue(1d);
        baseWidth.setImmediate(true);

        for (Property.ValueChangeNotifier c : new Property.ValueChangeNotifier[] {
                value, bgColor, borderColor, borderWidth, topWidth, baseWidth })
            c.addValueChangeListener(new Property.ValueChangeListener() {

                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    second.setY(value.getValue() / 60.0 * 12);
                    second.setDial(new Dial());
                    second.getDial().setRadius("100%");
                    second.getDial().setBaseWidth(
                            baseWidth.getValue().intValue());
                    second.getDial().setRearLength("20%");
                    second.getDial().setBackgroundColor(
                            (SolidColor) bgColor.getValue());
                    second.getDial().setBorderColor(
                            (SolidColor) borderColor.getValue());
                    second.getDial().setBorderWidth(
                            borderWidth.getValue().intValue());
                    second.getDial()
                            .setTopWidth(topWidth.getValue().intValue());
                    chart.drawChart();
                }
            });

        VerticalLayout layout = new VerticalLayout(chart, value, bgColor,
                borderColor, borderWidth, topWidth, baseWidth);
        return layout;

    }

    static class ThemeChangeListener implements Button.ClickListener {

        private Class<? extends Theme> themeclass;

        public ThemeChangeListener(Class<? extends Theme> themeclass) {
            this.themeclass = themeclass;
        }

        @Override
        public void buttonClick(ClickEvent event) {
            try {
                ChartOptions.get().setTheme(themeclass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
