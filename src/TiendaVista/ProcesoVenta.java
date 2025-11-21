package TiendaVista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock, txtCantidad, txtDescuento;
    private JTable table;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar;
    private JButton btnAgregarVenta;
    private JButton btnFinalizarVenta;
    private JButton btnExportarVentas;
    private DAO_Producto daoP = new DAO_Producto();
    private DAO_Venta daoV = new DAO_Venta();

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

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(25, 60, 60, 13);
        contentPanel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(90, 57, 100, 19);
        contentPanel.add(txtCodigo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        btnBuscar.setBounds(210, 56, 90, 22);
        contentPanel.add(btnBuscar);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(25, 100, 60, 13);
        contentPanel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(90, 97, 100, 19);
        txtNombre.setEditable(false);
        contentPanel.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(25, 140, 60, 13);
        contentPanel.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(90, 137, 100, 19);
        txtPrecio.setEditable(false);
        contentPanel.add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(25, 180, 60, 13);
        contentPanel.add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(90, 177, 100, 19);
        txtStock.setEditable(false);
        contentPanel.add(txtStock);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(25, 220, 60, 13);
        contentPanel.add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(90, 217, 100, 19);
        contentPanel.add(txtCantidad);

        JLabel lblDescuento = new JLabel("Descuento:");
        lblDescuento.setBounds(25, 260, 80, 13);
        contentPanel.add(lblDescuento);

        txtDescuento = new JTextField();
        txtDescuento.setBounds(90, 257, 100, 19);
        contentPanel.add(txtDescuento);

        btnAgregarVenta = new JButton("Agregar Venta");
        btnAgregarVenta.addActionListener(this);
        btnAgregarVenta.setBounds(210, 217, 150, 30);
        contentPanel.add(btnAgregarVenta);

        btnFinalizarVenta = new JButton("Finalizar Venta");
        btnFinalizarVenta.addActionListener(this);
        btnFinalizarVenta.setBounds(210, 257, 150, 30);
        contentPanel.add(btnFinalizarVenta);

        btnExportarVentas = new JButton("Exportar Ventas");
        btnExportarVentas.addActionListener(this);
        btnExportarVentas.setBounds(380, 217, 150, 30);
        contentPanel.add(btnExportarVentas);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 330, 530, 230);
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
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBuscar) {
            do_btnBuscar_actionPerformed(e);
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
    protected void do_btnBuscar_actionPerformed(ActionEvent e) {

        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            Producto p = daoP.buscarProducto(codigo);

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
                return;
            }

            txtNombre.setText(p.getNombre());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtStock.setText(String.valueOf(p.getStock()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Código inválido");
        }
    }

    protected void do_btnAgregarVenta_actionPerformed(ActionEvent e) {

        try {
            int cod = Integer.parseInt(txtCodigo.getText());
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

            Producto p = daoP.buscarProducto(cod);

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Busque primero un producto válido");
                return;
            }

            if (cant > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad mayor al stock disponible");
                return;
            }

            Venta v = new Venta(cant, p, desc);

            daoV.insertarVenta(v);
            daoV.actualizarStock(cod, p.getStock() - cant);

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

        double total = 0;

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            total += Double.parseDouble(modeloTabla.getValueAt(i, 5).toString());
        }

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

}

