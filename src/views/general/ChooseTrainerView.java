package views.general;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.trainer.Boy;
import model.trainer.Girl;
import model.trainer.Trainer;

public class ChooseTrainerView extends ParentView {

	public ChooseTrainerView(Trainer trainer) {
		initializePane();
	}

	private Canvas canvas1 = new Canvas(1000, 1000);
	private Canvas canvas = new Canvas(1000, 1000);
	private GraphicsContext gc1 = canvas1.getGraphicsContext2D();
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private GridPane pane;
	private Image trainers = new Image("file:media/images/ChooseGender.png");
	private Image background = new Image("file:media/images/MenuBackground.png");
	private Image girl = new Image("file:media/images/GirlChosen.png");
	private Image boy = new Image("file:media/images/BoyChosen.png");
	private Image finalBoy = new Image("file:media/images/ChooseBoy.png");
	private Image finalGirl = new Image("file:media/images/ChooseGirl.png");
	private Image oak = new Image("file:media/images/ProfessorOak.png");
	private Image flying = new Image("file:media/images/PokeFlyBalloons.png");
	private Image ethan = new Image("file:media/images/Ethan.png");
	private Image lyra = new Image("file:media/images/Lyra.png");
	private String trainerChosen = "not chosen";
	private double col;
	private double row;
	private final double x = 100;
	private final double y = 300;
	private final double w = 600;
	private final double h = 350;
	private Timeline timeline, timeline2, timeline3;
	private ClickDraw click;
	private String name;
	private Button enter, skip;
	private TextField txt;

	private String gif;
	private final String WELCOME = "Welcome.gif";
	private final String BOYQ = "BoyQ.gif";
	private final String GIRLQ = "GirlQ.gif";

