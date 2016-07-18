package br.com.mqsystems.jaPedi.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by valter ramos on 3/30/15.
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = -7723706379152844320L;

    public long id;
    public int icon;
    public String title;
    public Class<?> clazz;
    public List<Menu> subMenu;

    public Menu() {
    }

    public Menu(long id, int icon, String title, Class<?> clazz, List<Menu> subMenu) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.clazz = clazz;
        this.subMenu = subMenu;
    }

    public Menu(long id, int icon, String title, Class<?> clazz) {
        this(id, icon, title, clazz, null);
    }

    public Menu(long id, String title, Class<?> clazz) {
        this(id, 0, title, clazz, null);
    }

    public Menu(long id, int icon, String title) {
        this(id, icon, title, null, null);
    }

    public boolean hasSubMenu() {
        return (subMenu != null && !subMenu.isEmpty());
    }
}
