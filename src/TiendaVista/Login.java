package TiendaVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    private JTextField txtUsuario;
    private JPasswordField txtContra;
    private JButton btnIngresar;

    private int intentos = 0; 

    public Login() {

        setTitle("Inicio de Sesión");
        setSize(428, 301);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setBounds(110, 20, 300, 30);
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        getContentPane().add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(70, 80, 120, 20);
        getContentPane().add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 80, 180, 25);
        getContentPane().add(txtUsuario);

        JLabel lblContra = new JLabel("Contraseña:");
        lblContra.setBounds(70, 120, 120, 20);
        getContentPane().add(lblContra);

        txtContra = new JPasswordField();
        txtContra.setBounds(150, 120, 180, 25);
        getContentPane().add(txtContra);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(150, 170, 120, 35);
        btnIngresar.addActionListener(this);
        getContentPane().add(btnIngresar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String usuario = txtUsuario.getText().trim();
        String contra = String.valueOf(txtContra.getPassword()).trim();

        if (usuario.equals("Tia Felicita") && contra.equals("998454266")) {

            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario);

            MenúPrincipal menu = new MenúPrincipal();
            menu.setVisible(true);

            dispose(); 
            return;
        }

        intentos++;
        int restantes = 3 - intentos;

        if (restantes > 0) {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos.\nIntentos restantes: " + restantes);
        } else {
            JOptionPane.showMessageDialog(this,
                "Has excedido el número máximo de intentos.\nEl sistema se cerrará.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Login lg = new Login();
        lg.setVisible(true);
    }	
}

