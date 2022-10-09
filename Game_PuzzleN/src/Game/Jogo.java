package Game;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import Board.Tabuleiro;

public class Jogo {

	public static void main(String[] args) {
		
			JFrame frame = new JFrame();
			frame.add(new Tabuleiro(4, 550, 30), BorderLayout.CENTER);
			frame.pack();		
			frame.setVisible(true);
	}

}
