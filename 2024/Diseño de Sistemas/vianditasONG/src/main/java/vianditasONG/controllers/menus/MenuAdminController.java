package vianditasONG.controllers.menus;

import io.javalin.http.Context;
import lombok.Builder;

@Builder
public class MenuAdminController {

    public void getMenu(Context context){
        context.render("admin/menu_admin.hbs");
    }
}
