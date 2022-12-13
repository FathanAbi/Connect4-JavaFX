package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// class disc
public class Disc extends Circle{
	// variabel untuk jenis disc, (RED jika true, YELLOW jika false)
	public final boolean red;
	
	
	// konstruktor
	public Disc(boolean red){
		super(Game.getTileSize()/ 2, red ? Color.RED : Color.YELLOW);
		this.red = red;
		
		setCenterX(Game.getTileSize()/ 2);
		setCenterY(Game.getTileSize()/ 2);
	}

}
