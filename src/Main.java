/*
 * Author: AP COMP SCI CLASS
 * Date: 5/20/2022
 * Purpose: To create Clark Sweeper 
 */
public class Main implements Runnable {

	GUI picture = new GUI();

	public void run() {

		while(true) {

			picture.repaint();

			if (picture.set == false) {

				picture.winCheck();



			}

		}

	}

	public static void main(String[] clark) {

		new Thread(new Main()).start();

	}



}


