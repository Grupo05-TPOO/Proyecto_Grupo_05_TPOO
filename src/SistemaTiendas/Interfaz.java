package SistemaTiendas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Interfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtnom;
	private JTextField txtprecio;
	private JTextField txtstock;
	private JTextField txtcodigo;
	private JComboBox txtcategoria;
	private JTable tabla;
	private DefaultTableModel modelo;

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

	public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(10, 66, 77, 21);
		contentPane.add(lblNewLabel);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 97, 108, 21);
		contentPane.add(lblPrecio);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(191, 35, 108, 21);
		contentPane.add(lblStock);

		txtnom = new JTextField();
		txtnom.setBounds(67, 67, 96, 19);
		contentPane.add(txtnom);
		txtnom.setColumns(10);

		txtprecio = new JTextField();
		txtprecio.setColumns(10);
		txtprecio.setBounds(67, 98, 96, 19);
		contentPane.add(txtprecio);

		txtstock = new JTextField();
		txtstock.setColumns(10);
		txtstock.setBounds(252, 36, 96, 19);
		contentPane.add(txtstock);

		JButton btnNewButton = new JButton("💾 Guardar Producto");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = txtnom.getText();
				String precio = txtprecio.getText();
				String stock = txtstock.getText();
				String codigo = txtcodigo.getText();
				String categoria = txtcategoria.getSelectedItem().toString();

				if(nom.isEmpty() || precio.isEmpty() || stock.isEmpty() || codigo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete todos los campos por favor!!");
					return;
				} else {
					try {
						int c = Integer.parseInt(codigo);
						double p = Double.parseDouble(precio);
						int s = Integer.parseInt(stock);

						modelo.addRow(new Object[] {c, nom, p, s, categoria});

						txtcodigo.setText("");
						txtnom.setText("");
						txtprecio.setText("");
						txtstock.setText("");

					} catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "El precio y stock deben ser números válidos");
					}
				}
			}
		});
		btnNewButton.setBounds(10, 143, 144, 43);
		contentPane.add(btnNewButton);

		JLabel lblRegistroDeProducto = new JLabel("Registro de Producto");
		lblRegistroDeProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblRegistroDeProducto.setBounds(181, 0, 125, 31);
		contentPane.add(lblRegistroDeProducto);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 214, 474, 146);
		contentPane.add(scrollPane);

		modelo = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Código", "Nombre", "Precio", "Stock", "Categoría"}
		);
		tabla = new JTable(modelo);
		scrollPane.setViewportView(tabla);

		JButton btnLimpiar = new JButton("🗑 Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtnom.setText("");
				txtprecio.setText("");
				txtstock.setText("");
				txtcodigo.setText("");
				modelo.setRowCount(0); 
			}
		});
		btnLimpiar.setBounds(331, 143, 125, 43);
		contentPane.add(btnLimpiar);

		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setBounds(10, 35, 108, 21);
		contentPane.add(lblCodigo);

		txtcodigo = new JTextField();
		txtcodigo.setColumns(10);
		txtcodigo.setBounds(67, 37, 96, 19);
		contentPane.add(txtcodigo);

		JLabel lblcategoria = new JLabel("Categoria:");
		lblcategoria.setBounds(191, 66, 85, 21);
		contentPane.add(lblcategoria);

		txtcategoria = new JComboBox();
		txtcategoria.setModel(new DefaultComboBoxModel(new String[] {"Bebidas", "Snacks", "Dulces", "Bebidas Alcoholicas"}));
		txtcategoria.setBounds(262, 66, 120, 21);
		contentPane.add(txtcategoria);

		JButton btnModificar = new JButton("✏ Modificar");
		btnModificar.setBounds(181, 143, 125, 43);
		contentPane.add(btnModificar);

		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla para modificar.");
					return;
				}
				String codigo = txtcodigo.getText();
				String nom = txtnom.getText();
				String precio = txtprecio.getText();
				String stock = txtstock.getText();
				String categoria = txtcategoria.getSelectedItem().toString();

				if (codigo.isEmpty() || nom.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete todos los campos antes de modificar.");
					return;
				}
				try {
					int c = Integer.parseInt(codigo);
					double p = Double.parseDouble(precio);
					int s = Integer.parseInt(stock);

					modelo.setValueAt(c, fila, 0);
					modelo.setValueAt(nom, fila, 1);
					modelo.setValueAt(p, fila, 2);
					modelo.setValueAt(s, fila, 3);
					modelo.setValueAt(categoria, fila, 4);

					JOptionPane.showMessageDialog(null, "Producto modificado correctamente.");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El código, precio y stock deben ser números válidos.");
				}
			}
		});

		tabla.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int fila = tabla.getSelectedRow();
				if (fila != -1) {
					txtcodigo.setText(modelo.getValueAt(fila, 0).toString());
					txtnom.setText(modelo.getValueAt(fila, 1).toString());
					txtprecio.setText(modelo.getValueAt(fila, 2).toString());
					txtstock.setText(modelo.getValueAt(fila, 3).toString());
					txtcategoria.setSelectedItem(modelo.getValueAt(fila, 4).toString());
				}
			}
		});
	}
}