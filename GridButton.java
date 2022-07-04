import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public	class GridButton extends Button{
	//ImageView pic;
	private int x, y, status = 0, buttonID = 0, status2 = 0;
	GridButton[][] buttons = MineSweeper2.buttons;
	public GridButton(int x, int y) {
		double size = 30;
		this.x = x;
		this.y = y;
		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);
		//setFitHeight(size);
		//setFitWidth(size);
		setGraphic(new ImageView(new Image("File:res/cover.png")));
	}

	public void clickMouse(Timeline animation, NoMine noMine, ImageView smile) {
		setOnMousePressed(e -> {
			smile.setImage(new Image("File:res/face-O.png"));
		});
		setOnMouseReleased(e -> {
			smile.setImage(new Image("File:res/face-smile.png"));
		});
		setOnMouseClicked(e -> {
			if (MineSweeper2.click == 0)
				animation.play();
			//System.out.println("buttonID " + this.getButtonID());
			MouseButton button = e.getButton();
			//
			//if(button == MouseButton.PRIMARY && button == MouseButton.SECONDARY){

			//}
			//else 
			if(button == MouseButton.PRIMARY){

				if(MineSweeper2.click == 0 ) {
					//System.out.println("swap");
					setStatus2(1);
					if(x + 1 < 	MineSweeper2.gridX) buttons[x+1][y].setStatus2(1);
							
					if(x - 1 >= 0) buttons[x-1][y].setStatus2(1);
						
					if(x + 1 < 	MineSweeper2.gridX  &&  y - 1 >= 0) buttons[x+1][y-1].setStatus2(1);
					
					if(x + 1 < 	MineSweeper2.gridX && y + 1 < MineSweeper2.gridY)buttons[x+1][y+1].setStatus2(1);
							
					if(y + 1 < 	MineSweeper2.gridY ) buttons[x][y+1].setStatus2(1);
							
					if(y - 1 >= 0) buttons[x][y-1].setStatus2(1);
							
					if(y + 1 < 	MineSweeper2.gridY  && x - 1 >= 0 ) buttons[x-1][y+1].setStatus2(1);
						
					if(x - 1 >= 0 && y - 1 >= 0) buttons[x-1][y-1].setStatus2(1);
						
					MineSweeper2.randomPlace();
					setButtonID(0);
					setStatus(1);
					//System.out.println("x y-1, x y, x y+1 " + buttons[getX()][getY()-1].getButtonID() + " " + buttons[getX()][getY()].getButtonID() + " " + buttons[getX()][getY()+1].getButtonID() );
				}
				MineSweeper2.placeImage();
				if(getStatus() != 2)
					populate2(getX(), getY());

				if(this.getButtonID() == 0) {
					populate(getX(), getY(), 1);	
				}
				else {	
					if(getStatus() != 2)
						showBomb(x,y);	// if buttonID is 9		
				}			
			}
			if(button == MouseButton.SECONDARY) {				
				if(getStatus() == 0) { //set flag with status 3
					noMine.decrement();
					noMine.update();
					setStatus(2);
					//System.out.println("getStatus: " + getStatus());

				}
				else if(getStatus() == 2) {
					noMine.increment(); // deflag and set status back to 0
					noMine.update();
					setStatus(0);
					this.setGraphic(new ImageView(new Image("File:res/cover.png")));
				}
			}
			MineSweeper2.placeImage();
			MineSweeper2.click++;			
		});
	}

	public void populate(int x, int y, int z ) {
		if (x < 0 || x > MineSweeper2.gridX -1 || y < 0 || y > MineSweeper2.gridY -1 || buttons[x][y].getStatus() == 2) return; // check for bounds
		if(buttons[x][y].getButtonID() != 0 && z == 1) {
			showBomb(x,y); //if buttonID is 9
			//System.out.println("show bomb populate " + x + " " + y);
			if(buttons[x][y].getButtonID() != 9)
				buttons[x][y].setStatus(1);
			return;
		}
		if (( buttons[x][y].getStatus() == 0  && buttons[x][y].getButtonID() == 0) || z == -1) {
			buttons[x][y].setStatus(1);
			//System.out.println("show buttons populate " + x + " " + y);
			populate( x+1, y, 1);
			populate( x-1, y, 1);
			populate( x, y-1, 1);
			populate( x, y+1, 1);
			populate( x+1, y+1, 1);
			populate( x+1, y-1, 1);
			populate( x-1, y+1, 1);
			populate( x-1, y-1, 1);
			//System.out.println("click2: " + MineSweeper2.click);
		}
		else return;
	}
	public void populate2(int x, int y) {
		if (x < 0 || x > MineSweeper2.gridX -1 || y < 0 || y > MineSweeper2.gridY -1) return;

		int count = 0;
		if(x + 1 < 	MineSweeper2.gridX  && buttons[x+1][y].getStatus() == 2)
			count++;
		if(x - 1 >= 0 && buttons[x-1][y].getStatus() == 2)
			count++;
		if(x + 1 < 	MineSweeper2.gridX  &&  y - 1 >= 0 && buttons[x+1][y-1].getStatus() == 2)
			count++;
		if(x + 1 < 	MineSweeper2.gridX && y + 1 < MineSweeper2.gridY && buttons[x+1][y+1].getStatus() == 2)
			count++;
		if(y + 1 < 	MineSweeper2.gridY   && buttons[x][y+1].getStatus() == 2)
			count++;
		if(y - 1 >= 0 && buttons[x][y-1].getStatus() == 2)
			count++;
		if(y + 1 < 	MineSweeper2.gridY  && x - 1 >= 0 && buttons[x-1][y+1].getStatus() == 2)
			count++;
		if(x - 1 >= 0 && y - 1 >= 0 && buttons[x-1][y-1].getStatus() == 2)
			count++;
		//System.out.println("into populate2");
		if (this.getButtonID() == count ) {
			//System.out.println("count is true");
			setStatus(0);
			populate(x,y, -1);
		}
		else
			return;
	}
	public void showBomb(int x, int y) {
		if(buttons[x][y].getButtonID() == 9 && MineSweeper2.click != 0 ) {						
			//System.out.println("into showBomb");
			buttons[x][y].setStatus(1);
			MineSweeper2.showSmile(-1);
			MineSweeper2.statusBomb = 1;
		}	
		else 
			if(getStatus() != 2 && buttons[x][y].getButtonID() != 9) {
				buttons[x][y].setStatus(1);
				//MineSweeper2.placeImage(0);
			}
	}
	public void shift(int x, int y) {
		if(buttons[x][y].getButtonID() != 9) {
			buttons[x][y].setButtonID(9);
			//System.out.println("setMine here x y "+ x + " " + y + " " + buttons[x][y].getButtonID());
			return;
		}
		if(y+2 < MineSweeper2.gridY) 
			shift(x,y+2);
		else 
			if (x+2 < MineSweeper2.gridX) 
				shift(x+2,y);			
			else 
				if(x-2 >= 0)
					shift(x-2,y);
				else shift(x,y-2);
	}

	public void setButtonID(int buttonID) {
		this.buttonID = buttonID;
	}

	public void setStatus(int status) {
		this.status = status;
		if(status == 1) {
			int z = this.getButtonID();
			setGraphic(new ImageView(new Image("File:res/" + z + ".png")));	
		}
	}

	public void setStatus2(int status2) {
		this.status2 = status2;
	}

	public int getStatus() {
		return status;
	}
	public int getStatus2() {
		return status2;
	}

	public int getButtonID() {
		return buttonID;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}


