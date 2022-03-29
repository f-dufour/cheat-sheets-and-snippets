package fr.dufour.duolingodesktop.gui;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GUIController {

	@FXML
	WebView browser;
	
	@FXML
	ImageView loadingImage;
	
	@FXML
	Button btnReload, btnTheme, btnClip;
	
	@FXML
	HBox bottomToolBar;

	@FXML
	public void initialize(){
		browser.setCache(true);
		CookieManager cookiemanager = new CookieManager();
		CookieHandler.setDefault(cookiemanager);
		cookiemanager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		WebEngine engine = browser.getEngine();
		String url = "https://www.duolingo.com/";
		engine.load(url);
	}
	
	@FXML
	public void reloadListener(){
		browser.getEngine().reload();
	}
}	