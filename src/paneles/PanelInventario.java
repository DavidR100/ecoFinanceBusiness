/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import ui.UITheme;
import javax.swing.JOptionPane;
 
/**
 * @author omar.rebolledo
 */
public class PanelInventario extends javax.swing.JPanel {
 
    // === DATOS ===
    private String[] nombres     = new String[10];
    private double[] disponibles = new double[10];
    private double[] usadas      = new double[10];
    private int numIngredientes  = 0;
 
    // === COMPONENTES FORMULARIO ===
    private JTextField txtNombre, txtDisponible, txtUsada;
    private JLabel lblContador;
 
    // === TABLA ===
    private DefaultTableModel tableModel;
    private JTable tabla;
 
    public PanelInventario() {
        initComponents();
        setBackground(UITheme.BG);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(24, 28, 24, 28));
        build();
    }
 
    private void build() {
        add(buildTitle(),   BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }
 
    // ============================================================
    // TÍTULO
    // ============================================================
    private JPanel buildTitle() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 20, 0));
 
        JLabel title = new JLabel("Inventario");
        title.setFont(UITheme.FONT_EMOJI_BOLD_NORMAL);
        title.setForeground(UITheme.INK);
 
        lblContador = new JLabel("0 ingredientes registrados");
        lblContador.setFont(UITheme.FONT_SMALL);
        lblContador.setForeground(UITheme.MUTED);
 
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(lblContador);
        top.add(titleBox, BorderLayout.WEST);
 
        return top;
    }
 
    // ============================================================
    // CONTENIDO PRINCIPAL
    // ============================================================
    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setOpaque(false);
 
        content.add(buildFormulario(), BorderLayout.WEST);
        content.add(buildTabla(),      BorderLayout.CENTER);
 
        return content;
    }
 
    // ============================================================
    // FORMULARIO IZQUIERDO
    // ============================================================
    private JPanel buildFormulario() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(UITheme.CARD);
    panel.setBorder(new CompoundBorder(
        new LineBorder(UITheme.LINE, 1, true),
        new EmptyBorder(20, 20, 20, 20)
    ));
    panel.setPreferredSize(new Dimension(380, 0));

    JLabel lblTitulo = new JLabel("Agregar ingrediente");
    lblTitulo.setFont(UITheme.FONT_BOLD);
    lblTitulo.setForeground(UITheme.INK);
    lblTitulo.setAlignmentX(LEFT_ALIGNMENT);
    lblTitulo.setBorder(new EmptyBorder(0, 0, 16, 0));

    JLabel lblNombre = new JLabel("Nombre");
    lblNombre.setFont(UITheme.FONT_SMALL);
    lblNombre.setForeground(UITheme.MUTED);
    lblNombre.setAlignmentX(LEFT_ALIGNMENT);

    txtNombre = new JTextField();
    txtNombre.setFont(UITheme.FONT_BODY);
    txtNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
    txtNombre.setAlignmentX(LEFT_ALIGNMENT);
    txtNombre.setBorder(new CompoundBorder(
        new LineBorder(UITheme.VERDE_CLARO, 1, true),
        new EmptyBorder(6, 10, 6, 10)
    ));
    txtNombre.setBackground(UITheme.BG_SUCCESS);

    // === SOLO CANTIDAD DISPONIBLE ===
    JLabel lblDisp = new JLabel("Cantidad disponible");
    lblDisp.setFont(UITheme.FONT_SMALL);
    lblDisp.setForeground(UITheme.MUTED);
    lblDisp.setAlignmentX(LEFT_ALIGNMENT);

    txtDisponible = new JTextField("0.00");
    txtDisponible.setFont(UITheme.FONT_BODY);
    txtDisponible.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
    txtDisponible.setAlignmentX(LEFT_ALIGNMENT);
    txtDisponible.setBorder(new CompoundBorder(
        new LineBorder(UITheme.VERDE_CLARO, 1, true),
        new EmptyBorder(6, 10, 6, 10)
    ));
    txtDisponible.setBackground(UITheme.BG_SUCCESS);

    // === BOTONES VERTICALES Y ANCHOS ===
    JButton btnAgregar = new JButton("+ Agregar ingrediente");
    btnAgregar.setFont(UITheme.FONT_BOLD);
    btnAgregar.setBackground(UITheme.VERDE_PRINCIPAL);
    btnAgregar.setForeground(Color.WHITE);
    btnAgregar.setFocusPainted(false);
    btnAgregar.setBorderPainted(false);
    btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    btnAgregar.setAlignmentX(LEFT_ALIGNMENT);
    btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btnAgregar.addMouseListener(new MouseAdapter() {
        @Override public void mouseEntered(MouseEvent e) { btnAgregar.setBackground(UITheme.VERDE_MEDIO); }
        @Override public void mouseExited(MouseEvent e)  { btnAgregar.setBackground(UITheme.VERDE_PRINCIPAL); }
    });
    btnAgregar.addActionListener(e -> agregarIngrediente());

    JButton btnRegistrarUso = new JButton("Registrar uso");
    btnRegistrarUso.setFont(UITheme.FONT_BOLD);
    btnRegistrarUso.setBackground(UITheme.MENTA);
    btnRegistrarUso.setForeground(UITheme.VERDE_OSCURO);
    btnRegistrarUso.setFocusPainted(false);
    btnRegistrarUso.setBorderPainted(false);
    btnRegistrarUso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    btnRegistrarUso.setAlignmentX(LEFT_ALIGNMENT);
    btnRegistrarUso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btnRegistrarUso.addMouseListener(new MouseAdapter() {
        @Override public void mouseEntered(MouseEvent e) { btnRegistrarUso.setBackground(UITheme.VERDE_CLARO); }
        @Override public void mouseExited(MouseEvent e)  { btnRegistrarUso.setBackground(UITheme.MENTA); }
    });
    btnRegistrarUso.addActionListener(e -> registrarUso());

    panel.add(lblTitulo);
    panel.add(lblNombre);
    panel.add(Box.createVerticalStrut(4));
    panel.add(txtNombre);
    panel.add(Box.createVerticalStrut(14));
    panel.add(lblDisp);
    panel.add(Box.createVerticalStrut(4));
    panel.add(txtDisponible);
    panel.add(Box.createVerticalStrut(20));
    panel.add(btnAgregar);
    panel.add(Box.createVerticalStrut(8));
    panel.add(btnRegistrarUso);
    panel.add(Box.createVerticalGlue());

    return panel;
}
 
    // ============================================================
    // TABLA DERECHA
    // ============================================================
    private JPanel buildTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
 
        String[] columnas = {"N.", "INGREDIENTE", "DISPONIB.", "USADO", "RESTANTE", "ESTADO"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
 
        tabla = new JTable(tableModel);
        tabla.setFont(UITheme.FONT_BODY);
        tabla.setRowHeight(48);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBackground(UITheme.CARD);
        tabla.setSelectionBackground(UITheme.BG_SUCCESS);
        tabla.setSelectionForeground(UITheme.INK);
        tabla.setFocusable(false);
 
        JTableHeader header = tabla.getTableHeader();
        header.setFont(UITheme.FONT_BOLD);
        header.setBackground(UITheme.VERDE_OSCURO);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createEmptyBorder());
 
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
 
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                lbl.setBorder(new EmptyBorder(0, 16, 0, 16));
                lbl.setFont(col == 1 ? UITheme.FONT_BOLD : UITheme.FONT_BODY);
                lbl.setBackground(row % 2 == 0 ? UITheme.CARD : UITheme.BG);
                lbl.setForeground(UITheme.INK);
                lbl.setOpaque(true);
 
                if (col == 5 && val != null) {
                    String estado = val.toString();
                    if (estado.startsWith("OK")) {
                        lbl.setBackground(UITheme.BG_SUCCESS);
                        lbl.setForeground(UITheme.GREEN_VAL);
                    } else if (estado.startsWith("MEDIO")) {
                        lbl.setBackground(UITheme.BG_WARNING);
                        lbl.setForeground(UITheme.AMBER);
                    } else if (estado.startsWith("ALTO")) {
                        lbl.setBackground(UITheme.BG_DANGER);
                        lbl.setForeground(UITheme.RED_VAL);
                    }
                    lbl.setHorizontalAlignment(CENTER);
                    lbl.setBorder(new CompoundBorder(
                        new EmptyBorder(8, 8, 8, 8),
                        BorderFactory.createEmptyBorder()
                    ));
                }
                
 if (col == 0) {
    lbl.setText(val != null ? val.toString() : "");
    lbl.setHorizontalAlignment(CENTER);
} else if (col >= 2) {
    lbl.setHorizontalAlignment(RIGHT);
} else {
    lbl.setHorizontalAlignment(LEFT);
}

