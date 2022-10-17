package UI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PuzzleN extends JFrame{

	public PuzzleN() {
		setTitle("PuzzleN");
		setSize(300, 200);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menu = new JMenuBar();
		
		JMenu newGame = new JMenu("Jogar");
		
		JMenuItem modoFacil = new JMenuItem(new JogarFacil());
		modoFacil.setText("Fácil");
		
		JMenuItem modoMedio = new JMenuItem(new JogarMedio());
		modoMedio.setText("Médio");
		
		JMenuItem modoDificil = new JMenuItem(new JogarDificil());
		modoDificil.setText("Difícil");
		
		JMenuItem sair = new JMenuItem(new SairAction());
		sair.setText("Sair");
		
		newGame.add(modoFacil);
		newGame.add(modoMedio);
		newGame.add(modoDificil);
		newGame.add(sair);
		
		menu.add(newGame);
		
		setJMenuBar(menu);
		setVisible(true);
	}
}
