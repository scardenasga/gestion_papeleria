package co.edu.unbosque.view.panel;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelContabilidad extends JPanel {
    
    public PanelContabilidad(){
        this.setBackground(Color.magenta);
        this.setLayout(new GridBagLayout());

        this.setVisible(false);
    }
}
