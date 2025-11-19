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


    private DAO_Producto daoP = new DAO_Producto();
    private DAO_Venta daoV = new DAO_Venta();

    public ProcesoVenta() {
        setTitle("Proceso de Venta");
        setBounds(100, 100, 600, 650);
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

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(210, 56, 90, 22);
        btnBuscar.addActionListener(this);
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

        JButton btnAgregar = new JButton("Agregar Venta");
        btnAgregar.setBounds(210, 217, 150, 30);
        btnAgregar.addActionListener(this);
        contentPanel.add(btnAgregar);

        JButton btnFinalizar = new JButton("Finalizar Venta");
        btnFinalizar.setBounds(210, 257, 150, 30);
        btnFinalizar.addActionListener(this);
        contentPanel.add(btnFinalizar);

        JButton btnExportar = new JButton("Exportar Ventas");
        btnExportar.setBounds(380, 217, 150, 30);
        btnExportar.addActionListener(this);
        contentPanel.add(btnExportar);

        // TABLA
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
        String cmd = ((JButton)e.getSource()).getText();

        switch (cmd) {
            case "Buscar": buscarProducto(); break;
            case "Agregar Venta": agregarVenta(); break;
            case "Finalizar Venta": finalizarVenta(); break;
            case "Exportar Ventas": exportarVentas(); break;
        }
    }

    private void buscarProducto() {
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

    private void agregarVenta() {
        try {
            int cod = Integer.parseInt(txtCodigo.getText());
            int cant = Integer.parseInt(txtCantidad.getText());
            double desc = Double.parseDouble(txtDescuento.getText());

            // VALIDACIONES NUEVAS
            if (cant <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                return;
            }

            if (desc < 0) {
                JOptionPane.showMessageDialog(this, "El descuento no puede ser negativo");
                return;
            }

            if (desc > 100) {
                JOptionPane.showMessageDialog(this, "El descuento no puede ser mayor a 100%");
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

    private void finalizarVenta() {
        double total = 0;

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            total += Double.parseDouble(modeloTabla.getValueAt(i, 5).toString());
        }

        JOptionPane.showMessageDialog(this, "Venta finalizada.\nTotal vendido: S/ " + total);

        modeloTabla.setRowCount(0);
    }

    private void exportarVentas() {
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

