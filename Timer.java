import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class Timer extends HBox {
	private Timeline animation;
	private ImageView timer1, timer2, timer3;
	private int time = 0;
	public Timer() {
		setPadding(new Insets(5));
		timer1 = new ImageView();
		timer2 = new ImageView();
		timer3 = new ImageView();
		getChildren().addAll(timer3, timer2, timer1);
		setNum(timer1, 0);
		setNum(timer2, 0);
		setNum(timer3, 0);
	}

	public void update() {
		int ones, tens, hundreds;
		ones = time % 10;
		tens = (time / 10) % 10;
		hundreds = (time / 100) % 10;
		if (time == 999)
			animation.pause();
		setNum(timer1, ones);
		setNum(timer2, tens);
		setNum(timer3, hundreds);
	}
	public void increment() {
		this.time  += 1;
	}

	public void setNum(ImageView imag, int x) {
		imag.setPreserveRatio(true);
		imag.setFitHeight(40);
		imag.setImage(new Image("File:res/digits/" + x + ".png"));			
	}
	
	public void play(){
		this.animation.play();
	}
	public int getTime() {
		return this.time;
	}

	public void pause() {
		this.animation.pause();
	}
}

class NoMine extends HBox {
	private ImageView timer1, timer2, timer3;
	int time;
	public NoMine(int time) {
		setPadding(new Insets(5));
		timer1 = new ImageView();
		timer2 = new ImageView();
		timer3 = new ImageView();
		this.time = time;
		getChildren().addAll(timer3, timer2, timer1);
		update();
	}
	public void update() {
		int ones, tens, hundreds;
		if (time < 0)
			time = 0;
		if (time > MineSweeper2.mine)
			time = MineSweeper2.mine;
		ones = time % 10;
		tens = (time / 10) % 10;
		hundreds = (time / 100) % 10;
		setNum(timer1, ones);
		setNum(timer2, tens);
		setNum(timer3, hundreds);
	}
	public void setNum(ImageView imag, int x) {
		imag.setPreserveRatio(true);
		imag.setFitHeight(40);
		imag.setImage(new Image("File:res/digits/" + x + ".png"));			
	}
	public void increment() {
		this.time += 1;
	}
	public void decrement() {
		this.time  -= 1;
	}
}

