package app.com.loyaltyapp.bounty.models;

import java.io.Serializable;

public class MenuModel implements Serializable {


    String menu_name;
    String menu_image;

    public MenuModel(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(String menu_image) {
        this.menu_image = menu_image;
    }
}
