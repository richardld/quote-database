import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QuoteInterface extends Application {

	QuoteDB db = new QuoteDB();
	BorderPane rootPane = new BorderPane();
	Scene scene = new Scene(rootPane, 800, 600);
	Boolean isARainbow = false;
	Boolean passwordEntered = false;

	int resultsPage = 0;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		stage.setTitle("QuoteSearcher_1.02");

		BorderPane contents = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem addItem = new MenuItem("Add Quote (Developer only)");
		fileMenu.getItems().addAll(exitItem, addItem);
		exitItem.setOnAction(e -> {
			stage.close();
		});

		Menu helpMenu = new Menu("Help");
		MenuItem aboutItem = new MenuItem("About");
		aboutItem.setOnAction(e -> {
			Alert aboutAlert = new Alert(AlertType.INFORMATION);
			aboutAlert.setTitle("About");
			aboutAlert.setHeaderText(
					"Quote Searcher\n\nVersion: 1.0\n\nThis product allows a user to search through a database of quotes");
			aboutAlert.setContentText("Made by Derek Lu, Kenny Ngo, Richard Liu (rainbow time)");
			aboutAlert.showAndWait();
		});
		helpMenu.getItems().addAll(aboutItem);

		menuBar.getMenus().addAll(fileMenu, helpMenu);
		HBox titleBox = new HBox();
		titleBox.setPadding(new Insets(20));
		Label title = new Label("Quote Searcher");
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(title);
		String rand = Font.getFontNames().get(((int) (Font.getFontNames().size() * Math.random())));
		title.setFont(Font.font(rand, 40));

		HBox gridBox = new HBox();
		gridBox.setAlignment(Pos.CENTER);
		GridPane grid = new GridPane();
		gridBox.getChildren().add(grid);
		grid.setVgap(20);
		grid.setHgap(0);

		ChoiceBox cb = new ChoiceBox();

		cb.setPrefWidth(75);
		cb.setItems(FXCollections.observableArrayList("Keyword", "Author", "Genre"));

		ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream("magni.png")));

		TextField quoteArea = new TextField();
		quoteArea.setPrefHeight(40);
		img.setFitHeight(quoteArea.getHeight());
		cb.setPrefHeight(40);
		Button submit1 = new Button("", img);
		submit1.setBackground(null);

		Label results = new Label(db.getRandom().toString());
		results.setWrapText(true);
		results.setPrefWidth(700);

		quoteArea.setFont(Font.font("Gill Sans", 18));
		quoteArea.setPrefColumnCount(20);
		quoteArea.setPromptText("Enter search terms...");

		quoteArea.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				submit1.fire();
			}
		});

		GridPane resultbox = new GridPane();
		resultbox.setVgap(20);
		resultbox.add(grid, 0, 0);
		resultbox.add(results, 0, 1);
		resultbox.setAlignment(Pos.CENTER);

		grid.add(quoteArea, 0, 0);
		GridPane.setHalignment(quoteArea, HPos.RIGHT);
		grid.add(cb, 1, 0);
		GridPane.setHalignment(cb, HPos.RIGHT);
		grid.add(submit1, 0, 0);

		GridPane.setHalignment(submit1, HPos.RIGHT);
		grid.setAlignment(Pos.CENTER);

		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(20));
		buttonBox.setAlignment(Pos.TOP_CENTER);
		buttonBox.setSpacing(20);
		Button left = new Button("<--");
		left.setFont(Font.font("Gill Sans", 15));
		// scene.setFill(Color.color(Math.random(), Math.random(), Math.random()));

		addItem.setOnAction(e -> {
			if (quoteArea.getText().equals("password")) {
				passwordEntered = true;
			} else {
				if (passwordEntered) {
					try {
						PrintWriter pw = new PrintWriter(
								new FileOutputStream(new File("asd.txt"), true /* append = true */));
						pw.write(quoteArea.getText() + "\n");
						pw.close();
					} catch (FileNotFoundException e1) {
						System.out.println("Error unable to write");
					}
				}

			}

		});

		Button right = new Button("-->");
		right.setFont(left.getFont());
		right.setOnAction(e -> {
			if (quoteArea.getText().length() > 0 && cb.getSelectionModel().getSelectedItem() != null) {
				resultsPage++;
				String text = stringFromDB(
						lookForQuote(quoteArea.getText(), cb.getSelectionModel().getSelectedItem().toString()),
						resultsPage);
				if ("No more results.".equals(text)) {
					resultsPage--;
				}

				results.setText(text);
			}

		});

		left.setOnAction(e -> {
			if (quoteArea.getText().length() > 0 && cb.getSelectionModel().getSelectedItem() != null) {
				if (resultsPage != 0) {
					resultsPage--;
				}
				String text = stringFromDB(
						lookForQuote(quoteArea.getText(), cb.getSelectionModel().getSelectedItem().toString()),
						resultsPage);

				results.setText(text);
			}
		});

		submit1.setOnAction(e -> {
			if (quoteArea.getText().equals("rainbow time") && !isARainbow) {
				isARainbow = true;
				Timer timer = new Timer();

				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						// Scene cool = new Scene(rootPane, 800, 600, Color.color(Math.random(),
						// Math.random(), Math.random()));
						// stage.setScene(cool);
						rootPane.setBackground(null);
						scene.setFill(Color.color(Math.random(), Math.random(), Math.random()));

					}
				}, 5000, 250);
				// quoteArea.setText("WARNING!!! MAY CAUSE SEIZURES!!!");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("WARNING");
				alert.setContentText(
						"MAY CAUSE SEIZURES!!!!!\nIF YOU HAVE A HISTORY OF EPILEPSY, PLEASE LOOK AWAY!!!!!\n");
				alert.showAndWait();
			} else {
				if (quoteArea.getText().length() > 0 && cb.getSelectionModel().getSelectedItem() != null) {
					resultsPage = 0;
					String text = stringFromDB(
							lookForQuote(quoteArea.getText(), cb.getSelectionModel().getSelectedItem().toString()),
							resultsPage);
					resultsPage++;

					results.setText(text);
				} else {
					results.setText("Please fill out all boxes.");
				}
				quoteArea.requestFocus();
			}

		});
		buttonBox.getChildren().addAll(left, right);
		buttonBox.setPrefHeight(200);

		contents.setTop(titleBox);
		contents.setCenter(resultbox);
		contents.setBottom(buttonBox);

		rootPane.setTop(menuBar);

		rootPane.setCenter(contents);

		stage.setScene(scene);
		stage.show();
	}

	public ArrayList<Quote> lookForQuote(String words, String selection) {

		words = words.toLowerCase();
		if ("Author".equals(selection)) {
			return (db.getAllAuthors(words));
		} else if ("Genre".equals(selection)) {
			return (db.getAllGenres(words));
		} else if ("Keyword".equals(selection)) {
			return (db.thisWillWork(words));

		} else {
			System.out.println("ERROR WEISJFSDOFJISDF");
			return new ArrayList<Quote>();

		}
	}

	public String stringFromDB(ArrayList<Quote> a, int start) {
		String s = "";
		int displayed = 5;
		if (start * displayed + displayed >= a.size()) {
			for (int i = start * 5; i < a.size(); i++) {
				s += a.get(i) + "\n\n";
			}
			if (start == 0) {
				return "No results found.";
			} else if ("".equals(s)) {
				return "No more results.";
			}
			return s;
		} else {
			if (displayed == 0) {
				return "";
			}

			for (int i = start * 5; i < (start * 5 + displayed); i++) {
				/*
				 * String cool = a.get(i).getQuote(); for(int j = 0; j < cool.length(); j += 75)
				 * { if(cool.length() > j + 75) { s += cool.substring(j, j + 75) + "\n"; } else
				 * { s += cool.substring(j); } } s += " - " + a.get(i).getAuthor() + "\n";
				 */
				s += a.get(i) + "\n\n";
			}
			return s;
		}

	}

}
