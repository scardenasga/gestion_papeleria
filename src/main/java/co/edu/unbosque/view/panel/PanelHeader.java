package co.edu.unbosque.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lombok.Getter;
import lombok.Setter;
import raven.drawer.Drawer;

@Getter
@Setter
public class PanelHeader extends JPanel {

    private JLabel titulo;

    public PanelHeader(){
        // this.setBackground(Color.GREEN);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        gbc.weighty = 1.0;
        gbc.fill= GridBagConstraints.NONE;
        
        JButton boton = new JButton();
        boton.setText("Menu");
        boton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Drawer.getInstance().showDrawer();
            }
            
        });

        boton.setPreferredSize(new Dimension(100, 60)); // Tamaño cuadrado (100x100 píxeles)
        
        this.add(boton, gbc);


        gbc.gridx = 1;
        gbc.weightx = 0.85;
        gbc.weighty = 1.0;
        gbc.fill= GridBagConstraints.NONE;


        titulo = new JLabel();
        titulo.setText("Ventas");
        titulo.setFont(new Font("Arial", Font.BOLD, 40)); // Cambiar el tamaño del texto
        titulo.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        this.add(titulo,gbc);

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));  // El JPanel no puede crecer verticalmente más allá de 60px

        this.setVisible(true);
    }
}
