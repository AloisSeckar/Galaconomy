package galaconomy.constants;

public class Constants {
    
    public static final String PROGRAM_NAME = "Galaconomy 0.1";
    
    public static final long PLAYER_MONEY = 10000;
    
    public static final int AVAILABLE_PLANETS = 68;
    public static final int AVAILABLE_PLAYERS = 13;
    public static final int AVAILABLE_SHIPS = 5;
    public static final int AVAILABLE_STARS = 36;
    
    public static final int MAX_X = 100;
    public static final int MAX_Y = 80;
    
    public static final int SCREEN_X = (Constants.MAX_X + 2) * 8;
    public static final int SCREEN_Y = (Constants.MAX_Y + 2) * 8;
    public static final int SIDE_PANEL_X = 275;
    public static final int BOTTOM_PANEL_Y = 250;
    
    public static final String DEFAULT_IMAGE = "default";
    
    private static final String SEPARATOR = "/";
    public static final String FOLDER_RES = SEPARATOR;
    public static final String FOLDER_CSS = FOLDER_RES + "css" + SEPARATOR;
    public static final String FOLDER_IMG = FOLDER_RES + "img" + SEPARATOR;
    public static final String BUILDINGS_FOLDER = FOLDER_IMG + "buildings" + SEPARATOR;
    public static final String GOODS_FOLDER = FOLDER_IMG + "goods" + SEPARATOR;
    public static final String ICONS_FOLDER = FOLDER_IMG + "icons" + SEPARATOR;
    public static final String PLANETS_FOLDER = FOLDER_IMG + "planets" + SEPARATOR;
    public static final String PLAYERS_FOLDER = FOLDER_IMG + "players" + SEPARATOR;
    public static final String SHIPS_FOLDER = FOLDER_IMG + "ships" + SEPARATOR;
    public static final String STARS_FOLDER = FOLDER_IMG + "stars" + SEPARATOR;
    public static final String TILES_FOLDER = FOLDER_IMG + "tiles" + SEPARATOR;
    
    public static final String SQLITE_DB = "jdbc:sqlite:sample.db";

}
