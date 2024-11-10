package co.edu.unbosque.view.util.menu;

import java.util.function.Consumer;

import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.SimpleMenuOption;
import raven.swing.AvatarIcon;

public class MenuDesplegable extends SimpleDrawerBuilder{

    private String selectedOption;
    private Consumer<String> panelUpdater;

    public void setPanelUpdater(Consumer<String> panelUpdater) {
        this.panelUpdater = panelUpdater;
    }
    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
            .setIcon(new AvatarIcon(getClass().getResource("/imagenes/ciudad.png"), 60, 60, 60))
            .setTitle("Nombre de usuario")
            .setDescription("Descripcion o que tipo de usuario es");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        String[][] menu = {
            {"~Ventas~"},
            {"Ventas"},
            {"Pedidos"},
            {"~Inventario~"},
            {"Inventario"},
            {"~Usuarios~"},
            {"Empleados"},
            {"Proveedores"},
            {"~Contabilidad~"},
            {"Contabilidad","Ventas", "Costos", "Cierre Caja"},
            {"~Otros~"},
            {"Cerrar Seción"}
        };

        return new SimpleMenuOption()
        .setMenus(menu)
        .addMenuEvent(new MenuEvent() {
            @Override
            public void selected(MenuAction action, int index, int subIndex) {
                selectedOption = index+""+subIndex; // Actualizar la opción seleccionada
                System.out.println("Menu: "+index+" Sub: "+subIndex+" opcion: "+selectedOption);
                if (panelUpdater != null) {
                    panelUpdater.accept(selectedOption); // Llama al actualizador en Ventana
                }
            }
        });
//          
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        
        return new SimpleFooterData()
                .setTitle("Papeleria Rosita")
                .setDescription("Version 1.0.0");
    }

    

    
}
