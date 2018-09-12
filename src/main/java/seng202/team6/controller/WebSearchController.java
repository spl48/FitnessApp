package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebSearchController {
    @FXML
    WebView webView;

    public void initialize() {
        WebEngine engine = webView.getEngine();
        engine.load("https://www.google.co.nz");
    }
}
