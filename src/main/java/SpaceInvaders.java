package main.java;

import main.java.manager.AnimationManager;
import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;
import main.java.scene.MainGameScene;
import main.java.util.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;


/**
 *
 * @author
 */
public class SpaceInvaders implements Commons {
	public static Language lang;
	public static double delta = 0;

	private JButton start, help, lang_sel;

	JFrame gameFrame;
	JFrame frame2;
	JFrame frame3;
	JFrame frame4;

	private GameSceneManager gsm;
	private InputManager inputManager;
	private Canvas gameCanvas;
	private Thread gameThread;

	/*
	 * Constructor
	 */
	private static void listFilesForFolder(File folder) {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				try {
					Language lang = new Language(fileEntry.getPath());
					langs.put(lang.getLanguageName(), lang);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static HashMap<String, Language> langs = new HashMap<String, Language>();



	public SpaceInvaders() {
		lang = langs.get("default");

		gameFrame = new JFrame(lang.getTitle());
		frame2 = new JFrame(lang.getTitle());
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3 = new JFrame(lang.getHelpTopMessage());
		frame4 = new JFrame(lang.getLanguageSelection());

		String topmessage = "<html><br><br>" + lang.getTopMessage() + "</html>";
		String message = "<html>" + lang.getInitialMessage() + "</html>";

		start = new JButton(lang.getStartMessage());
		start.addActionListener(new ButtonListener());
		start.setBounds(800, 800, 200, 100);

		help = new JButton(lang.getHelpTopMessage());
		help.addActionListener(new HelpButton());

		lang_sel = new JButton(lang.getLanguageSelection());
		lang_sel.addActionListener(new LanguageButton());

		JLabel tekst = new JLabel(message, SwingConstants.CENTER);
		JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);

		Font font = new Font("Helvetica", Font.BOLD, 12);
		tekst.setFont(font);

		Font font2 = new Font("Helvetica", Font.BOLD, 20);
		toptekst.setFont(font2);

		frame2.setTitle(lang.getTitle());

		frame2.add(tekst);

		frame2.add(toptekst, BorderLayout.PAGE_START);
		JPanel nedredel = new JPanel();
		nedredel.add(help);
		nedredel.add(start);
		nedredel.add(lang_sel);

		frame2.add(nedredel, BorderLayout.PAGE_END);
		frame2.setSize(500, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		frame2.setResizable(false);

	}

	public void reloadLanguage() {
		gameFrame.dispose();
		frame2.dispose();
		frame3.dispose();
		frame4.dispose();

		gameFrame = new JFrame(lang.getTitle());
		frame2 = new JFrame(lang.getTitle());
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3 = new JFrame(lang.getHelpTopMessage());
		frame4 = new JFrame(lang.getLanguageSelection());

		String topmessage = "<html><br><br>" + lang.getTopMessage() + "</html>";
		String message = "<html>" + lang.getInitialMessage() + "</html>";

		start = new JButton(lang.getStartMessage());
		start.addActionListener(new ButtonListener());
		start.setBounds(800, 800, 200, 100);

		help = new JButton(lang.getHelpTopMessage());
		help.addActionListener(new HelpButton());

		lang_sel = new JButton(lang.getLanguageSelection());
		lang_sel.addActionListener(new LanguageButton());

		JLabel tekst = new JLabel(message, SwingConstants.CENTER);
		JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);

		Font font = new Font("Helvetica", Font.BOLD, 12);
		tekst.setFont(font);

		Font font2 = new Font("Helvetica", Font.BOLD, 20);
		toptekst.setFont(font2);

		frame2.setTitle(lang.getTitle());

		frame2.add(tekst);

		frame2.add(toptekst, BorderLayout.PAGE_START);
		JPanel nedredel = new JPanel();
		nedredel.add(help);
		nedredel.add(start);
		nedredel.add(lang_sel);

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
		gameThread = new Thread("Game Thread"){
			@Override
			public void run() {
				try {
					gameLoop();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		gameThread.start();
	}

	public void closeHelp() {
		frame3.dispose();
	}


	/**
	 * The main game loop
	 */
	public void gameLoop() throws IOException, InterruptedException {
		gameCanvas = new Canvas();
		gameCanvas.setPreferredSize(new Dimension(BOARD_WIDTH * GRAPHICS_SCALE, BOARD_HEIGHT * GRAPHICS_SCALE));

		gameFrame.add(gameCanvas, BorderLayout.CENTER);
		gameFrame.pack();

		var dimension = Toolkit.getDefaultToolkit().getScreenSize();
		var x = (int)((dimension.getWidth() - gameFrame.getWidth()) / 2);
		var y = (int)((dimension.getHeight() - gameFrame.getHeight()) / 2);
		gameFrame.setLocation(x, y);

		inputManager = new InputManager();
		gameFrame.addKeyListener(inputManager);
		gameFrame.setFocusable(true);

		// makes sure the frame regains focus when the user clicks back on the frame
		gameFrame.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				e.getComponent().requestFocus();
			}
		});
		gameFrame.setVisible(true);

		// load the main sprite sheet for all future levels
		AnimationManager.getInstance().load();;

		// setup the GameSceneManager
		gsm = new GameSceneManager();
		gsm.ingame = true;

		gsm.addScene(new MainGameScene(gsm));

		long lastTime = System.nanoTime();
		// used to reset fps and timer per second
		long timer = System.currentTimeMillis();
		final double ns = 1e9 / UPDATE_PER_SECOND;
//		double delta = 0;
		int fps = 0;
		int updates = 0;

		while (gsm.ingame) {
			var shouldUpdate = false;
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				updates++;
				delta--;
				shouldUpdate = true;
			}

			// rendering is currently fixed to the 60 FPS
			if (shouldUpdate) {
				render();
				fps++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				gameFrame.setTitle("Space Invaders  | " + " U: " + updates + " FPS: " + fps);
				fps = 0;
				updates = 0;
			}
		}
		gsm.dispose();
		gameFrame.dispose();
		System.out.println("game over");
	}

