package galaconomy.universe.map;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;

public class VoidElement implements IDisplayable {

    @Override
    public String displayName() {
        return "Galaconomy";
    }

    @Override
    public String displayDscr() {
        return "Click onto map element to see details";
    }

    @Override
    public String getImage() {
        return Constants.FOLDER_IMG + "galaconomy.png";
    }
}
