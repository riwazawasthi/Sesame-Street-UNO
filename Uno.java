/*
 Riwaz Awasthi
 110746533
 CSE114- Final Project
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Uno extends Application {
	int khum = 0, kcom = 0, i = 0, ihum = 0, icom = 0;
	boolean human = false, computer = false, found = false, UNO = false;
	boolean hforfeit = false, cforfeit = false;
	public ArrayList<String> DiscardPile = new ArrayList<String>();
	public ArrayList<String> DrawPile = new ArrayList<String>();
	ImageView[] images = new ImageView[20];
	public String[] imagepath = new String[20];
	public ImageView[] computerCards = new ImageView[20];
	public String[] computerImages = new String[20];
	public Label[] labels = new Label[20];
	boolean started = false;
	TextField turn = new TextField();
	TextField color = new TextField();
	ImageView a;
	boolean drawn = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		pane.add(new Label("YOUR CARDS:"), 0, 3);
		pane.add(new Label("DISCARD PILE:"), 0, 10);
		pane.add(new Label("COMPUTER CARDS:"), 0, 20);
		pane.add(new Label("Enter Wild-Card Color:"), 4, 2);
		pane.add(turn, 1, 2);
		pane.add(color, 5, 2);
		Button reset = new Button("Reset");
		Button draw = new Button("Draw");
		Button deal = new Button("Deal");
		Button forfeit = new Button("Forfeit");
		Button discard = new Button("Discard");
		Button Computer = new Button("Computer");
		Button uno = new Button("Uno!");
		pane.add(uno, 5, 0);
		pane.add(discard, 0, 2);
		for (i = 0; i < 20; i++) {
			imagepath[i] = "";
		}

		discard.setOnAction(e -> {
			int j = Integer.parseInt(turn.getText());
			if ((human && drawn) || cforfeit) {
				DiscardPile.add(imagepath[j]);
				pane.getChildren().remove(images[j]);
				pane.getChildren().remove(labels[j]);
				ihum--;
				drawn = false;
				discard(pane);
				human = false;
				computer = true;
				turn.setText("COMPUTER'S TURN");
			} else if (!(DiscardPile.get(DiscardPile.size() - 1).contains("push"))) {
				if (human && condition(imagepath, j)) {
					DiscardPile.add(imagepath[j]);
					pane.getChildren().remove(images[j]);
					pane.getChildren().remove(labels[j]);
					ihum--;
					discard(pane);
					human = false;
					computer = true;
					turn.setText("COMPUTER'S TURN");
				} else if (human && DiscardPile.get(DiscardPile.size() - 1).equals("wild.jpg")
						&& (imagepath[j].substring(2).equalsIgnoreCase(color.getText() + ".jpg")
								|| (imagepath[j].substring(7).equalsIgnoreCase(color.getText() + ".jpg")))) {
					DiscardPile.add(imagepath[j]);
					pane.getChildren().remove(images[j]);
					pane.getChildren().remove(labels[j]);
					ihum--;
					discard(pane);
					human = false;
					computer = true;
					turn.setText("COMPUTER'S TURN");
				}
			} else
				turn.setText("DRAW TWICE AND FORFEIT");

		});

		reset.setOnAction(e -> {
			DrawPile = new UnoCards().deck;
			if (DrawPile.get(0).charAt(0) >= DrawPile.get(1).charAt(0)) {
				human = true;
				turn.setText("YOUR TURN");

			} else {
				computer = true;
				turn.setText("COMPUTER'S TURN");

			}
			for (int i = 0; i < images.length; i++) {
				pane.getChildren().remove(images[i]);
				pane.getChildren().remove(computerCards[i]);
			}
			pane.getChildren().remove(a);
			started = false;

		});
		draw.setOnAction(e -> {
			if (human) {
				khum++;
				imagepath[khum] = new String(DrawPile.get(0));
				images[khum] = new ImageView(new Image(imagepath[khum]));
				images[khum].setFitHeight(150);
				images[khum].setFitWidth(150);
				labels[khum] = new Label(khum + "");
				pane.add(labels[khum], khum, 5);
				pane.add(images[khum], khum, 8);
				ihum++;
				DrawPile.remove(0);
				if (DiscardPile.get(DiscardPile.size() - 1).indexOf("plus") == 1)
					drawn = true;

			}

		});
		deal.setOnAction(e -> {
			if (!started) {
				started = true;
				for (int i = 0; i < 5; i++) {
					khum = i;
					imagepath[i] = new String(DrawPile.get(0));
					images[i] = new ImageView(new Image(imagepath[i]));
					images[i].setFitHeight(150);
					images[i].setFitWidth(150);
					labels[i] = new Label(i + "");
					pane.add(labels[i], i, 5);
					pane.add(images[i], i, 8);
					DrawPile.remove(0);
				}
				for (int i = 0; i < 5; i++) {
					kcom = i;
					computerImages[i] = new String(DrawPile.get(0));
					computerCards[i] = new ImageView(new Image(computerImages[i]));
					computerCards[i].setFitHeight(150);
					computerCards[i].setFitWidth(150);
					pane.add(computerCards[i], i, 21);
					DrawPile.remove(0);
				}
				ihum = 5;
				icom = 5;
				
				for(int i=0;i<DrawPile.size();i++){
					String a=DrawPile.get(0);
					DiscardPile.add(DrawPile.get(0));
					discard(pane);
					DrawPile.remove(0);
					if(!(a.equals("wild.jpg")||a.contains("plus")))
						break;
				}
				
				
				
				if (computer)
					turn.setText("COMPUTER'S TURN");
			}

		});
		forfeit.setOnAction(e -> {
			computer = true;
			human = false;
			hforfeit = true;
			turn.setText("COMPUTER'S TURN");
		});
		Computer.setOnAction(e -> {
			found=false;
			if ((computer && drawn) || hforfeit) {
				for (int i = 0; i < computerImages.length; i++) {
					if (computerImages[i] != null
							&& !(computerImages[i].equals(DiscardPile.get(DiscardPile.size() - 1)))) {
						DiscardPile.add(computerImages[i]);
						color.setText("red");
						pane.getChildren().remove(computerCards[i]);
						computerImages[i]=null;
						icom--;
						drawn = false;
						hforfeit = false;
						discard(pane);
						found = true;
						break;
					}
				}
			} else if (!(DiscardPile.get(DiscardPile.size() - 1).contains("plus"))) {
				for (int i = 0; i < computerImages.length; i++) {
					if (computerImages[i] == null)
						continue;
					if (condition(computerImages, i)) {
						DiscardPile.add(computerImages[i]);
						color.setText("red");
						pane.getChildren().remove(computerCards[i]);
						computerImages[i]=null;
						icom--;
						discard(pane);
						found = true;
						break;
					} else if (DiscardPile.get(DiscardPile.size() - 1).equals("wild.jpg")
							&& (computerImages[i].substring(2).equalsIgnoreCase(color.getText() + ".jpg")
									|| computerImages[i].substring(7).equalsIgnoreCase(color.getText() + ".jpg"))) {
						DiscardPile.add(computerImages[i]);
						pane.getChildren().remove(computerCards[i]);
						computerImages[i]=null;
						icom--;
						discard(pane);
						found = true;
						break;
					}
				}
			} else {
				if (DiscardPile.get(DiscardPile.size() - 1).charAt(0) == '1') {
					kcom++;
					computerImages[kcom] = new String(DrawPile.get(0));
					computerCards[kcom] = new ImageView(new Image(computerImages[kcom]));
					computerCards[kcom].setFitHeight(150);
					computerCards[kcom].setFitWidth(150);
					pane.add(computerCards[kcom], kcom, 21);
					drawn = true;
					cforfeit=true;
					icom++;
					DrawPile.remove(0);
				} else {
					for (int i = 0; i < 2; i++) {
						kcom++;
						computerImages[kcom] = new String(DrawPile.get(0));
						computerCards[kcom] = new ImageView(new Image(computerImages[kcom]));
						computerCards[kcom].setFitHeight(150);
						computerCards[kcom].setFitWidth(150);
						pane.add(computerCards[kcom], kcom, 21);
						drawn = true;
						cforfeit=true;
						icom++;
						DrawPile.remove(0);
					}
				}
				found = true;

			}

			if (!found) {
				kcom++;
				computerImages[kcom] = new String(DrawPile.get(0));
				computerCards[kcom] = new ImageView(new Image(computerImages[kcom]));
				computerCards[kcom].setFitHeight(150);
				computerCards[kcom].setFitWidth(150);
				pane.add(computerCards[kcom], kcom, 21);
				icom++;
				DrawPile.remove(0);
				if (condition(computerImages, kcom)) {
					DiscardPile.add(computerImages[kcom]);
					pane.getChildren().remove(computerCards[kcom]);
					computerImages[kcom]=null;
					icom--;
					discard(pane);
				} else {
					cforfeit = true;
					turn.setText("Computer Forfeits");
				}

			}

			computer = false;
			human = true;
			if (!UNO && ihum == 2) {
				turn.setText("You didn't press UNO, draw two cards");
			} else
				turn.setText("YOUR TURN");
			if (icom == 0)
				turn.setText("Computer Wins");

		});
		uno.setOnAction(e -> {
			UNO = true;
		});
		pane.add(reset, 0, 0);
		GridPane.setHalignment(reset, HPos.LEFT);
		pane.add(deal, 2, 0);
		GridPane.setHalignment(deal, HPos.LEFT);
		pane.add(draw, 1, 0);
		GridPane.setHalignment(draw, HPos.RIGHT);
		pane.add(forfeit, 3, 0);
		GridPane.setHalignment(forfeit, HPos.RIGHT);
		pane.add(Computer, 4, 0);
		GridPane.setHalignment(Computer, HPos.CENTER);
		GridPane.setHalignment(uno, HPos.CENTER);
		Scene scene = new Scene(pane);
		primaryStage.setTitle("UNO Sesame Street Version-Human V Computer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public boolean condition(String[] computerImages, int i) {
		if ((!computerImages[i].contains("plus")&&computerImages[i].charAt(0) == DiscardPile.get(DiscardPile.size() - 1).charAt(0)) || (computerImages[i]
				.substring(2).equalsIgnoreCase(DiscardPile.get(DiscardPile.size() - 1).substring(2))))
			return true;
		else if (computerImages[i].equalsIgnoreCase("wild.jpg"))
			return true;
		else if (computerImages[i].substring(7).equalsIgnoreCase(DiscardPile.get(DiscardPile.size() - 1).substring(2)))
			return true;
		else
			return false;
	}

	public void discard(GridPane pane) {
		a = new ImageView(new Image(DiscardPile.get(DiscardPile.size() - 1)));
		a.setFitHeight(250);
		a.setFitWidth(250);
		pane.add(a, 2, 11);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
