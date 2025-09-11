package SistemaTiendas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class Interfaz extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private JLabel lblNewLabel;
	private JLabel lblPrecio;
	private JLabel lblStock;
	private JTextField txtnom;
	private JTextField txtprecio;
	private JTextField txtstock;
	private JButton btnNewButton;
	private JLabel lblRegistroDeProducto;
	private JButton btnLimpiar;
	private JLabel lblcodigo;
	private JLabel lblcategoria;
	private JComboBox txtcategoria;
	private JScrollPane scrollPane;
	private JTextArea txtS;
	private JTextField txtcodigo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
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
	public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBounds(0, 0, 484, 370);
		contentPane.add(contentPane_1);
		
		lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(10, 66, 77, 21);
		contentPane_1.add(lblNewLabel);
		
		lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 97, 108, 21);
		contentPane_1.add(lblPrecio);
		
		lblStock = new JLabel("Stock:");
		lblStock.setBounds(191, 35, 108, 21);
		contentPane_1.add(lblStock);
		
		txtnom = new JTextField();
		txtnom.setColumns(10);
		txtnom.setBounds(67, 67, 96, 19);
		contentPane_1.add(txtnom);
		
		txtprecio = new JTextField();
		txtprecio.setColumns(10);
		txtprecio.setBounds(67, 98, 96, 19);
		contentPane_1.add(txtprecio);
		
		txtstock = new JTextField();
		txtstock.setColumns(10);
		txtstock.setBounds(252, 36, 96, 19);
		contentPane_1.add(txtstock);
		
		btnNewButton = new JButton(" Guardar Producto");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(10, 143, 144, 43);
		contentPane_1.add(btnNewButton);
		
		lblRegistroDeProducto = new JLabel("Registro de Producto");
		lblRegistroDeProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblRegistroDeProducto.setBounds(181, 0, 125, 31);
		contentPane_1.add(lblRegistroDeProducto);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(this);
		btnLimpiar.setBounds(174, 143, 125, 43);
		contentPane_1.add(btnLimpiar);
		
		lblcodigo = new JLabel("Codigo:");
		lblcodigo.setBounds(10, 35, 108, 21);
		contentPane_1.add(lblcodigo);
		
		lblcategoria = new JLabel("Categoria:");
		lblcategoria.setBounds(191, 66, 85, 21);
		contentPane_1.add(lblcategoria);
		
		txtcategoria = new JComboBox();
		txtcategoria.setModel(new DefaultComboBoxModel(new String[] {"BEBIDAS", "SNACKS", "DULCES", "BEBIDAS ALCOHOLICAS"}));
		txtcategoria.setBounds(262, 66, 120, 21);
		contentPane_1.add(txtcategoria);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 206, 423, 139);
		contentPane_1.add(scrollPane);
		
		txtS = new JTextArea();
		scrollPane.setViewportView(txtS);
		
		txtcodigo = new JTextField();
		txtcodigo.setBounds(67, 37, 96, 19);
		contentPane_1.add(txtcodigo);
		txtcodigo.setColumns(10);

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLimpiar) {
			do_btnLimpiar_actionPerformed(e);
		}
		if (e.getSource() == btnNewButton) {
			do_btnNewButton_actionPerformed(e);
		}
	}
	
	protected void do_btnNewButton_actionPerformed(ActionEvent e) {
		String nom = txtnom.getText();
		String precio = txtprecio.getText();
		String stock = txtstock.getText();
		String codigo = txtcodigo.getText();
		String categoria = txtcategoria.getSelectedItem().toString();
		
		if(nom.isEmpty() || precio.isEmpty() || stock.isEmpty() || codigo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Complete todos los campos por favor!!");
			return;
		} else {
			
				
				    int c = Integer.parseInt(codigo.trim());
					double p = Double.parseDouble(precio.trim());
					int s = Integer.parseInt(stock.trim());
					
					txtS.append("--------------------------------------------------------------------------------------------------------------\n");
					txtS.append("                                        Registro de Bodega                                                    \n");
 					txtS.append("--------------------------------------------------------------------------------------------------------------\n");
					 
				
					Registro d = new Registro(nom, categoria, p, s, c);
					 txtS.append(d.getCodigo() + "\t"  + d.getNombre() + "\t"  + d.getPrecio() + "\t" + d.getStock() + "\t"  + d.getCategoria() + "\n");
				             
				      
					lblcodigo.setText("");
					txtnom.setText("");
					txtprecio.setText("");
					txtstock.setText("");
					

					
			 
		}
	}
	
	protected void do_btnLimpiar_actionPerformed(ActionEvent e) {
	
		txtnom.setText("");
		txtprecio.setText("");
		txtstock.setText("");
		txtcodigo.setText("");
	}
}
