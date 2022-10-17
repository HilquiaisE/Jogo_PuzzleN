package UI;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import java.awt.BorderLayout;

import Board.Tabuleiro;

public class JogarFacil extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		frame.add(new Tabuleiro(3, 550, 30), BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
		
	}

}
