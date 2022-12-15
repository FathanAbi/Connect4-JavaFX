package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game{
	// game stage, scene, dane pane
	Stage gameStage;
	Scene gameScene;
	Pane gamePane;
	Shape gridShape;
	
	// ukuran tile
	private static final int TILE_SIZE = 80;
	
	// banyaknya baris dan kolom tile
	private static final int COLUMNS = 7;
	private static final int ROWS = 6;
	
	// melacak giliran pemain
	private boolean redMove = true;
	
	// untuk mentrack disc
	private Disc[][] grid = new Disc[COLUMNS][ROWS];
	
	private Pane discRoot;
	
	
	// constructor create window
	public Game() {
		gameStage = new Stage();
		gamePane = new Pane();
		gridShape = makeGrid();
		discRoot = new Pane();
		
		gamePane.getChildren().add(discRoot);
		gamePane.getChildren().add(gridShape);
		gamePane.getChildren().addAll(makeColumns());
	}
	
	
	// launch game
	public void createGame() {
		gameScene = new Scene(gamePane);
		gameStage.setScene(gameScene);
		
		gameStage.setTitle("Connect 4");
		gameStage.show();
	}
	
	
	public static int getTileSize() {
		return TILE_SIZE;
	}
	
	
	// buat grid tile 
	private Shape makeGrid() {
		Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);
		
		for(int y = 0; y < ROWS; y++) {
			for(int x = 0; x < COLUMNS; x++) {
				Circle circle = new Circle(TILE_SIZE / 2);
				circle.setCenterX(TILE_SIZE / 2);
				circle.setCenterY(TILE_SIZE / 2);
				circle.setTranslateX(x * (TILE_SIZE	+ 5) + TILE_SIZE / 4);
				circle.setTranslateY(y * (TILE_SIZE	+ 5) + TILE_SIZE / 4);
				
				shape = Shape.subtract(shape, circle);
			}
		}
		
		Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        
		shape.setFill(Color.BLUE);
		shape.setEffect(lighting);
		
		return shape;
	}
	
	// buat overlay untuk membantu pemain mengetahui kolom mana yang dipilih dan menaruh disc
	private List<Rectangle> makeColumns(){
		List<Rectangle> list = new ArrayList<>();
		
		for(int x = 0; x < COLUMNS; x++) {
			Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            rect.setFill(Color.TRANSPARENT);
            
            // efek saat hover
            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));
            
            final int column= x;
            
            // place disc saat ditekan
            rect.setOnMouseClicked(e -> placeDisc(new Disc(redMove), column));

            list.add(rect);
		}
		
		return list;
	}
	
	
	// method untuk menempatkan disc
	private void placeDisc(Disc disc, int column) {
		
		int row = ROWS - 1;
		
		// looping untuk mencari posisi row untuk menempatkan disc
        do {
            if (!getDisc(column, row).isPresent())
                break;

            row--;
        } while (row >= 0);
        
        // jika kolom penuh
        if (row < 0)
            return;
        
     
        
        
        
        
        // track disc
        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);
        
        final int curentRow = row;
        
        
        // animasi saat meletakkan disc
        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
        
     
        
        // cek apakah disc membentuk pattern dengan disc yang sudah ditempatkan sebelumnya
        animation.setOnFinished(e -> {
        	// gameover jika mebnetuk pattern
        	 
            if (gameEnded(column, curentRow)) {
                try {
					gameOver();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
         // switch pemain
            redMove = !redMove;
        
            
        });
        animation.play();
	}
	
	
	// cek apakah dapat menempatkan disc
	private Optional<Disc> getDisc(int column, int row){
		if (column < 0 || column >= COLUMNS
                || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(grid[column][row]);
	}
	
	// cek apakah disc membentuk urutan berjumlah 4
	private boolean checkRange(List<Point2D> points) {
        int chain = 0;

        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!redMove));
            if (disc.red == redMove) {
            	chain++;
            	if(chain == 4) {
            		return true;
            	}	  
            } else {
            	chain = 0;
            }
        }
        
        return false;

       
    }
	
	// check apakah membentuk urutan berjumlah empat secara vertikal, horizontal, atau diagone
	private boolean gameEnded(int column, int row) {
		// vertika;
			List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
	                .mapToObj(r -> new Point2D(column, r))
	                .collect(Collectors.toList());

			// horizontal
	        List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
	                .mapToObj(c -> new Point2D(c, row))
	                .collect(Collectors.toList());
	        
	        
	        // kemiringan positif
	        Point2D topLeft = new Point2D(column - 3, row - 3);
	        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
	                .mapToObj(i -> topLeft.add(i, i))
	                .collect(Collectors.toList());
	        
	        // kemiringan negatif
	        Point2D botLeft = new Point2D(column - 3, row + 3);
	        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
	                .mapToObj(i -> botLeft.add(i, -i))
	                .collect(Collectors.toList());

	        return checkRange(vertical) || checkRange(horizontal)
	                || checkRange(diagonal1) || checkRange(diagonal2);
	}
	
	// tampilkan pemenang
	private void gameOver() throws IOException {
		
		// close game
		gameStage.close();
		
		// launch win screem
		WinScreen.setWinner((redMove ? "Red" : "Yellow"));
		WinScreen win = new WinScreen();
		win.createWinScreen();
		
		
	}
}
