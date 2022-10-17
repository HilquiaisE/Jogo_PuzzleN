package UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import Board.Tabuleiro;

public class JogarMedio extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		frame.add(new Tabuleiro(4, 550, 30), BorderLayout.CENTER);
		frame.pack();	
		frame.setVisible(true);
		
	}

}
