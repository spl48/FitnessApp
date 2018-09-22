package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * <h1>Web search Controller</h1>
 * <p>Sets up the web search page, and displays a web search will a url
 * which depends on the type of button pushed to get there.</p>
 */
public class WebSearchController {

    /**
     * The web view that will be displayed
     */
    @FXML
    WebView webView;

    /**
     * Initialises the web search screen by loading a url to the webview, the url
     * is specific to the "button Type" that was previosuly clicked
     */
    public void initialize() {
        WebEngine engine = webView.getEngine();
        int buttonType = HealthController.getType();
        engine.load("https://www.google.co.nz/search?q=tachycardia&oq=tachycardia&aqs=chrome");
        if (buttonType == 1) {
            engine.load("https://www.google.co.nz/search?q=tachycardia");
        } else if (buttonType == 2) {
            engine.load("https://www.google.co.nz/search?q=cardiovascular+disease");
        } else if (buttonType == 3){
            engine.load("https://www.google.co.nz/search?q=cardiovascular+disease");
        } else {
            engine.load("https://www.google.co.nz");
        }


    }
}
