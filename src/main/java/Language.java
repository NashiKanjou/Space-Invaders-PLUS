package main.java;

import java.io.IOException;

public class Language {

    private String TITLE;
    private String START;
    private String CLOSE;

    private String TOP_MESSAGE;
    private String INITIAL_MESSAGE;

    private String HELP_TOP_MESSAGE;
    private String HELP_MESSAGE;
    private String ENDING_WIN;
    private String ENDING_LOSE;
    private String INVADE;

    private String Lang_select;

    private String Lang_Name;

    public Language(String path) throws IOException {
        Config config = new Config(path);
        Lang_Name=config.getString("lang");
        TITLE = config.getString("title");
        START = config.getString("start");
        CLOSE = config.getString("close");

        TOP_MESSAGE = config.getString("top_message");
        INITIAL_MESSAGE = config.getString("initial_message");

        HELP_TOP_MESSAGE = config.getString("help_top_message");
        HELP_MESSAGE = config.getString("help_message");
        ENDING_WIN = config.getString("ending_win");
        ENDING_LOSE = config.getString("ending_lose");
        INVADE = config.getString("invade");

        Lang_select = config.getString("language_selection");
        //read file
    }

    public String getLanguageSelection(){
        return Lang_select;
    }
    public String getLanguageName(){
        return Lang_Name;
    }
    public String getEndingWinMessage(){
        return ENDING_WIN;
    }
    public String getInvadeMessage(){
        return INVADE;
    }
    public String getEndingLoseMessage(){
        return ENDING_LOSE;
    }
    public String getCloseMessage(){
        return CLOSE;
    }
    public String getTitle(){
        return TITLE;
    }
    public String getStartMessage(){
        return START;
    }
    public String getTopMessage(){
        return TOP_MESSAGE;
    }
    public String getInitialMessage() {
        return INITIAL_MESSAGE;
    }
    public String getHelpTopMessage(){
        return HELP_TOP_MESSAGE;
    }
    public String getHelpMessage(){
        return HELP_MESSAGE;
    }

}
