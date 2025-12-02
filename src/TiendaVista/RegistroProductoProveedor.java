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
import Métodos.DAO_Proveedor;
import Métodos.DAO_ProductoProveedor;

import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.ProductoProveedor;

public class RegistroProductoProveedor extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private JComboBox<String> cbProducto;
    private JComboBox<String> cbProveedor;

    private JTextField txtCantidad;

    private JTable table;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar, btnListarTodo, btnFiltrar;

    private DAO_Producto daoProd = new DAO_Producto();
    private DAO_Proveedor daoProv = new DAO_Proveedor();
    private DAO_ProductoProveedor daoPP = new DAO_ProductoProveedor();

    public RegistroProductoProveedor() {

        setTitle("Relación Producto - Proveedor");
        setBounds(100, 100, 750, 550);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblTitulo = new JLabel("REGISTRO PRODUCTO - PROVEEDOR");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitulo.setBounds(180, 10, 400, 30);
        contentPanel.add(lblTitulo);

        JLabel lblProduto = new JLabel("Producto:");
        lblProduto.setBounds(30, 70, 100, 20);
        contentPanel.add(lblProduto);

        cbProducto = new JComboBox<>();
        cbProducto.setBounds(120, 70, 200, 25);
        contentPanel.add(cbProducto);

        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setBounds(360, 70, 100, 20);
        contentPanel.add(lblProveedor);

        cbProveedor = new JComboBox<>();
        cbProveedor.setBounds(450, 70, 220, 25);
        contentPanel.add(cbProveedor);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(30, 120, 100, 20);
        contentPanel.add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(120, 120, 100, 20);
        contentPanel.add(txtCantidad);

        btnRegistrar = new JButton("Registrar Relación");
        btnRegistrar.setBounds(260, 120, 160, 30);
        btnRegistrar.addActionListener(this);
        contentPanel.add(btnRegistrar);

        btnFiltrar = new JButton("Ver proveedores del producto");
        btnFiltrar.setBounds(440, 120, 230, 30);
        btnFiltrar.addActionListener(this);
        contentPanel.add(btnFiltrar);

        btnListarTodo = new JButton("Listar Todo");
        btnListarTodo.setBounds(580, 160, 120, 30);
        btnListarTodo.addActionListener(this);
        contentPanel.add(btnListarTodo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 220, 680, 250);
        contentPanel.add(scrollPane);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código Producto");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Cod Proveedor");
        modeloTabla.addColumn("Razón Social");
        modeloTabla.addColumn("Cantidad");

        table = new JTable(modeloTabla);
        scrollPane.setViewportView(table);

        cargarCombos();
        listarTodo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnRegistrar) {
            do_btnRegistrar_actionPerformed(e);
        }

        if (e.getSource() == btnListarTodo) {
            do_btnListarTodo_actionPerformed(e);
        }

        if (e.getSource() == btnFiltrar) {
            do_btnFiltrar_actionPerformed(e);
        }
    }

    protected void do_btnRegistrar_actionPerformed(ActionEvent e) {

        try {

            String nomProd = cbProducto.getSelectedItem().toString();
            String nomProv = cbProveedor.getSelectedItem().toString();

            if (txtCantidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese cantidad.");
                return;
            }

            int cantidad = Integer.parseInt(txtCantidad.getText());

            Producto prod = null;
            for (Producto p : daoProd.listarProductos()) {
                if (p.getNombre().equalsIgnoreCase(nomProd)) {
                    prod = p;
                    break;
                }
            }

            Proveedor prov = null;
            for (Proveedor pr : daoProv.listar()) {
                if (pr.getRazonSocial().equalsIgnoreCase(nomProv)) {
                    prov = pr;
                    break;
                }
            }

            if (prod == null || prov == null) {
                JOptionPane.showMessageDialog(this, "Error cargando datos.");
                return;
            }

            ProductoProveedor pp = new ProductoProveedor(prod, prov, cantidad);
            daoPP.insertar(pp);

            listarTodo();
            txtCantidad.setText("");

            JOptionPane.showMessageDialog(this, "Relación registrada correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    protected void do_btnListarTodo_actionPerformed(ActionEvent e) {
        listarTodo();
    }

    protected void do_btnFiltrar_actionPerformed(ActionEvent e) {

        try {

            String nomProd = cbProducto.getSelectedItem().toString();
            Producto prod = null;

            for (Producto p : daoProd.listarProductos()) {
                if (p.getNombre().equalsIgnoreCase(nomProd)) {
                    prod = p;
                    break;
                }
            }

            if (prod == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
                return;
            }

            modeloTabla.setRowCount(0);

            ArrayList<ProductoProveedor> lista = daoPP.listarProveedoresDeProducto(prod.getCodigo());

            for (ProductoProveedor pp : lista) {
                modeloTabla.addRow(new Object[]{
                        pp.getProducto().getCodigo(),
                        pp.getProducto().getNombre(),
                        pp.getProveedor().getCodProveedor(),
                        pp.getProveedor().getRazonSocial(),
                        pp.getCantidad()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    void cargarCombos() {

        cbProducto.removeAllItems();
        for (Producto p : daoProd.listarProductos()) {
            cbProducto.addItem(p.getNombre());
        }

        cbProveedor.removeAllItems();
        for (Proveedor p : daoProv.listar()) {
            cbProveedor.addItem(p.getRazonSocial());
        }
    }

    void listarTodo() {

        modeloTabla.setRowCount(0);

        for (ProductoProveedor pp : daoPP.listarTodo()) {
            modeloTabla.addRow(new Object[]{
                    pp.getProducto().getCodigo(),
                    pp.getProducto().getNombre(),
                    pp.getProveedor().getCodProveedor(),
                    pp.getProveedor().getRazonSocial(),
                    pp.getCantidad()
            });
        }
    }
}