	private void initializePane() {

		pane = new GridPane();

		timeline = new Timeline(new KeyFrame(Duration.millis(100), new AnimateStarter()));
		timeline2 = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStarter2()));
		timeline.setCycleCount(9);
		timeline2.setCycleCount(Timeline.INDEFINITE);
		timeline3 = new Timeline(new KeyFrame(Duration.millis(5), new AnimateStarter3()));
		timeline3.setCycleCount(300);

		gif = this.getClass().getResource(WELCOME).toExternalForm();
		this.setStyle("-fx-background-image: url('" + gif + "'); "
				+ "-fx-background-position: 250 90, 160 20, 20 160, 160 160;" + "-fx-background-repeat: no-repeat;"
				+ "-fx-background-size: 390 100");

		gc1.drawImage(background, 0, 0, 800, 700);
		gc.drawImage(trainers, x, y, w, h);

		gc.drawImage(oak, 80, 23, 130, 230);
		gc1.clearRect(250, 90, 390, 100);
		timeline2.play();

		click = new ClickDraw();

		this.addEventHandler(MouseEvent.MOUSE_CLICKED, click);
		this.getChildren().addAll(canvas1, canvas, pane);

	}

	private void addButtons() {

		Button yes = new Button("Yes!");
		Button no = new Button("No!");
		skip = new Button("Skip This");
		enter = new Button("Enter Name");

		Label label = new Label(" ");
		Label label1 = new Label(" ");

		yes.setMinWidth(200);
		yes.setMinHeight(60);
		no.setMinWidth(200);
		no.setMinHeight(60);
		pane.add(yes, 0, 0);
		pane.add(label, 0, 1);
		pane.add(label1, 0, 2);
		pane.add(no, 0, 3);
		this.removeEventHandler(MouseEvent.MOUSE_CLICKED, click);

		no.setOnAction(event -> {
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, click);
			gc.drawImage(trainers, x, y, w, h);
			pane.getChildren().remove(yes);
			pane.getChildren().remove(no);
		});

		yes.setOnAction(event -> {

			this.removeEventHandler(MouseEvent.MOUSE_CLICKED, click);
			gc.clearRect(x, y, w, h);
			pane.setPadding(new Insets(290, 1000, 550, 470));
			pane.getChildren().remove(yes);
			pane.getChildren().remove(no);
			txt = new TextField();
			txt.setMinWidth(200);
			txt.setMinHeight(30);
			skip.setMinWidth(200);
			skip.setMinHeight(30);
			enter.setMinWidth(200);
			enter.setMinHeight(30);
			txt.setStyle("-fx-text-fill: brown;");
			pane.add(txt, 0, 0);
			pane.add(enter, 0, 2);
			pane.add(skip, 0, 4);
			timeline3.play();

			String image;
			if (trainerChosen.equals("girl")) {
				image = this.getClass().getResource(GIRLQ).toExternalForm();
			} else {
				image = this.getClass().getResource(BOYQ).toExternalForm();
			}
			this.setStyle("-fx-background-image: url('" + image + "'); "
					+ "-fx-background-position: 250 90, 160 20, 20 160, 160 160;"
					+ "-fx-background-repeat: no-repeat no-repeat;" + "-fx-background-size: 390 100");

		});

		setNameHandler();

	}

	public String getTrainerChosen() {
		return trainerChosen;
	}

	public Trainer getTrainer() {
		if (trainerChosen.equals("boy")) {
			return new Boy(getNameChosen());
		} else {
			return new Girl(getNameChosen());
		}
	}

	public String getNameChosen() {
		return name;
	}

	private void setNameHandler() {

		enter.setOnAction(event -> {
			name = txt.getText();
			if (name.equals("")) {
				return;
			}
			Controller.startSetup();
		});

		skip.setOnAction(event -> {

			if (trainerChosen.equals("boy")) {
				name = "Ethan";
			}
			if (trainerChosen.equals("girl")) {
				name = "Lyra";
			}
			Controller.startSetup();

		});
	}

	private class ClickDraw implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent click) {

			// getting the x y coordinates where the mouse was clicked.
			col = click.getX();
			row = click.getY();

			if (col >= 120 && col <= 357 && row >= 315 && row <= 634) {

				trainerChosen = "boy";
				timeline.play();
				clearAndDraw(finalBoy);
				pane.setPadding(new Insets(400, 400, 300, 465));

			} else if (col >= 446 && col <= 680 && row >= 318 && row <= 634) {

				trainerChosen = "girl";
				timeline.play();
				clearAndDraw(finalGirl);
				pane.setPadding(new Insets(400, 400, 300, 133));

			} else {
				trainerChosen = "not chosen";
			}
		}

		private void clearAndDraw(Image image) {

			timeline.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					gc.clearRect(x, y, w, h);
					gc.drawImage(image, x, y, w, h);
					addButtons();
				}
			});
		}
	}

	private class AnimateStarter implements EventHandler<ActionEvent> {

		private int tic = 0;

		@Override
		public void handle(ActionEvent event) {

			tic++;

			if (tic % 2 == 0) {
				gc.drawImage(trainers, x, y, w, h);
			} else if (trainerChosen.equals("boy")) {
				gc.drawImage(boy, x, y, w, h);
			} else if (trainerChosen.equals("girl")) {
				gc.drawImage(girl, x, y, w, h);
			}
			if (tic == 10) {
				timeline.stop();
				tic = 0;
			}

		}
	}

	private class AnimateStarter2 implements EventHandler<ActionEvent> {

		private int tic2 = 0;
		double sx = 50, sy = 500, dx, dy = 500, dw = 50, dh = 50;
		double sw = dw = 600;
		double sh = dh = 600;

		@Override
		public void handle(ActionEvent event) {

			sy -= sh;
			dy -= 2;

			tic2++;

			if (tic2 % 2 == 0) {
				sx = sy = 0;
				gc.clearRect(0, 0, 85, 700);
				gc.drawImage(flying, sx, sy, sw, sh, dx, dy, dw, dh);
			}

			if (tic2 == 380) {
				tic2 = 0;
				sx = 50;
				sy = 600;
				dx = 0;
				dy = 700;
				dw = 50;
				dh = 50;
				sw = dw = 600;
				sh = dh = 600;
			}

		}
	}

	private class AnimateStarter3 implements EventHandler<ActionEvent> {

		private int tic2 = 0;
		double sx = 700, sy = 100, dx = 800, dy = 300, dw = 50, dh = 50;
		double sw = dw = 600;
		double sh = dh = 600;

		@Override
		public void handle(ActionEvent event) {

			Image trainer;
			if (trainerChosen == "boy") {
				trainer = ethan;
			} else {
				trainer = lyra;
			}

			sx -= sw;
			dx -= 2;

			tic2++;
			if (tic2 % 2 == 0) {
				sx = sy = 0;
				gc.clearRect(90, 300, 800, 800);
				gc.drawImage(trainer, sx, sy, sw, sh, dx, dy, dw, dh);
			}

			if (tic2 == 500) {
				timeline3.stop();
			}

		}
	}
}
