package TiendaVista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Autores extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JMenuBar menuBar;
    private JMenu mnNewMenu;
    private JMenuItem mntmNewMenuItem;
    private JMenuItem mntmNewMenuItem_1;
    private JMenuItem mntmNewMenuItem_2;
    private JMenuItem mntmNewMenuItem_3;
    private JMenuItem mntmNewMenuItem_4;

    private JLabel lblFoto;
    private JLabel lblNombre;
    private JLabel lblRol;

    public Autores() {
        setTitle("Autores del Proyecto");
        setBounds(100, 100, 527, 360);
        getContentPane().setLayout(new BorderLayout());


        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);
        getContentPane().add(contentPanel, BorderLayout.CENTER);


        lblFoto = new JLabel("");
        lblFoto.setBounds(29, 23, 206, 246);
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(lblFoto);

        lblNombre = new JLabel("");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 22));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombre.setBounds(203, 60, 300, 30);
        contentPanel.add(lblNombre);

        lblRol = new JLabel("");
        lblRol.setFont(new Font("Arial", Font.PLAIN, 17));
        lblRol.setHorizontalAlignment(SwingConstants.CENTER);
        lblRol.setBounds(203, 100, 300, 30);
        contentPanel.add(lblRol);

       

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnNewMenu = new JMenu("Autores");
        menuBar.add(mnNewMenu);

        mntmNewMenuItem = new JMenuItem("Jean Pieer");
        mnNewMenu.add(mntmNewMenuItem);

        mntmNewMenuItem_1 = new JMenuItem("Vampeta");
        mnNewMenu.add(mntmNewMenuItem_1);

        mntmNewMenuItem_2 = new JMenuItem("Jesus");
        mnNewMenu.add(mntmNewMenuItem_2);

        mntmNewMenuItem_3 = new JMenuItem("Gabriel");
        mnNewMenu.add(mntmNewMenuItem_3);

        mntmNewMenuItem_4 = new JMenuItem("Rafaella");
        mnNewMenu.add(mntmNewMenuItem_4);

     

        mntmNewMenuItem.addActionListener(e -> mostrarAutor("Jean Pieer", "Ingeniero de Sistemas"));
        mntmNewMenuItem_1.addActionListener(e -> mostrarAutor("Vampeta", "Ingeniero de Sistemas"));
        mntmNewMenuItem_2.addActionListener(e -> mostrarAutor("Jesus", "Ingeniero de Sistemas"));
        mntmNewMenuItem_3.addActionListener(e -> mostrarAutor("Gabriel", "Ingeniero de Sistemas"));
        mntmNewMenuItem_4.addActionListener(e -> mostrarAutor("Rafaella", "Ingeniero de Sistemas"));
    }


    private void mostrarAutor(String nombre, String rol) {

        
        lblNombre.setText(nombre);
        lblRol.setText(rol);

      
        try {
            ImageIcon icon = new ImageIcon("src/imagenes/" + nombre + ".jpg");
            Image img = icon.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
            lblFoto.setText("");
        } catch (Exception e) {
            lblFoto.setIcon(null);
            lblFoto.setText("[Sin foto]");
        }
    }

    public static void main(String[] args) {
        Autores dialog = new Autores();
        dialog.setVisible(true);
    }
}

