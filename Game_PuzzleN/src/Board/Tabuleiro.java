package Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;

public class Tabuleiro extends JPanel implements NewGame{

		// Tamanho
		private int size;
		// Numero de blocos
		private int nbTiles;
		// Dimens�o da UI
		private int dimension;
		// Cor do primeiro plano
		private static final Color FOREGROUND_COLOR = new Color(39, 9, 147); 
		// Random object para plotar os n�meros nos blocos
		private static final Random RANDOM = new Random();
		// Armazenando as pe�as em uma matriz 1D de inteiros
		private int[] tiles;
		// Tamanho do bloco na UI
		private int tileSize;
		// Posi��o do bloco branco
		private int blankPos;
		// Margem para o tabuleiro no quadro
		private int margin;
		// Tamanho do tabuleiro na UI
		private int gridSize;
		private boolean gameOver; // true para game over, false em caso contr�rio

		public Tabuleiro(int size, int dim, int mar) {
			this.size = size;
			dimension = dim;
			margin = mar;

			// blocos de inicializa��o
			nbTiles = size * size - 1; // -1 Para n�o contar o bolco branco
			tiles = new int[size * size];

			// Calcular o tamanho do tabuleiro e dos blocos
			gridSize = (dim - 2 * margin);
			tileSize = gridSize / size;

			setPreferredSize(new Dimension(dimension, dimension + margin));
			setForeground(FOREGROUND_COLOR);
			setFont(new Font("tahoma", Font.BOLD, 60));

			gameOver = true;

			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					// usado para permitir que os usu�rios interajam na grade clicando
					// Implementando a intera��o com os usu�rios para mover pe�as para resolver o jogo
					if (gameOver) {
						newGame();
					} else {
						// obter a posi��o do clique
						int ex = e.getX() - margin;
						int ey = e.getY() - margin;

						// Saber se o clique foi no tabuleiro
						if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize)
							return;

						// Obter a posi��o no tabuleiro
						int c1 = ex / tileSize;
						int r1 = ey / tileSize;

						// obter a posi��o do bloco em branco
						int c2 = blankPos % size;
						int r2 = blankPos / size;

						// Converter em coord 1D
						int clickPos = r1 * size + c1;

						int dir = 0;

						// procuramos a dire��o para v�rios movimentos de pe�as de uma s� vez
						if (c1 == c2 && Math.abs(r1 - r2) > 0)
							dir = (r1 - r2) > 0 ? size : -size;
						else if (r1 == r2 && Math.abs(c1 - c2) > 0)
							dir = (c1 - c2) > 0 ? 1 : -1;

						if (dir != 0) {
							//movemos as pe�as na dire��o
							do {
								int newBlankPos = blankPos + dir;
								tiles[blankPos] = tiles[newBlankPos];
								blankPos = newBlankPos;
							} while (blankPos != clickPos);

							tiles[blankPos] = 0;
						}

						// verificamos se o jogo foi resolvido
						gameOver = isSolved();
					}

					// refazer o tabulerio
					repaint();
				}
			});

			newGame();
		}

		@Override
		public void newGame() {
			do {
				reset(); // redefinir no estado inicial
				shuffle(); // embaralhar
			} while (!isSolvable()); // at� que o jogo seja solucion�vel

			gameOver = false;
		}

		@Override
		public void reset() {
			for (int i = 0; i < tiles.length; i++) {
				tiles[i] = (i + 1) % tiles.length;
			}

			// definimos um bloco em branco por �ltimo
			blankPos = tiles.length - 1;
		}

		@Override
		public void shuffle() {
			// n�o inclua a pe�a em branco no embaralhamento, deixe na posi��o resolvida
			int n = nbTiles;

			while (n > 1) {
				int r = RANDOM.nextInt(n--);
				int tmp = tiles[r];
				tiles[r] = tiles[n];
				tiles[n] = tmp;
			}
		}

		// Apenas meias permuta��es do quebra-cabe�a s�o solucion�veis
		// Sempre que uma pe�a for precedida por uma pe�a de maior valor conta
		// como uma invers�o. No nosso caso, com o bloco em branco na posi��o resolvida,
		// o n�mero de invers�es deve ser par para que o quebra-cabe�a seja solucionado
		private boolean isSolvable() {
			int countInversions = 0;

			for (int i = 0; i < nbTiles; i++) {
				for (int j = 0; j < i; j++) {
					if (tiles[j] > tiles[i])
						countInversions++;
				}
			}

			return countInversions % 2 == 0;
		}

		private boolean isSolved() {
			if (tiles[tiles.length - 1] != 0) // se o bloco em branco n�o estiver na posi��o resolvida ==> n�o resolvido
				return false;

			for (int i = nbTiles - 1; i >= 0; i--) {
				if (tiles[i] != i + 1)
					return false;
			}

			return true;
		}

		private void drawGrid(Graphics2D g) {
			for (int i = 0; i < tiles.length; i++) {
				// convertemos coordenadas 1D em coordenadas 2D dado o tamanho da matriz 2D
				int r = i / size;
				int c = i % size;
				// convertemos em coordenadas na UI
				int x = margin + c * tileSize;
				int y = margin + r * tileSize;

				// verifique o caso especial para o bloco em branco
				if (tiles[i] == 0) {
					if (gameOver) {
						g.setColor(FOREGROUND_COLOR);
						drawCenteredString(g, "\u2713", x, y);
					}

					continue;
				}

				// Para os outros blocos
				g.setColor(getForeground());
				g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
				g.setColor(Color.BLACK);
				g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
				g.setColor(Color.WHITE);

				drawCenteredString(g, String.valueOf(tiles[i]), x, y);
			}
		}

		private void drawStartMessage(Graphics2D g) {
			if (gameOver) {
				g.setFont(getFont().deriveFont(Font.BOLD, 18));
				g.setColor(FOREGROUND_COLOR);
				String s = "Clique para iniciar um novo jogo";
				g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2, getHeight() - margin);
			}
		}

		private void drawCenteredString(Graphics2D g, String s, int x, int y) {
			// string central s para o bloco fornecido (x,y)
			FontMetrics fm = g.getFontMetrics();
			int asc = fm.getAscent();
			int desc = fm.getDescent();
			g.drawString(s, x + (tileSize - fm.stringWidth(s)) / 2, y + (asc + (tileSize - (asc + desc)) / 2));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawGrid(g2D);
			drawStartMessage(g2D);
		}

}