return lbl;
            }
        });
 
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(90);
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(new LineBorder(UITheme.LINE, 1, true));
        scroll.getViewport().setBackground(UITheme.CARD);
 
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
 
    // ============================================================
    // LÓGICA — AGREGAR INGREDIENTE
    // ============================================================
    private void agregarIngrediente() {
    String nombre = txtNombre.getText().trim();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingresa el nombre del ingrediente.",
            "Campo vacío", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        double disp = Double.parseDouble(txtDisponible.getText().replace(",", "."));

        if (disp <= 0) {
            JOptionPane.showMessageDialog(this,
                "La cantidad disponible debe ser mayor a 0.",
                "Valor inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // === BUSCAR SI YA EXISTE ===
        int posExistente = -1;
        for (int i = 1; i <= numIngredientes; i++) {
            if (nombres[i].equalsIgnoreCase(nombre)) {
                posExistente = i;
                break;
            }
        }

        if (posExistente != -1) {
            // === INGREDIENTE YA EXISTE — SUMA AL STOCK ===
            disponibles[posExistente] += disp;
            actualizarTabla();

            JOptionPane.showMessageDialog(this,
                "✅ Ingrediente ya registrado.\n" +
                "Se sumó " + disp + " al stock de " + nombres[posExistente] + ".\n" +
                "Stock actual: " + disponibles[posExistente],
                "Stock actualizado", JOptionPane.INFORMATION_MESSAGE);

        } else {
            // === INGREDIENTE NUEVO ===
            if (numIngredientes >= 10) {
                JOptionPane.showMessageDialog(this,
                    "Inventario lleno. Máximo 10 ingredientes.",
                    "Inventario lleno", JOptionPane.WARNING_MESSAGE);
                return;
            }

            numIngredientes++;
            nombres[numIngredientes]     = nombre;
            disponibles[numIngredientes] = disp;
            usadas[numIngredientes]      = 0;

            actualizarTabla();
            lblContador.setText(numIngredientes + " ingrediente(s) registrado(s)");

            JOptionPane.showMessageDialog(this,
                "✅ Ingrediente agregado correctamente.\n" +
                nombre + " → " + disp + " kg disponibles.",
                "Ingrediente agregado", JOptionPane.INFORMATION_MESSAGE);
        }

        // === LIMPIAR FORMULARIO ===
        txtNombre.setText("");
        txtDisponible.setText("0.00");

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
            "Ingresa un número válido en la cantidad.",
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
 
    // ============================================================
    // LÓGICA — REGISTRAR USO
    // ============================================================
    private void registrarUso() {
        if (numIngredientes == 0) {
            JOptionPane.showMessageDialog(this, "No hay ingredientes registrados.", "Sin ingredientes", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        String[] opciones = new String[numIngredientes];
        for (int i = 1; i <= numIngredientes; i++) {
            opciones[i - 1] = i + ". " + nombres[i] + " (disponible: " + disponibles[i] + ")";
        }
 
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona el ingrediente:", "Registrar uso",
            JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == null) return;
 
        int pos = Integer.parseInt(seleccion.substring(0, seleccion.indexOf(".")));
 
        String cantStr = JOptionPane.showInputDialog(this, "Cantidad utilizada de " + nombres[pos] + ":");
        if (cantStr == null) return;
 
        try {
            double cantidad = Double.parseDouble(cantStr.replace(",", "."));
 
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cantidad > disponibles[pos]) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + disponibles[pos], "Sin stock", JOptionPane.WARNING_MESSAGE);
                return;
            }
 
            disponibles[pos] -= cantidad;
            usadas[pos]      += cantidad;
            actualizarTabla();
 
            JOptionPane.showMessageDialog(this,
                "✅ Uso registrado correctamente.\n" +
                "Ingrediente: " + nombres[pos] + "\n" +
                "Cantidad usada: " + cantidad + "\n" +
                "Restante: " + disponibles[pos],
                "Uso registrado", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ============================================================
    // ACTUALIZAR TABLA
    // ============================================================
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (int i = 1; i <= numIngredientes; i++) {
            double total = disponibles[i] + usadas[i];
            int pct      = total > 0 ? (int)((usadas[i] / total) * 100) : 0;
 
            String estado;
            if (pct <= 50)      estado = "OK (" + pct + "%)";
            else if (pct <= 75) estado = "MEDIO (" + pct + "%)";
            else                estado = "ALTO (" + pct + "%)";
 
           tableModel.addRow(new Object[]{
    String.valueOf(i),
    nombres[i],
    String.format("%.3f", total),
    String.format("%.3f", usadas[i]),
    String.format("%.3f", disponibles[i]),
    estado
});
        }
    }
    // ============================================================
    // INIT COMPONENTS — NO MODIFICAR
    // ============================================================

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

