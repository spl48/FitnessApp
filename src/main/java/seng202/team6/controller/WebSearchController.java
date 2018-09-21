package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebSearchController {
    @FXML
    WebView webView;

    public void initialize() {
        WebEngine engine = webView.getEngine();
        int type = HealthController.getType();
        if (type == 1) {
            engine.load("https://www.google.co.nz/search?q=tachycardia");
        } else if (type == 2) {
            engine.load("https://www.google.co.nz/search?q=cardiovascular+disease");
        } else if (type == 3){
            engine.load("https://www.google.co.nz/search?q=cardiovascular+disease");
        } else {
            engine.load("https://www.google.co.nz");
        }

    }
}
