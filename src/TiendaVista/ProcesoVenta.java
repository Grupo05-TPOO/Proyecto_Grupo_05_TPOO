package TiendaVista;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modelo.Producto;
import Modelo.Venta;
import Métodos.M_Producto;
import Métodos.M_Venta;
import Métodos.Exportar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProcesoVenta extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo, txtNombre, txtPrecio, txtStock, txtcat, txtdes;
	private JTable table;
	private DefaultTableModel modeloTabla;
	private JButton btnBuscar, btnAgregarVenta, btnFinalizarVenta, btnExportarVentas;

	private M_Venta ventaManager = new M_Venta();
	private M_Producto productoManager = new M_Producto();

	public static void main(String[] args) {
		try {
			ProcesoVenta dialog = new ProcesoVenta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProcesoVenta() {
		setTitle("Proceso de Venta");
		setBounds(100, 100, 584, 620);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTitulo = new JLabel("PROCESO DE VENTA");
		lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTitulo.setBounds(180, 10, 250, 30);
		contentPanel.add(lblTitulo);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(25, 54, 60, 13);
		contentPanel.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(82, 51, 96, 19);
		contentPanel.add(txtCodigo);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(210, 50, 85, 21);
		contentPanel.add(btnBuscar);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 92, 60, 13);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(82, 89, 96, 19);
		txtNombre.setEditable(false);
		contentPanel.add(txtNombre);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(25, 131, 45, 13);
		contentPanel.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(82, 128, 96, 19);
		txtPrecio.setEditable(false);
		contentPanel.add(txtPrecio);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(25, 168, 45, 13);
		contentPanel.add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(82, 165, 96, 19);
		txtStock.setEditable(false);
		contentPanel.add(txtStock);

		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(25, 202, 70, 13);
		contentPanel.add(lblCantidad);

		txtcat = new JTextField();
		txtcat.setBounds(102, 199, 76, 19);
		contentPanel.add(txtcat);

		JLabel lblDescuento = new JLabel("Descuento:");
		lblDescuento.setBounds(25, 238, 71, 13);
		contentPanel.add(lblDescuento);

		txtdes = new JTextField();
		txtdes.setBounds(102, 235, 76, 19);
		contentPanel.add(txtdes);

		btnAgregarVenta = new JButton("Agregar Venta");
		btnAgregarVenta.addActionListener(this);
		btnAgregarVenta.setBounds(210, 276, 143, 30);
		contentPanel.add(btnAgregarVenta);

		btnExportarVentas = new JButton("Exportar Ventas");
		btnExportarVentas.addActionListener(this);
		btnExportarVentas.setBounds(370, 276, 143, 30);
		contentPanel.add(btnExportarVentas);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 327, 516, 130);
		contentPanel.add(scrollPane);

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Producto");
		modeloTabla.addColumn("Precio");
		modeloTabla.addColumn("Cantidad");
		modeloTabla.addColumn("Descuento");
		modeloTabla.addColumn("Subtotal");

		table = new JTable(modeloTabla);
		scrollPane.setViewportView(table);

		btnFinalizarVenta = new JButton("Finalizar Venta");
		btnFinalizarVenta.addActionListener(this);
		btnFinalizarVenta.setBounds(210, 478, 143, 30);
		contentPanel.add(btnFinalizarVenta);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) buscarProducto();
		else if (e.getSource() == btnAgregarVenta) agregarVenta();
		else if (e.getSource() == btnFinalizarVenta) finalizarVenta();
		else if (e.getSource() == btnExportarVentas) Exportar.exportarVentas(ventaManager.getVentas());
	}

	private void buscarProducto() {
		try {
			int codigo = Integer.parseInt(txtCodigo.getText().trim());
			Producto p = productoManager.buscarProducto(codigo);

			if (p != null) {
				txtNombre.setText(p.getNombre());
				txtPrecio.setText(String.valueOf(p.getPrecio()));
				txtStock.setText(String.valueOf(p.getStock()));
				JOptionPane.showMessageDialog(this, "Producto encontrado y cargado correctamente.");
			} else {
				JOptionPane.showMessageDialog(this, "No se encontró el producto.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void agregarVenta() {
		try {
			int codigo = Integer.parseInt(txtCodigo.getText().trim());
			int cantidad = Integer.parseInt(txtcat.getText().trim());
			double descuento = Double.parseDouble(txtdes.getText().trim());

			Producto p = productoManager.buscarProducto(codigo);
			if (p == null) {
				JOptionPane.showMessageDialog(this, "Busque primero un producto válido.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (cantidad > p.getStock()) {
				JOptionPane.showMessageDialog(this, "Cantidad mayor al stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Venta nuevaVenta = new Venta(cantidad, p, descuento);
			ventaManager.agregarVenta(nuevaVenta);

			double subtotal = nuevaVenta.calcularSubtotal();
			modeloTabla.addRow(new Object[]{
					p.getCodigo(), p.getNombre(), p.getPrecio(), cantidad, descuento, subtotal
			});

			p.setStock(p.getStock() - cantidad);
			txtStock.setText(String.valueOf(p.getStock()));

			JOptionPane.showMessageDialog(this, "Venta agregada correctamente.");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Ingrese valores válidos para cantidad y descuento.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void finalizarVenta() {
		double total = ventaManager.calculadoraSubtotal();
		JOptionPane.showMessageDialog(this, "Venta finalizada. Total: S/ " + total);
		ventaManager.limpiarVenta();
		modeloTabla.setRowCount(0);
	}
}
