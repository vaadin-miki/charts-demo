package org.vaadin.miki.demo.charts2;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Exporting;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Lang;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.ZoomType;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Tab with the results of sprint 3.
 * 
 * @author miki, guillermo
 *
 */
public class Sprint3Tab extends CustomComponent {

    public Sprint3Tab() {
        TabSheet sheet = new TabSheet();
        sheet.addTab(this.getSlicesTab(), "Pie chart slicing");
        sheet.addTab(this.getExtremesTab(), "Dynamic axis extremes");
        sheet.addTab(this.getLocaleTab(), "Localised menu");
        this.setCompositionRoot(sheet);
    }

    private Component getExtremesTab() {
        final Chart chart = new Chart();
        chart.setHeight("450px");
        chart.setWidth("100%");

        Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.LINE);
        configuration.getChart().setMarginRight(130);
        configuration.getChart().setMarginBottom(25);

        configuration.getTitle().setText("Monthly Average Temperature");
        configuration.getSubTitle().setText("Source: WorldClimate.com");

        configuration.getxAxis().setCategories("Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        Axis yAxis = configuration.getyAxis();
        yAxis.setMin(-10d);
        yAxis.setMax(30d);
        yAxis.setTitle(new Title("Temperature (°C)"));
        yAxis.getTitle().setVerticalAlign(VerticalAlign.HIGH);

        configuration
                .getTooltip()
                .setFormatter(
                        "'<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C'");

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setDataLabels(new Labels(true));
        configuration.setPlotOptions(plotOptions);

        Legend legend = configuration.getLegend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setHorizontalAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(-10d);
        legend.setY(100d);
        legend.setBorderWidth(0);

        ListSeries ls = new ListSeries();
        ls.setName("Tokyo");
        ls.setData(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
                13.9, 9.6);
        configuration.addSeries(ls);
        ls = new ListSeries();
        ls.setName("New York");
        ls.setData(-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1,
                8.6, 2.5);
        configuration.addSeries(ls);
        ls = new ListSeries();
        ls.setName("Berlin");
        ls.setData(-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9,
                1.0);
        configuration.addSeries(ls);
        ls = new ListSeries();
        ls.setName("London");
        ls.setData(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6,
                4.8);
        configuration.addSeries(ls);

        chart.drawChart(configuration);

        final CheckBox extremes = new CheckBox("Switch extremes");
        extremes.setImmediate(true);
        extremes.addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 20141118;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if ((Boolean) event.getProperty().getValue())
                    chart.getConfiguration().getyAxes().getAxis(0)
                            .setExtremes(10, 15);
                else
                    chart.getConfiguration().getyAxes().getAxis(0)
                            .setExtremes(-10, 30);
                chart.markAsDirty();
            }
        });

        VerticalLayout layout = new VerticalLayout(chart, extremes);
        return layout;
    }

    private Component getLocaleTab() {
        ChartOptions opts = ChartOptions.get();

        Lang langpl = new Lang();
        langpl.setContextButtonTitle("Menu podrêczne");
        langpl.setDecimalPoint(",");
        langpl.setDownloadJPEG("Pobierz jako obraz JPEG");
        langpl.setDownloadPDF("Pobierz jako dokument PDF");
        langpl.setDownloadPNG("Pobierz jako obraz PNG");
        langpl.setDownloadSVG("Pobierz jako grafikê wektorow¹ SVG");
        langpl.setDrillUpText("Wstecz (do {series.name})");
        langpl.setLoading("Wczytujê...");
        langpl.setMonths(new String[] { "styczeñ", "luty", "marzec",
                "kwiecieñ", "maj", "czerwiec", "lipiec", "sierpieñ",
                "wrzesieñ", "paŸdziernik", "listopad", "grudzieñ" });
        langpl.setNoData("Brak danych.");
        langpl.setPrintChart("Drukuj");
        langpl.setResetZoom("Domyœlne powiêkszenie");
        langpl.setResetZoomTitle("Domyœlne powiêkszenie (1:1)");
        langpl.setShortMonths(new String[] { "sty", "lut", "mar", "kwi", "maj",
                "cze", "lip", "sie", "wrz", "paŸ", "lis", "gru" });
        langpl.setThousandsSep(" ");
        langpl.setWeekdays(new String[] { "poniedzia³ek", "wtorek", "œroda",
                "czwartek", "pi¹tek", "sobota", "niedziela" });

        opts.setLang(langpl);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(new Label(getDescription()));

        Chart chart = new Chart();

        Configuration conf = chart.getConfiguration();

        conf.getChart().setZoomType(ZoomType.XY);

        conf.setTitle("Average Monthly Temperature and Rainfall in Tokyo");
        conf.setSubTitle("Source: WorldClimate.com");

        XAxis x = new XAxis();
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(x);

        YAxis primary = new YAxis();
        primary.setTitle("Temperature");
        Style style = new Style();
        style.setColor(new SolidColor("#89A54E"));
        primary.getTitle().setStyle(style);
        conf.addyAxis(primary);

        YAxis snd = new YAxis();
        snd.setTitle("Rainfall");
        snd.setOpposite(true);
        style = new Style();
        style.setColor(new SolidColor("#4572A7"));
        snd.getTitle().setStyle(style);
        conf.addyAxis(snd);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y + (this.series.name == 'Rainfall' ? ' mm' : '°C')");
        conf.setTooltip(tooltip);

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setHorizontalAlign(HorizontalAlign.LEFT);
        legend.setX(120);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setY(100);
        legend.setFloating(true);
        legend.setBackgroundColor("#FFFFFF");
        conf.setLegend(legend);

        DataSeries series = new DataSeries();
        series.setPlotOptions(new PlotOptionsColumn());
        series.setName("Rainfall");
        series.setData(49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5,
                216.4, 194.1, 95.6, 54.4);
        series.setyAxis(1);
        conf.addSeries(series);

        series = new DataSeries();
        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        series.setPlotOptions(plotOptions);
        series.setName("Temperature");
        series.setData(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
                13.9, 9.6);
        plotOptions.setColor(new SolidColor("#89A54E"));
        conf.addSeries(series);

        // Enabling exporting adds a button to UI via users can download the
        // chart e.g. for printing
        Exporting exporting = new Exporting(true);

        // One can customize the filename
        exporting.setFilename("mychartfile.pdf");

        // and choose whether to post raster images to exporting server
        exporting.setEnableImages(true);

        // Exporting is by default done on highcharts public servers, but you
        // can also use your own server
        // exporting.setUrl("http://my.own.server.com");

        // Actually use these settings in the chart
        chart.getConfiguration().setExporting(exporting);

        // Simpler boolean API can also be used to just toggle the service
        // on/off
        // chart.getConfiguration().setExporting(true);

        verticalLayout.addComponent(chart);
        return verticalLayout;

    }

    private Component getSlicesTab() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Browser market shares at a specific website, 2010");

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
        Labels dataLabels = new Labels();
        dataLabels.setEnabled(true);
        dataLabels.setColor(SolidColor.BLACK);
        dataLabels.setConnectorColor(SolidColor.BLACK);
        dataLabels
                .setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);
        final DataSeries series = getBrowserMarketShareSeries();
        conf.setSeries(series);

        chart.drawChart();
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(chart);
        Button button = new Button("Slice");
        button.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                series.setItemSliced(1, null);
            }
        });
        layout.addComponent(button);
        return layout;
    }

    private DataSeries getBrowserMarketShareSeries() {
        DataSeriesItem firefox = new DataSeriesItem("Firefox", 45.0);
        firefox.setColor(createRadialGradient(new SolidColor(255, 128, 0),
                new SolidColor(128, 64, 0)));

        DataSeriesItem ie = new DataSeriesItem("IE", 26.8);
        ie.setColor(createRadialGradient(new SolidColor(0, 255, 255),
                new SolidColor(0, 128, 128)));

        DataSeriesItem chrome = new DataSeriesItem("Chrome", 12.8);
        chrome.setColor(createRadialGradient(new SolidColor(255, 255, 0),
                new SolidColor(128, 128, 0)));
        chrome.setSliced(true);
        chrome.setSelected(true);

        DataSeriesItem safari = new DataSeriesItem("Safari", 8.5);
        safari.setColor(createRadialGradient(new SolidColor(0, 128, 255),
                new SolidColor(0, 64, 128)));

        DataSeriesItem opera = new DataSeriesItem("Opera", 6.2);
        opera.setColor(createRadialGradient(new SolidColor(255, 0, 0),
                new SolidColor(128, 0, 0)));

        DataSeriesItem others = new DataSeriesItem("Others", 0.7);
        others.setColor(createRadialGradient(new SolidColor(0, 128, 0),
                new SolidColor(0, 64, 0)));

        return new DataSeries(firefox, ie, chrome, safari, opera, others);
    }

    /**
     * Creates a radial gradient with the specified start and end colors.
     */
    private GradientColor createRadialGradient(SolidColor start, SolidColor end) {
        GradientColor color = GradientColor.createRadial(0.5, 0.3, 0.7);
        color.addColorStop(0, start);
        color.addColorStop(1, end);
        return color;
    }

}