	private void update() {
		inputManager.update();
		gsm.input(inputManager);
		AnimationManager.getInstance().update();
		gsm.update();
	}

	private void render() {
		BufferStrategy bs = gameCanvas.getBufferStrategy();
		if (bs == null) {
			gameCanvas.createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D)bs.getDrawGraphics();
		g2d.scale(GRAPHICS_SCALE, GRAPHICS_SCALE);

		// clear the screen
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, BOARD_WIDTH * GRAPHICS_SCALE, BOARD_HEIGHT * GRAPHICS_SCALE);

		// render the current screen
		gsm.draw(g2d);

		g2d.dispose();
		// swap buffers
		bs.show();
	}


	public void closeLangauageSelection() {
		frame4.dispose();
	}

	/*
	 * Main
	 */
	public static void main(String[] args) {
		loadLanguage();
		new SpaceInvaders();
	}

	public static void loadLanguage() {
		final File folder = new File(Paths.get("").toAbsolutePath().toString() + File.separator + "lang");
		listFilesForFolder(folder);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gameFrame.setResizable(false);
			gameFrame.setLocationRelativeTo(null);
			gameFrame.setLayout(new BorderLayout());

			closeIntro();
		}
	}

	private class CloseHelp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeHelp();
		}
	}

	private class CloseLangSelect implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeLangauageSelection();
		}
	}

	private class LangSelect implements ActionListener {
		private String language;

		public LangSelect(String language) {
			this.language = language;
		}

		public void actionPerformed(ActionEvent event) {
			lang = langs.get(language);
			closeLangauageSelection();
			reloadLanguage();
		}

	}

	private class HelpButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton close = new JButton(lang.getCloseMessage());
			close.addActionListener(new CloseHelp());

			String topmessage = "<html><br>" + lang.getHelpTopMessage() + "</html>";
			String message = "<html>" + lang.getHelpMessage() + "</html> ";
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

	private class LanguageButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton close = new JButton(lang.getCloseMessage());
			close.addActionListener(new CloseLangSelect());

			String topmessage = "<html><br>" + lang.getLanguageSelection() + "</html>";
			JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);
			Font font2 = new Font("Helvetica", Font.BOLD, 18);
			toptekst.setFont(font2);
			JPanel nedredel = new JPanel();
			for (String str : langs.keySet()) {
				JButton button = new JButton(str);
				button.addActionListener(new LangSelect(str));
				nedredel.add(button);
			}

			frame4.add(toptekst, BorderLayout.PAGE_START);
			frame4.add(nedredel, BorderLayout.CENTER);
			frame4.add(close, BorderLayout.PAGE_END);
			frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame4.setSize(250, 290);
			frame4.setResizable(false);
			frame4.setLocationRelativeTo(null);
			frame4.setVisible(true);
		}
	}
}