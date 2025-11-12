package TiendaVista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import TiendaVista.RegistroProducto;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenúPrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnRegistroProducto;
	private JButton btnProcesoDeVenta;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenúPrincipal frame = new MenúPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	

	/**
	 * Create the frame.
	 */
	public MenúPrincipal() {
		setTitle("Menú");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 555, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Sistema Gestión de Tienda");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(138, 59, 287, 24);
		contentPane.add(lblNewLabel);
		
		btnRegistroProducto = new JButton("Registro de Producto 💾");
		btnRegistroProducto.addActionListener(this);
		btnRegistroProducto.setBounds(185, 104, 186, 34);
		contentPane.add(btnRegistroProducto);
		
		btnProcesoDeVenta = new JButton("Proceso de Venta 💸");
		btnProcesoDeVenta.addActionListener(this);
		btnProcesoDeVenta.setBounds(185, 150, 186, 34);
		contentPane.add(btnProcesoDeVenta);
		
		btnSalir = new JButton("Salir 🚪");
		btnSalir.addActionListener(this);
		btnSalir.setBounds(185, 197, 186, 34);
		contentPane.add(btnSalir);

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnProcesoDeVenta) {
			do_btnProcesoDeVenta_actionPerformed(e);
		}
		if (e.getSource() == btnSalir) {
			do_btnSalir_actionPerformed(e);
		}
		if (e.getSource() == btnRegistroProducto) {
			do_btnNewButton_actionPerformed(e);
		}
	}
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		RegistroProducto RP = new RegistroProducto();
		RP.setVisible(true);
		
	}
	protected void do_btnSalir_actionPerformed(ActionEvent e) {
	dispose();
	}
	protected void do_btnProcesoDeVenta_actionPerformed(ActionEvent e) {
	ProcesoVenta PV = new ProcesoVenta();
	PV.setVisible(true);
	}
}
