
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JPanel;

public class Tabuleiro extends JPanel {

		// Tamanho
		private int size;
		// Numero de blocos
		private int nbTiles;
		// Dimensão da UI
		private int dimension;
		// Cor do primeiro plano
		private static final Color FOREGROUND_COLOR = new Color(39, 9, 147); 
		// Random object para plotar os números nos blocos
		private static final Random RANDOM = new Random();
		// Armazenando as peças em uma matriz 1D de inteiros
		private int[] tiles;
		// Tamanho do bloco na UI
		private int tileSize;
		// Posição do bloco branco
		private int blankPos;
		// Margem para o tabuleiro no quadro
		private int margin;
		// Tamanho do tabuleiro na UI
		private int gridSize;
		private boolean gameOver; // true para game over, false em caso contrário

		public Tabuleiro(int size, int dim, int mar) {
			this.size = size;
			dimension = dim;
			margin = mar;

			// blocos de inicialização
			nbTiles = size * size - 1; // -1 Para não contar o bolco branco
			tiles = new int[size * size];

			// Calcular o tamanho do tabuleiro e dos blocos
			gridSize = (dim - 2 * margin);
			tileSize = gridSize / size;

			setPreferredSize(new Dimension(dimension, dimension + margin));
			setForeground(FOREGROUND_COLOR);

			gameOver = true;
		}
}