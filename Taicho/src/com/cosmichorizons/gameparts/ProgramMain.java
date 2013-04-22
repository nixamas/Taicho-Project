package com.cosmichorizons.gameparts;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class has the main() routine. Used to instantiate Board.java object for the game.
 * Also sets bounds for Board on JPanel of ProgramMain. Draws the board
 * 
 */
public class ProgramMain extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JButton unstackBtn;

	/**
	 * Main routine makes it possible to run Taicho as a stand-alone
	 * application. Opens a window showing a Taicho panel; the program ends
	 * when the user closes the window.
	 * 
	 * Intialize JFrame for Window
	 */
	public static void main(String[] args) {
		System.out.println("MAIN");
		JFrame window = new JFrame("Taicho");
		ProgramMain content = new ProgramMain();
		window.setContentPane(content);
		window.pack();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screensize.width - window.getWidth()) / 2,
				(screensize.height - window.getHeight()) / 2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
	}

	/**
	 * The constructor creates the Board, adds all the components, and sets the bounds
	 * of the components. 
	 */
	public ProgramMain() {
		System.out.println("Taicho constructor");
		setLayout(null); // I will do the layout myself.

		this.setBackground(Color.BLACK); // Black background.
		
		unstackBtn = new JButton("Un-Stack");

		Board board = new Board(unstackBtn);	//create a new game board
										//inside the constructor there is a value to set the size of the window
		setPreferredSize(new Dimension(board.getBoardProperties().getBoardLength()+10, board.getBoardProperties().getBoardWidth()+100));
		add(board);
		
		add(unstackBtn);
		unstackBtn.setBounds(10, board.getBoardProperties().getBoardWidth()+20, 100, 50);

		board.setBounds(10, 10, board.getBoardProperties().getBoardLength(), board.getBoardProperties().getBoardWidth()); 

	} // end constructor

} // end class Checkers