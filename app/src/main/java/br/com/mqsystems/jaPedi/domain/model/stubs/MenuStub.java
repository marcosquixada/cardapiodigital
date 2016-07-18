package br.com.mqsystems.jaPedi.domain.model.stubs;


import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.activity.about.AboutActivity;
import br.com.mqsystems.jaPedi.domain.model.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valter ramos on 3/30/15.
 */
public class MenuStub {

    public static List<Object> createMenu() {
        List<Object> items = new ArrayList<>();

        //items.add(new MenuSection(R.drawable.ic_order, "MENU"));
        items.add(new Menu(1, R.drawable.drawer_boll, "Sobre nós", AboutActivity.class));

        return items;
    }
}
