
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
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
	///
	Properties properties = new Properties();
	InputStream configFileIn;
	OutputStream configFileOut;


	///
	String[] aimStrings = {"Mouse", "Keys"};
	JComboBox aimList = new JComboBox(aimStrings); // combobox for aim type selection
	// protected static int aimType = 0;


	JFrame frame;
	JFrame frame2;
	JFrame frame3;
	JFrame frame4;
	JFrame frame5;
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

		/*/////// CONFIG AND PLAY SETTINGS
		// start reading from config file, if it exists
		try {
			configFile = new FileInputStream("/src/main/java/config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// load the config file into the properties, if "parsing" is successful
		try {
			properties.load(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
/*

		try {
			String propFileName = "config.properties";

			configFileIn = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (configFileIn != null) {
				properties.load(configFileIn);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
*/


		aimList.setBounds(900,900,100,100);
		aimList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(aimList.getSelectedItem().toString() == "Mouse") { // mouse
					// set var to 0
					//aimType = 0;
					properties.setProperty("AimControlType", "test mouse"); // permanently remembers this option

				}
				if(aimList.getSelectedItem().toString() == "Keys") { // keys
					// set var to 1
					// aimType = 1;
					properties.setProperty("AimControlType", "test keys"); // permanently remembers this option
				}
			}
		});

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
		nedredel.add(aimList);

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
				//String message = "<html>" + lang.getHelpMessage() + "</html> ";
				//JLabel tekst = new JLabel(message, SwingConstants.CENTER);
				//Font font = new Font("Helvetica", Font.BOLD, 12);
				//tekst.setFont(font);
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