package org.vaadin.miki.demo.charts2;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

/**
 * Showcase for Charts 2.0 features.
 * 
 * @author miki
 *
 */
@SuppressWarnings("serial")
@Theme("showcase")
public class Charts2ShowcaseUI extends UI {

    /**
     * Servlet class.
     */
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = Charts2ShowcaseUI.class, widgetset = "org.vaadin.miki.demo.charts2.widgetset.Charts_2_0_showcaseWidgetset")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        TabSheet main = new TabSheet();
        main.addTab(new Sprint1Tab(), "New chart types");
        main.addTab(new Sprint2Tab(), "Enhancements 1");
        main.addTab(new Sprint3Tab(), "Enhancements 2");
        this.setContent(main);
    }

}