
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import manager.GameSceneManager;
import manager.KeyboardManager;
import scene.MainGameScene;
import scene.Won;
import util.Commons;

/**
 * 
 * @author
 */
public class SpaceInvaders implements Commons {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4905230094675077405L;

	private JButton start, help;

	/*
	 * Inicio
	 */
	private static final String TOP_MESSAGE = "Space Invaders <br> Java Version";
	private static final String INITIAL_MESSAGE = "Ajude-nos, capit�o impressionante!!"
			+ "<br>Os alien�genas est�o tentando invadir nosso planeta." + "<br><br><br>Sua miss�o:"
			+ "<br><br>Matar todos os alien�genas invasores antes que eles consigam invadir o planeta Terra."
			+ "<br>E, de prefer�ncia, n�o morra durante a batalha!" + "<br><br><br>BOA SORTE!!!";
	/*
	 * Ajuda
	 */
	private static final String HELP_TOP_MESSAGE = "Ajuda";
	private static final String HELP_MESSAGE = "Controles: "
			+ "<br><br>Movimento � Esquerda: <br>Seta Esquerda do teclado"
			+ "<br><br>Movimento � Direita: <br>Seta Direita do teclado" + "<br><br>Atirar: <br>Barra de espa�o";

	JFrame frame = new JFrame("Space Invaders");
	JFrame frame2 = new JFrame("Space Invaders");
	JFrame frame3 = new JFrame("Ajuda");

	private GameSceneManager gsm;
	private KeyboardManager keyboardManager;

	/*
	 * Constructor
	 */
	public SpaceInvaders() {
		String topmessage = "<html><br><br>" + TOP_MESSAGE + "</html>";
		String message = "<html>" + INITIAL_MESSAGE + "</html>";

		start = new JButton("Iniciar Miss�o");
		start.addActionListener(new ButtonListener());
		start.setBounds(800, 800, 200, 100);

		help = new JButton("Ajuda");
		help.addActionListener(new HelpButton());

		JLabel tekst = new JLabel(message, SwingConstants.CENTER);
		JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);

		Font font = new Font("Helvetica", Font.BOLD, 12);
		tekst.setFont(font);

		Font font2 = new Font("Helvetica", Font.BOLD, 20);
		toptekst.setFont(font2);

		frame2.setTitle("Space Invaders");

		frame2.add(tekst);

		frame2.add(toptekst, BorderLayout.PAGE_START);
		JPanel nedredel = new JPanel();
		nedredel.add(help);
		nedredel.add(start);

		frame2.add(nedredel, BorderLayout.PAGE_END);
		frame2.setSize(500, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		frame2.setResizable(false);

	}

	public void closeIntro() {
		frame2.dispose();
		frame3.dispose();

		// begin the main game loop
		gameLoop();
	}

	public void closeHelp() {
		frame3.dispose();
	}

	/**
	 * The main game loop
	 */
	private void gameLoop() {
		new Thread(() -> {
			JPanel panel = new JPanel();
			panel.setSize(BOARD_WIDTH, BOARD_HEIGTH);
			panel.setFocusable(true);
			panel.requestFocus();

			frame.setContentPane(panel);
			// setup the GameSceneManager

			gsm = new GameSceneManager(panel);
			keyboardManager = new KeyboardManager(panel);
			gsm.addScene(new MainGameScene(gsm));

			long beforeTime, timeDiff, sleep;

			while (gsm.isIngame()) {
				beforeTime = System.currentTimeMillis();

				gsm.update();
				keyboardManager.update();
				gsm.input(keyboardManager);
				gsm.draw(panel.getGraphics());

				timeDiff = System.currentTimeMillis() - beforeTime;
				sleep = DELAY - timeDiff;

				if (sleep < 0)
					sleep = 1;
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
				}
				beforeTime = System.currentTimeMillis();
			}
			System.out.println("game over");
			gsm.dispose();
			frame.dispose();
		}, "Game Loop").start();
	}

	/*
	 * Main
	 */
	public static void main(String[] args) {
		new SpaceInvaders();
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(BOARD_WIDTH, BOARD_HEIGTH);

			// frame.getContentPane().add(new MainGameScene(gsm));
			frame.setResizable(true);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			closeIntro();

		}
	}

	private class CloseHelp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeHelp();
		}
	}

	private class HelpButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton close = new JButton("Fechar");
			close.addActionListener(new CloseHelp());

			String topmessage = "<html><br>" + HELP_TOP_MESSAGE + "</html>";
			String message = "<html>" + HELP_MESSAGE + "</html> ";
			JLabel tekst = new JLabel(message, SwingConstants.CENTER);
			JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);

			Font font = new Font("Helvetica", Font.BOLD, 12);
			tekst.setFont(font);

			Font font2 = new Font("Helvetica", Font.BOLD, 20);
			toptekst.setFont(font2);

			frame3.add(tekst);

			frame3.add(toptekst, BorderLayout.PAGE_START);

			frame3.add(close, BorderLayout.PAGE_END);
			frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame3.setSize(250, 290);
			frame3.setResizable(false);
			frame3.setLocationRelativeTo(null);
			frame3.setVisible(true);
		}
	}
}