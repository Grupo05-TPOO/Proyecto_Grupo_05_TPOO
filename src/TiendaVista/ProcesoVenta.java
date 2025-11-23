package TiendaVista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Métodos.DAO_Producto;
import Métodos.DAO_Venta;
import Métodos.Exportar;
import Modelo.Producto;
import Modelo.Venta;

public class ProcesoVenta extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JComboBox<String> cbCategoria;
    private JComboBox<String> cbProducto;

    private JTextField txtNombre, txtPrecio, txtStock, txtCantidad, txtDescuento;

    private JTable table;
    private DefaultTableModel modeloTabla;

    private JButton btnAgregarVenta;
    private JButton btnFinalizarVenta;
    private JButton btnExportarVentas;

    private DAO_Producto daoP = new DAO_Producto();
    private DAO_Venta daoV = new DAO_Venta();
    private JButton btnReporteSemanal;

    public ProcesoVenta() {

        setTitle("Proceso de Venta");
        setBounds(100, 100, 600, 650);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblTitulo = new JLabel("PROCESO DE VENTA");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitulo.setBounds(180, 10, 250, 30);
        contentPanel.add(lblTitulo);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(25, 60, 90, 20);
        contentPanel.add(lblCategoria);

        cbCategoria = new JComboBox<>();
        cbCategoria.setModel(new DefaultComboBoxModel(new String[] {"Gaseosas", "Bebidas ", "Snacks", "Dulces"}));
        cbCategoria.setBounds(120, 57, 150, 25);

        cbCategoria.addActionListener(this);
        contentPanel.add(cbCategoria);

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(25, 100, 90, 20);
        contentPanel.add(lblProducto);

        cbProducto = new JComboBox<>();
        cbProducto.setBounds(120, 97, 150, 25);
        cbProducto.addActionListener(this);
        contentPanel.add(cbProducto);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(25, 140, 60, 13);
        contentPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(90, 137, 100, 19);
        txtNombre.setEditable(false);
        contentPanel.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(25, 180, 60, 13);
        contentPanel.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(90, 177, 100, 19);
        txtPrecio.setEditable(false);
        contentPanel.add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(25, 220, 60, 13);
        contentPanel.add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(90, 217, 100, 19);
        txtStock.setEditable(false);
        contentPanel.add(txtStock);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(25, 260, 60, 13);
        contentPanel.add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(90, 257, 100, 19);
        contentPanel.add(txtCantidad);

        JLabel lblDescuento = new JLabel("Descuento:");
        lblDescuento.setBounds(25, 300, 80, 13);
        contentPanel.add(lblDescuento);

        txtDescuento = new JTextField();
        txtDescuento.setBounds(90, 297, 100, 19);
        contentPanel.add(txtDescuento);

        btnAgregarVenta = new JButton("Agregar Venta");
        btnAgregarVenta.addActionListener(this);
        btnAgregarVenta.setBounds(210, 257, 150, 30);
        contentPanel.add(btnAgregarVenta);

        btnFinalizarVenta = new JButton("Finalizar Venta");
        btnFinalizarVenta.addActionListener(this);
        btnFinalizarVenta.setBounds(210, 297, 150, 30);
        contentPanel.add(btnFinalizarVenta);

        btnExportarVentas = new JButton("Exportar Ventas");
        btnExportarVentas.addActionListener(this);
        btnExportarVentas.setBounds(380, 257, 150, 30);
        contentPanel.add(btnExportarVentas);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 360, 530, 230);
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
        
        btnReporteSemanal = new JButton("Reporte Semanal");
        btnReporteSemanal.addActionListener(this);
        btnReporteSemanal.setBounds(380, 296, 150, 30);
        contentPanel.add(btnReporteSemanal);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == btnReporteSemanal) {
    		do_btnReporteSemanal_actionPerformed(e);
    	}

        if (e.getSource() == cbCategoria) {
            if (cbCategoria.getSelectedItem() != null) {
                cargarProductosPorCategoria(cbCategoria.getSelectedItem().toString());
            }
        }

        if (e.getSource() == cbProducto) {
            if (cbProducto.getSelectedItem() != null) {
                cargarDatosProducto(cbProducto.getSelectedItem().toString());
            }
        }

        if (e.getSource() == btnAgregarVenta) {
            do_btnAgregarVenta_actionPerformed(e);
        }
        if (e.getSource() == btnFinalizarVenta) {
            do_btnFinalizarVenta_actionPerformed(e);
        }
        if (e.getSource() == btnExportarVentas) {
            do_btnExportarVentas_actionPerformed(e);
        }
    }


    private void cargarProductosPorCategoria(String categoria) {

        cbProducto.removeAllItems();

        for (Producto p : daoP.listarProductos()) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                cbProducto.addItem(p.getNombre());
            }
        }
    }


    private void cargarDatosProducto(String nombreProducto) {

        for (Producto p : daoP.listarProductos()) {

            if (p.getNombre().equalsIgnoreCase(nombreProducto)) {

                txtNombre.setText(p.getNombre());
                txtPrecio.setText(String.valueOf(p.getPrecio()));
                txtStock.setText(String.valueOf(p.getStock()));
                break;
                
            }
        }
    }

    protected void do_btnAgregarVenta_actionPerformed(ActionEvent e) {

        try {

            String nombre = txtNombre.getText();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.");
                return;
            }

            int cant = Integer.parseInt(txtCantidad.getText());
            double desc = Double.parseDouble(txtDescuento.getText());

            if (cant <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                return;
            }

            if (desc < 0 || desc > 100) {
                JOptionPane.showMessageDialog(this, "Descuento inválido");
                return;
            }

            Producto p = null;
            for (Producto x : daoP.listarProductos()) {
                if (x.getNombre().equalsIgnoreCase(nombre)) {
                    p = x;
                    break;
                }
            }

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Producto no existente.");
                return;
            }

            if (cant > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad mayor al stock disponible");
                return;
            }

            Venta v = new Venta(cant, p, desc);

            daoV.insertarVenta(v);
            daoV.actualizarStock(p.getCodigo(), p.getStock() - cant);

            modeloTabla.addRow(new Object[]{
                p.getCodigo(), p.getNombre(), p.getPrecio(), cant, desc, v.calcularSubtotal()
            });

            txtStock.setText(String.valueOf(p.getStock() - cant));

            JOptionPane.showMessageDialog(this, "Venta agregada correctamente");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos");
        }
    }


    protected void do_btnFinalizarVenta_actionPerformed(ActionEvent e) {

    	 if (modeloTabla.getRowCount() == 0) {
    	        JOptionPane.showMessageDialog(this, "No hay productos en la venta.");
    	        return;
    	    }

    	    double total = 0;

    	    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
    	        total += Double.parseDouble(modeloTabla.getValueAt(i, 5).toString());
    	    }

    	    Exportar.generarBoletaTXT(modeloTabla, total);

    	    JOptionPane.showMessageDialog(this, "Venta finalizada.\nTotal vendido: S/ " + total);

    	    modeloTabla.setRowCount(0);
        
    }


    protected void do_btnExportarVentas_actionPerformed(ActionEvent e) {

        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay ventas para exportar");
            return;
        }

        java.util.ArrayList<Venta> lista = new java.util.ArrayList<>();

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Producto p = new Producto(
                Integer.parseInt(modeloTabla.getValueAt(i, 0).toString()),
                modeloTabla.getValueAt(i, 1).toString(),
                Double.parseDouble(modeloTabla.getValueAt(i, 2).toString()),
                0,
                ""
            );

            Venta v = new Venta(
                Integer.parseInt(modeloTabla.getValueAt(i, 3).toString()),
                p,
                Double.parseDouble(modeloTabla.getValueAt(i, 4).toString())
            );

            lista.add(v);
        }

        Exportar.exportarVentas(lista);
    }
	protected void do_btnReporteSemanal_actionPerformed(ActionEvent e) {
		ArrayList<DAO_Venta.VentaSemanal> lista = daoV.listarVentasSemanales();

	    if (lista.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "No hay ventas registradas esta semana.");
	        return;
	    }

	    Exportar.exportarReporteSemanalTXT(lista);
	}
}
