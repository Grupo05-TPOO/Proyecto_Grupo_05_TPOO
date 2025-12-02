package TiendaVista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Métodos.DAO_Proveedor;
import Modelo.Proveedor;

public class RegistroProveedor extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private JTextField txtCodigo, txtRuc, txtRazon, txtTelefono, txtCorreo;

    private JTable table;
    private DefaultTableModel modeloTabla;

    private JButton btnGuardar, btnBuscar, btnModificar, btnEliminar, btnListar;

    private DAO_Proveedor dao = new DAO_Proveedor();

    public RegistroProveedor() {
        setTitle("Registro de Proveedor");
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblTitulo = new JLabel("REGISTRO DE PROVEEDOR");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTitulo.setBounds(210, 10, 300, 30);
        contentPanel.add(lblTitulo);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 70, 100, 20);
        contentPanel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(130, 70, 120, 20);
        contentPanel.add(txtCodigo);

        JLabel lblRuc = new JLabel("RUC:");
        lblRuc.setBounds(30, 110, 100, 20);
        contentPanel.add(lblRuc);

        txtRuc = new JTextField();
        txtRuc.setBounds(130, 110, 120, 20);
        contentPanel.add(txtRuc);

        JLabel lblRazon = new JLabel("Razón Social:");
        lblRazon.setBounds(30, 150, 100, 20);
        contentPanel.add(lblRazon);

        txtRazon = new JTextField();
        txtRazon.setBounds(130, 150, 250, 20);
        contentPanel.add(txtRazon);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(360, 70, 100, 20);
        contentPanel.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(450, 70, 150, 20);
        contentPanel.add(txtTelefono);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(360, 110, 100, 20);
        contentPanel.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(450, 110, 150, 20);
        contentPanel.add(txtCorreo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(30, 200, 120, 30);
        btnGuardar.addActionListener(this);
        contentPanel.add(btnGuardar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(170, 200, 120, 30);
        btnBuscar.addActionListener(this);
        contentPanel.add(btnBuscar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(310, 200, 120, 30);
        btnModificar.addActionListener(this);
        contentPanel.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(450, 200, 120, 30);
        btnEliminar.addActionListener(this);
        contentPanel.add(btnEliminar);

        btnListar = new JButton("Listar");
        btnListar.setBounds(590, 200, 80, 30);
        btnListar.addActionListener(this);
        contentPanel.add(btnListar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 260, 640, 180);
        contentPanel.add(scrollPane);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("RUC");
        modeloTabla.addColumn("Razón Social");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Correo");

        table = new JTable(modeloTabla);
        scrollPane.setViewportView(table);

        listarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnGuardar) {
            do_btnGuardarProveedor_actionPerformed(e);
        }

        if (e.getSource() == btnBuscar) {
            do_btnBuscarProveedor_actionPerformed(e);
        }

        if (e.getSource() == btnModificar) {
            do_btnModificarProveedor_actionPerformed(e);
        }

        if (e.getSource() == btnEliminar) {
            do_btnEliminarProveedor_actionPerformed(e);
        }

        if (e.getSource() == btnListar) {
            do_btnListarProveedor_actionPerformed(e);
        }
    }

    protected void do_btnGuardarProveedor_actionPerformed(ActionEvent e) {

        if (txtCodigo.getText().isEmpty() || txtRuc.getText().isEmpty() ||
            txtRazon.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
            txtCorreo.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        if (dao.buscar(txtCodigo.getText()) != null) {
            JOptionPane.showMessageDialog(this, "El proveedor con este código ya existe.");
            return;
        }

        Proveedor p = new Proveedor(
                txtCodigo.getText(),
                txtRuc.getText(),
                txtRazon.getText(),
                txtTelefono.getText(),
                txtCorreo.getText()
        );

        dao.insertar(p);
        listarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente.");
    }

    protected void do_btnBuscarProveedor_actionPerformed(ActionEvent e) {

        Proveedor p = dao.buscar(txtCodigo.getText());

        if (p != null) {
            txtRuc.setText(p.getRuc());
            txtRazon.setText(p.getRazonSocial());
            txtTelefono.setText(p.getTelefono());
            txtCorreo.setText(p.getCorreo());
            imprimirProveedor(p);
            JOptionPane.showMessageDialog(this, "Proveedor encontrado");
        } else {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
        }
    }

    protected void do_btnModificarProveedor_actionPerformed(ActionEvent e) {

        Proveedor p = dao.buscar(txtCodigo.getText());

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
            return;
        }

        p.setRuc(txtRuc.getText());
        p.setRazonSocial(txtRazon.getText());
        p.setTelefono(txtTelefono.getText());
        p.setCorreo(txtCorreo.getText());

        dao.modificar(p);
        listarTabla();
        limpiarCampos();

        JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente");
    }

    protected void do_btnEliminarProveedor_actionPerformed(ActionEvent e) {

        Proveedor p = dao.buscar(txtCodigo.getText());

        if (p != null) {
            dao.eliminar(txtCodigo.getText());
            listarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Proveedor eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
        }
    }

    protected void do_btnListarProveedor_actionPerformed(ActionEvent e) {
        listarTabla();
    }

    void listarTabla() {
        modeloTabla.setRowCount(0);

        for (Proveedor p : dao.listar()) {
            modeloTabla.addRow(new Object[]{
                p.getCodProveedor(),
                p.getRuc(),
                p.getRazonSocial(),
                p.getTelefono(),
                p.getCorreo()
            });
        }
    }

    void imprimirProveedor(Proveedor p) {
        modeloTabla.setRowCount(0);	

        modeloTabla.addRow(new Object[]{
            p.getCodProveedor(),
            p.getRuc(),
            p.getRazonSocial(),
            p.getTelefono(),
            p.getCorreo()
        });
    }

    void limpiarCampos() {
        txtCodigo.setText("");
        txtRuc.setText("");
        txtRazon.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }
}

