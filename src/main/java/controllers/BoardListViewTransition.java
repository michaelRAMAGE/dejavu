package controllers;

import java.io.IOException;

import Rello.Client;
import javafx.stage.Stage;
import template.BoardListView;

public class BoardListViewTransition
{
	public Stage stage;
	public Client client; 
	
	public BoardListViewTransition(Stage stage, Client client) {
		this.stage = stage;
		this.client = client; 
	}
	
	public void showView() throws IOException {
    	new BoardListView(stage, client).load();
	}
}
