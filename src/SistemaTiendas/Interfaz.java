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
    private JTextField txtCantidadVenta;
    private JTextField txtDescuento;
    private JComboBox<String> txtcategoria;
    private JTable tabla;
    private JTable tablaVentas;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloVentas;

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
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblRegistroDeProducto = new JLabel("SISTEMA DE GESTIÓN DE TIENDA");
        lblRegistroDeProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblRegistroDeProducto.setBounds(280, 10, 250, 31);
        contentPane.add(lblRegistroDeProducto);

       
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
                guardarProducto();
            }
        });
        btnNewButton.setBounds(10, 143, 144, 43);
        contentPane.add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 214, 474, 146);
        contentPane.add(scrollPane);

        modelo = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Código", "Nombre", "Precio", "Stock", "Categoría"}
        );
        tabla = new JTable(modelo);
        scrollPane.setViewportView(tabla);

        JButton btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
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

        txtcategoria = new JComboBox<String>();
        txtcategoria.setModel(new DefaultComboBoxModel<String>(new String[] {"Bebidas", "Snacks", "Dulces", "Bebidas Alcoholicas"}));
        txtcategoria.setBounds(262, 66, 120, 21);
        contentPane.add(txtcategoria);

        JButton btnModificar = new JButton("✏️ Modificar");
        btnModificar.setBounds(181, 143, 125, 43);
        contentPane.add(btnModificar);

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setBounds(150, 190, 120, 25);
        contentPane.add(btnBuscar);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = txtcodigo.getText().trim();

                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un código para buscar.");
                    return;
                }

                boolean encontrado = false;
                DefaultTableModel modeloFiltrado = new DefaultTableModel(
                    new Object[][] {},
                    new String[] {"Código", "Nombre", "Precio", "Stock", "Categoría"}
                );

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String codTabla = modelo.getValueAt(i, 0).toString();

                    if (codTabla.equals(codigo)) {
                        modeloFiltrado.addRow(new Object[] {
                            modelo.getValueAt(i, 0),
                            modelo.getValueAt(i, 1),
                            modelo.getValueAt(i, 2),
                            modelo.getValueAt(i, 3),
                            modelo.getValueAt(i, 4)
                        });
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    tabla.setModel(modeloFiltrado); 
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
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

        
        JLabel lblVentas = new JLabel("PROCESO DE VENTAS");
        lblVentas.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblVentas.setBounds(500, 40, 200, 21);
        contentPane.add(lblVentas);

        JLabel lblSeleccionarProducto = new JLabel("Seleccione producto:");
        lblSeleccionarProducto.setBounds(500, 70, 150, 21);
        contentPane.add(lblSeleccionarProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(500, 100, 70, 21);
        contentPane.add(lblCantidad);

        txtCantidadVenta = new JTextField();
        txtCantidadVenta.setBounds(560, 98, 70, 19);
        contentPane.add(txtCantidadVenta);
        txtCantidadVenta.setColumns(10);

        JLabel lblDescuento = new JLabel("Descuento (%):");
        lblDescuento.setBounds(500, 130, 100, 21);
        contentPane.add(lblDescuento);

        txtDescuento = new JTextField();
        txtDescuento.setBounds(597, 131, 70, 19);
        contentPane.add(txtDescuento);
        txtDescuento.setColumns(10);

        JButton btnAgregarVenta = new JButton("🛒 Agregar a Venta");
        btnAgregarVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarVenta();
            }
        });
        btnAgregarVenta.setBounds(500, 160, 150, 30);
        contentPane.add(btnAgregarVenta);

        JButton btnFinalizarVenta = new JButton("💰 Finalizar Venta");
        btnFinalizarVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finalizarVenta();
            }
        });
        btnFinalizarVenta.setBounds(660, 160, 120, 30);
        contentPane.add(btnFinalizarVenta);

        JScrollPane scrollPaneVentas = new JScrollPane();
        scrollPaneVentas.setBounds(500, 200, 280, 160);
        contentPane.add(scrollPaneVentas);

        modeloVentas = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Producto", "Cantidad", "Precio", "Descuento", "Subtotal"}
        );
        tablaVentas = new JTable(modeloVentas);
        scrollPaneVentas.setViewportView(tablaVentas);

        JLabel lblTotalVenta = new JLabel("Total Venta: $0.00");
        lblTotalVenta.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
        lblTotalVenta.setBounds(500, 370, 200, 21);
        contentPane.add(lblTotalVenta);
    }
    
    private void guardarProducto() {
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

    	            
    	            if(p <= 0) {
    	                JOptionPane.showMessageDialog(null, "El precio debe ser mayor a 0.");
    	                return;
    	            }
    	            if(s < 0) {
    	                JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.");
    	                return;
    	            }

    	            modelo.addRow(new Object[] {c, nom, p, s, categoria});
    	            limpiarCampos();
    	            JOptionPane.showMessageDialog(null, "Producto guardado correctamente!");

    	        } catch(NumberFormatException ex) {
    	            JOptionPane.showMessageDialog(null, "El código, precio y stock deben ser números válidos");
    	        }
    	    }
    }
    
    private void modificarProducto() {
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
    
    private void limpiarCampos() {
        txtcodigo.setText("");
        txtnom.setText("");
        txtprecio.setText("");
        txtstock.setText("");
    }
    
    private void agregarVenta() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla para vender.");
            return;
        }
        
        String cantidadStr = txtCantidadVenta.getText();
        String descuentoStr = txtDescuento.getText();
        
        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese la cantidad a vender.");
            return;
        }
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            int stockDisponible = Integer.parseInt(modelo.getValueAt(fila, 3).toString());
            
            if (cantidad > stockDisponible) {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock. Stock disponible: " + stockDisponible);
                return;
            }
            
            double descuento = descuentoStr.isEmpty() ? 0 : Double.parseDouble(descuentoStr);
            if (descuento < 0 || descuento > 100) {
                JOptionPane.showMessageDialog(null, "El descuento debe estar entre 0 y 100%.");
                return;
            }
            
            String producto = modelo.getValueAt(fila, 1).toString();
            double precio = Double.parseDouble(modelo.getValueAt(fila, 2).toString());
            double subtotal = precio * cantidad;
            double montoDescuento = subtotal * (descuento / 100);
            subtotal -= montoDescuento;
            
            
            int nuevoStock = stockDisponible - cantidad;
            modelo.setValueAt(nuevoStock, fila, 3);
            
            
            modeloVentas.addRow(new Object[] {
                producto, 
                cantidad, 
                String.format("$%.2f", precio), 
                String.format("%.0f%%", descuento), 
                String.format("$%.2f", subtotal)
            });
            
           
            txtCantidadVenta.setText("");
            txtDescuento.setText("");
            
            
            actualizarTotalVenta();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "La cantidad y el descuento deben ser números válidos.");
        }
    }
    
    private void finalizarVenta() {
        if (modeloVentas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta.");
            return;
        }
        
        double total = 0;
        for (int i = 0; i < modeloVentas.getRowCount(); i++) {
            String subtotalStr = modeloVentas.getValueAt(i, 4).toString().replace("$", "");
            total += Double.parseDouble(subtotalStr);
        }
        
        JOptionPane.showMessageDialog(null, "Venta finalizada. Total: $" + String.format("%.2f", total));
        modeloVentas.setRowCount(0);
        actualizarTotalVenta();
    }
    
    private void actualizarTotalVenta() {
        double total = 0;
        for (int i = 0; i < modeloVentas.getRowCount(); i++) {
            String subtotalStr = modeloVentas.getValueAt(i, 4).toString().replace("$", "");
            total += Double.parseDouble(subtotalStr);
        }
        
       
        for (java.awt.Component comp : contentPane.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Total Venta:")) {
                    label.setText("Total Venta: $" + String.format("%.2f", total));
                    break;
                }
            }
        }
    }
}
