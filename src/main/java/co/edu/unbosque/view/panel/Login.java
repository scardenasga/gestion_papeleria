package co.edu.unbosque.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.unbosque.view.util.login.BackGround;
import co.edu.unbosque.view.util.login.utility.Button;
import co.edu.unbosque.view.util.login.utility.PasswordField;
import co.edu.unbosque.view.util.login.utility.TextField;

public class Login extends JPanel {
    
    
    private TextField username;
    private PasswordField password;
    private JLabel loginLabel;
    private Button loginButton;

    public Login(){
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(500, 500));
        this.setOpaque(false); // Hacemos que el panel sea transparente para ver el fondo

        // Configuración de los componentes con GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
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
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(loginLabel, gbc);

        // Configuración del campo de texto username
        username = new TextField();
        username.setHint("Usuario");
        username.setPreferredSize(new Dimension(350, 30));

        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        this.add(username, gbc);

        // Configuración del campo de contraseña password
        password = new PasswordField();
        password.setHint("Contraseña");
        password.setPreferredSize(new Dimension(350, 30));

        gbc.gridy = 2;
        gbc.weighty = 0.1;
        this.add(password, gbc);

        // Configuración del botón loginButton
        loginButton = new Button();
        loginButton.setText("Inicio Sesión");
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("arial", Font.PLAIN, 12));
        loginButton.setPreferredSize(new Dimension(200, 80));

        gbc.gridy = 3;
        gbc.weightx = 0.4;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(loginButton, gbc);
        
        
        
        
        // this.add(bg);

    }
    
}
