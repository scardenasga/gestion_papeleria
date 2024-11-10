package co.edu.unbosque;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import co.edu.unbosque.view.Prueba;
import co.edu.unbosque.view.Ventana;
import co.edu.unbosque.view.VentanaPrincipal;
import co.edu.unbosque.view.MenuDesplegablePrueba;

public class Controller implements ActionListener{

    private VentanaPrincipal vp;
    private Prueba pb;
    private MenuDesplegablePrueba mdp;
    private Ventana ven;

    public Controller() {
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // this.vp = new VentanaPrincipal ();
        // this.pb = new Prueba();
        // this.mdp = new MenuDesplegablePrueba();

        this.ven = new Ventana();
    }

    public void run() {

    }


    public void addActionListeners(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
