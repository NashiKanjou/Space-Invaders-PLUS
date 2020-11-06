import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.swing.*;

/**
 * 
 * @author 
*/
public class SpaceInvaders extends JFrame implements Commons {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4905230094675077405L;
	public static Language lang;
	private JButton start, help, lang_sel;

	JFrame frame;
	JFrame frame2;
	JFrame frame3;
	JFrame frame4;
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
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	public static HashMap<String, Language> langs = new HashMap<String,Language>();

	public SpaceInvaders() {
		lang = langs.get("default");

		frame = new JFrame(lang.getTitle());
		frame2 = new JFrame(lang.getTitle());
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
	public void reloadLanguage(){
		frame.dispose();
		frame2.dispose();
		frame3.dispose();
		frame4.dispose();

		frame = new JFrame(lang.getTitle());
		frame2 = new JFrame(lang.getTitle());
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
	}

	public void closeHelp() {
		frame3.dispose();
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
		final File folder = new File(Paths.get("").toAbsolutePath().toString()+ File.separator + "lang");
		listFilesForFolder(folder);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(BOARD_WIDTH, BOARD_HEIGTH);
			frame.getContentPane().add(new Board());
			frame.setResizable(false);
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

	private class CloseLangSelect implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeLangauageSelection();
		}
	}

	private class LangSelect implements ActionListener {
		private String language;
		public LangSelect(String language){
			this.language=language;
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
			for(String str: langs.keySet()) {
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