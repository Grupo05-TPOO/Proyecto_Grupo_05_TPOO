package TiendaVista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Métodos.DAO_Producto;
import Métodos.Exportar;
import Modelo.Producto;

public class RegistroProducto extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtcod;
    private JTextField txtnom;
    private JTextField txtprecio;
    private JTextField txtstock;
    private JTable table;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> txtcat;

    private DAO_Producto dao = new DAO_Producto();

    public static void main(String[] args) {
        try {
            RegistroProducto dialog = new RegistroProducto();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegistroProducto() {
        setTitle("Registro de Producto");
        setBounds(100, 100, 620, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblTitulo = new JLabel("REGISTRO DE PRODUCTO");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTitulo.setBounds(170, 10, 260, 36);
        contentPanel.add(lblTitulo);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(26, 76, 60, 13);
        contentPanel.add(lblCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(26, 114, 60, 13);
        contentPanel.add(lblNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(26, 153, 60, 13);
        contentPanel.add(lblPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(228, 76, 60, 13);
        contentPanel.add(lblStock);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(228, 114, 70, 13);
        contentPanel.add(lblCategoria);

        txtcod = new JTextField();
        txtcod.setBounds(83, 73, 96, 19);
        contentPanel.add(txtcod);

        txtnom = new JTextField();
        txtnom.setBounds(83, 111, 96, 19);
        contentPanel.add(txtnom);

        txtprecio = new JTextField();
        txtprecio.setBounds(83, 150, 96, 19);
        contentPanel.add(txtprecio);

        txtstock = new JTextField();
        txtstock.setBounds(286, 73, 96, 19);
        contentPanel.add(txtstock);

        txtcat = new JComboBox<>();
        txtcat.setModel(new DefaultComboBoxModel<>(new String[]{"Gaseosas", "Bebidas", "Snacks"}));
        txtcat.setBounds(296, 110, 86, 21);
        contentPanel.add(txtcat);

        JButton btnGuardarProducto = new JButton("Guardar Producto");
        btnGuardarProducto.addActionListener(this);
        btnGuardarProducto.setBounds(26, 191, 153, 29);
        contentPanel.add(btnGuardarProducto);

        JButton btnBuscarProducto = new JButton("Buscar Producto");
        btnBuscarProducto.addActionListener(this);
        btnBuscarProducto.setBounds(216, 191, 153, 29);
        contentPanel.add(btnBuscarProducto);

        JButton btnModificarProducto = new JButton("Modificar Producto");
        btnModificarProducto.addActionListener(this);
        btnModificarProducto.setBounds(26, 235, 153, 29);
        contentPanel.add(btnModificarProducto);

        JButton btnEliminarProducto = new JButton("Eliminar Producto");
        btnEliminarProducto.addActionListener(this);
        btnEliminarProducto.setBounds(216, 235, 153, 29);
        contentPanel.add(btnEliminarProducto);

        JButton btnExportarProducto = new JButton("Exportar Producto");
        btnExportarProducto.addActionListener(this);
        btnExportarProducto.setBounds(415, 72, 153, 29);
        contentPanel.add(btnExportarProducto);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(37, 284, 530, 140);
        contentPanel.add(scrollPane);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Categoría");

        table = new JTable(modeloTabla);
        scrollPane.setViewportView(table);

        imprimir();
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();

        switch (cmd) {
            case "Guardar Producto":
                guardarProducto();
                break;
            case "Buscar Producto":
                buscarProducto();
                break;
            case "Modificar Producto":
                modificarProducto();
                break;
            case "Eliminar Producto":
                eliminarProducto();
                break;
            case "Exportar Producto":
                exportarProducto();
                break;
        }
    }

    // ================= VALIDACIONES =================

    private boolean existeNombre(String nombre) {
        for (Producto p : dao.listarProductos()) {
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    // ================= ACCIONES =================

    private void guardarProducto() {

        // Validar campos
        if (txtcod.getText().isEmpty() || txtnom.getText().isEmpty() ||
            txtprecio.getText().isEmpty() || txtstock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        // Validar negativos
        if (leerprecio() < 0) {
            JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.");
            return;
        }

        if (leerstock() < 0) {
            JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.");
            return;
        }

        // validar nombre repetido
        if (existeNombre(leernombre())) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con este nombre.");
            return;
        }

        // Validar código repetido
        Producto pro = dao.buscarProducto(leercod());
        if (pro == null) {

            Producto nuevo = new Producto(leercod(), leernombre(), leerprecio(), leerstock(), leercategoria());
            dao.insertarProducto(nuevo);
            imprimir();
            limpiarcampos();
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente");

        } else {
            JOptionPane.showMessageDialog(this, "El producto con este código ya existe");
        }
    }

    private void buscarProducto() {
        Producto pro = dao.buscarProducto(leercod());
        if (pro != null) {
            txtnom.setText(pro.getNombre());
            txtprecio.setText(String.valueOf(pro.getPrecio()));
            txtstock.setText(String.valueOf(pro.getStock()));
            txtcat.setSelectedItem(pro.getCategoria());
            JOptionPane.showMessageDialog(this, "Producto encontrado");
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
        }
    }

    private void modificarProducto() {

        if (leerprecio() < 0) {
            JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.");
            return;
        }

        if (leerstock() < 0) {
            JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.");
            return;
        }

        Producto pro = dao.buscarProducto(leercod());
        if (pro != null) {

            if(!pro.getNombre().equalsIgnoreCase(leernombre()) && existeNombre(leernombre())){
                JOptionPane.showMessageDialog(this, "Ya existe otro producto con este nombre.");
                return;
            }

            pro.setNombre(leernombre());
            pro.setPrecio(leerprecio());
            pro.setStock(leerstock());
            pro.setCategoria(leercategoria());
            dao.modificarProducto(pro);

            imprimir();
            limpiarcampos();
            JOptionPane.showMessageDialog(this, "Producto modificado correctamente");

        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
        }
    }

    private void eliminarProducto() {
        Producto pro = dao.buscarProducto(leercod());
        if (pro != null) {
            dao.eliminarProducto(leercod());
            imprimir();
            limpiarcampos();
            JOptionPane.showMessageDialog(this, "Producto eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
        }
    }

    private void exportarProducto() {
        Exportar.exportarProductos(dao.listarProductos());
    }

    // ================= LECTURAS =================

    int leercod() {
        return Integer.parseInt(txtcod.getText());
    }

    int leerstock() {
        return Integer.parseInt(txtstock.getText());
    }

    double leerprecio() {
        return Double.parseDouble(txtprecio.getText());
    }

    String leernombre() {
        return txtnom.getText();
    }

    String leercategoria() {
        return txtcat.getSelectedItem().toString();
    }

    void imprimir() {
        modeloTabla.setRowCount(0);
        for (Producto x : dao.listarProductos()) {
            modeloTabla.addRow(new Object[]{
                    x.getCodigo(), x.getNombre(), x.getPrecio(), x.getStock(), x.getCategoria()
            });
        }
    }

    void limpiarcampos() {
        txtcod.setText("");
        txtnom.setText("");
        txtprecio.setText("");
        txtstock.setText("");
        txtcat.setSelectedIndex(0);
    }
}
