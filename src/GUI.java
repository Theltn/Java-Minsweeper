import java.util.*;

import java.awt.*;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

import javax.swing.*;



public class GUI extends JFrame  {

	public int xcoor = -100;
	public int ycoor= -100;



	public int buttonX = 605;
	public int buttonY = 5;

	public boolean set = false;

	Date beginning = new Date();

	Date end;



	int between = 1;
	int sideMines = 0;

	String congrats = "None";

	public int middleBX = buttonX + 35;
	public int middleBY = buttonY + 35;

	public int x = 800;
	public int y = -50;

	public int timeX = 1130;
	public int timeY = 5;

	public boolean factor = true;
	public boolean win = false;
	public boolean loss = false;

	Random rand = new Random();



	int [][] bombs = new int [16][9];

	int [][] nextTo = new int [16] [9];

	boolean[][] revealedBoxes = new boolean[16][9];





	public GUI() {

		this.setTitle("Clarksweeper");

		this.setSize(1286,829);;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

		this.setResizable(false);



		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				if (rand.nextInt(100) < 20) {

					//percent of boxes that contains bombs

					bombs[i][j] = 1;

				} else{

					bombs[i][j] = 0;

				}

				revealedBoxes[i][j] = false;

			}

		}

		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				sideMines = 0;

				for (int m = 0; m <16; m++) {

					for (int n = 0; n<9; n++) {

						if(!(m==i && n ==j)) {

							if(squareIn(i,j,m,n)== true)

								sideMines++;

						}



					}

				}

				nextTo[i][j] = sideMines;

			}

		}







		Board clark = new Board();

		this.setContentPane(clark);



		Move ethan = new Move();

		this.addMouseMotionListener(ethan);



		Click ham = new Click();

		this.addMouseListener(ham);

	}



	public class Board extends JPanel {

		public void paintComponent(Graphics g) {

			g.setColor(Color.BLUE);

			g.fillRect(0,0,1280,800);



			for (int i = 0; i <16; i++) {

				for (int j = 0; j<9; j++) {

					g.setColor(Color.white);

					//sets the revealed squares to their respective color

					if (revealedBoxes[i][j] == true) {

						g.setColor(Color.gray);

						if(bombs[i][j] == 1) {

							g.setColor(Color.red);

						}

					}

					// mouse hovering color
					if (xcoor >= between+i*80 && xcoor <i*80+ 80-2*between && ycoor >= between+j*80+80+26 && ycoor < between+j*80+26+80+80-2*between) {

						g.setColor(Color.YELLOW);

					}
					//coloring the numbered boxes
					g.fillRect(between+i*80, between+j*80+80, 80-2*between,80-2*between);

					if (revealedBoxes[i][j] == true) {

						g.setColor(Color.black);

						if (bombs[i][j] == 0 && nextTo[i][j] != 0 ) {                

							if (nextTo[i][j] == 1) {

								g.setColor(Color.blue);

							}else if (nextTo[i][j] == 2){

								g.setColor(Color.green);

							}else if (nextTo[i][j] == 3){

								g.setColor(Color.red);

							}else if (nextTo[i][j] == 4){

								g.setColor(new Color(0,0,128));

							}else if (nextTo[i][j] == 5){

								g.setColor(new Color(178,34,34));

							}else if (nextTo[i][j] == 6){

								g.setColor(new Color(72,209,204));

							}else if (nextTo[i][j] == 8){

								g.setColor(Color.pink);

							}  



							g.setFont(new Font("Tahoma", Font.ITALIC, 40));

							g.drawString(Integer.toString(nextTo[i][j]), i*80+27, j*80+80+55);

						}else if (bombs[i][j]==1){

							g.fillRect(i*80+10+20, j*80+80+20, 20, 40);

							g.fillRect(i*80+20, j*80+80+10+20, 40, 20);

							g.fillRect(i*80+5+20, j*80+80+5+20, 30, 30);


						}

					}

				}

			}

			//CCH GUI

			g.setColor(Color.white);

			g.fillRect(buttonX, buttonY, 70,70);

			g.setColor(Color.blue);

			g.fillRect(buttonX +10, buttonY+10, 70, 50);

			g.setColor(Color.white);
			g.fillRect(buttonX+35, buttonY+20, 10,10);
			g.fillRect(buttonX+25, buttonY+30, 30,10);
			g.fillRect(buttonX+35, buttonY+40, 10,10);

			g.setColor(Color.blue);

			//win or lose



			if(win == true) {

				g.setColor(Color.green);

				congrats = "YOU WON!";

			}else if (loss == true) {

				g.setColor(Color.red);

				congrats = "UR BAD!";

			}

			// the dropping message

			if (win == true || loss == true) {

				y = -50 + (int) (new Date().getTime() - end.getTime()) / 10;

				g.drawString(congrats, x, y);

			}

		}

	}



	public class Move implements MouseMotionListener {

		public void mouseDragged(MouseEvent arg0) {

		}

		public void mouseMoved(MouseEvent e) {

			xcoor = e.getX();

			ycoor = e.getY();

		}

	}

	public class Click implements MouseListener {

		public void mouseClicked(MouseEvent e) {



			xcoor = e.getX();

			ycoor = e.getY();

			if(foundX() != -1 && foundY() != -1) {

				revealedBoxes[foundX()][foundY()] = true;

			}

			if(inButton() == true) {

				reset();

			}

		}

		public void mousePressed(MouseEvent arg0) {
		}
		public void mouseReleased(MouseEvent arg0) {
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseExited(MouseEvent arg0) {
		}



	}



	public void winCheck() {

		if (loss == false) {

			for (int i = 0; i <16; i++) {

				for (int j = 0; j<9; j++) {

					if (revealedBoxes[i][j] == true && bombs[i][j] == 1) {

						loss = true;

						factor = false;

						end = new Date();

					}

				}

			}

		}


		// checking to see if all boxes besides bombs have been revealed
		if(allboxes() >= 144 - allbombs() && win == false) {

			win = true;

			end = new Date();

		}



	}

	//keeps track of total bombs 

	public int allbombs() {

		int total = 0;

		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				if (bombs[i][j] == 1) {

					total++;

				}

			}

		}

		return total;

	}

	//keeps track of all boxes currently revealed

	public int allboxes() {

		int total = 0;

		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				if (revealedBoxes[i][j] == true) {

					total++;

				}

			}

		}

		return total;

	}

	//returns x value of where mouse is

	public int foundX() {

		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				if (xcoor >= between+i*80 && xcoor <i*80+ 80-2*between && ycoor >= between+j*80+80+26 && ycoor < between+j*80+26+80+80-2*between) {

					return i;

				}

			}

		}

		return -1;

	}

	//resets the game back

	public void reset() {

		String congrats = "None!";

		set = true;

		beginning = new Date();



		y = -50;



		factor = true;
		win = false;
		loss = false;

		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				if (rand.nextInt(100) < 20) {

					//percent of boxes that contains bombs

					bombs[i][j] = 1;

				} else{

					bombs[i][j] = 0;
				}
				revealedBoxes[i][j] = false;
			}
		}
		
		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				sideMines = 0;

				for (int m = 0; m <16; m++) {

					for (int n = 0; n<9; n++) {

						if(!(m==i && n ==j)) {

							if(squareIn(i,j,m,n)== true)

								sideMines++;
							
						}
						
					}
					
				}
				
				nextTo[i][j] = sideMines;
				
			}
			
		}
		
		set = false;
	}

	//method to outline the clickbox the of CCH logo

	public  boolean inButton() {

		int dif = (int) Math.sqrt(Math.abs(xcoor-middleBX)*Math.abs(xcoor-middleBX)+Math.abs(ycoor-middleBY)*Math.abs(ycoor-middleBY));

		if (dif < 35) {

			return true;

		}

		return false;

	}

	//returns y value of the box that the mouse is inside of
	
	public int foundY() {

		for (int i = 0; i <16; i++) {

			for (int j = 0; j<9; j++) {

				if (xcoor >= between+i*80 && xcoor <i*80+ 80-2*between && ycoor >= between+j*80+80+26 && ycoor < between+j*80+26+80+80-2*between) {

					return j;

				}

			}

		}

		return -1;

	}

	//compares already clicked box to currently clicked box to see if they are next to each other
	
	public boolean squareIn(int xcoor, int ycoor, int cX, int cY) {

		if (xcoor-cX<2&&xcoor-cX>-2&&ycoor-cY<2&&ycoor-cY>-2&&bombs[cX][cY]==1 ) {

			return true;
		}
		return false;
	}

}
