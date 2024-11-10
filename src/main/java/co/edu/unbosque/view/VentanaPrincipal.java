package co.edu.unbosque.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.unbosque.view.util.login.BackGround;
import co.edu.unbosque.view.util.login.utility.Button;
import co.edu.unbosque.view.util.login.utility.PasswordField;
import co.edu.unbosque.view.util.login.utility.TextField;


public class VentanaPrincipal extends JFrame {
    
    private BackGround bg;
    private JPanel jp;
    private TextField username;
    private PasswordField password;
    private JLabel loginLabel;
    private Button loginButton;
    
    public VentanaPrincipal(){
        this.setTitle("Rosita");
        this.setSize(new Dimension(700,550));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(700,550));

        bg = new BackGround();
        bg.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        

        this.jp = new JPanel();
        this.jp.setLayout(new GridBagLayout());
        
        this.bg.setBlur(jp);
        jp.setPreferredSize(new Dimension(500,500));
        jp.setOpaque(false);

        gbc.insets = new Insets(10, 10, 10, 10);


        // Configuración del JLabel loginLabel
        loginLabel = new JLabel("Inicio de sesión");
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("arial", Font.BOLD, 30));
        loginLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL; // Expande horizontalmente
        jp.add(loginLabel, gbc);

        // Configuración del campo de texto username
        this.username = new TextField();
        username.setHint("Usuario");
        username.setPreferredSize(new Dimension(350, 30)); // Ajuste de ancho deseado

        gbc.gridy = 1;
        gbc.weightx = 0.0; // Expande este componente horizontalmente
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        jp.add(username, gbc);

        // Configuración del campo de contraseña password
        this.password = new PasswordField();
        password.setHint("Contraseña");
        password.setPreferredSize(new Dimension(350, 30)); // Ajuste de ancho deseado

        gbc.gridy = 2;
        // gbc.weightx = 1.0; // Expande este componente horizontalmente
        gbc.weighty = 0.1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        jp.add(password, gbc);

        // Configuración del botón loginButton
        loginButton = new Button();
        loginButton.setText("Inicio Sesión");
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("arial", Font.PLAIN, 12));
        loginButton.setPreferredSize(new Dimension(200, 80)); // Ajuste de ancho deseado

        gbc.gridy = 3;
        gbc.weightx = 0.4; // No expande horizontalmente
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.NORTH;
        jp.add(loginButton, gbc);
        
        
        
        this.bg.add(jp);
        
        
        getContentPane().add(bg);
        
        this.setVisible(true);
    }
    
}
