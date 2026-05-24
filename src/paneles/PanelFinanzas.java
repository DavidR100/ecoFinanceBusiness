/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import ui.RoundedBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import ui.UITheme;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;



/**
 * @author omar.rebolledo
 */
public class PanelFinanzas extends javax.swing.JPanel {
 
    // === DATOS ===
    private double totalIngresos = 0;
    private double totalGastos   = 0;
 
    // === COMPONENTES FORMULARIO ===
    private JComboBox<String> cmbTipo;
    private JTextField txtFecha, txtDescripcion, txtMonto;
 
    // === TOTALES ===
    private JLabel lblTotalIngresos, lblTotalGastos, lblSaldo;
 
    // === TABLA ===
    private DefaultTableModel tableModel;
    private JTable tabla;
 
    public PanelFinanzas() {
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
 
        JLabel title = new JLabel("💰 Módulo 1 — Finanzas");
        title.setFont(UITheme.FONT_EMOJI_BOLD_NORMAL);
        title.setForeground(UITheme.INK);
 
        JLabel subTitle = new JLabel("Registro de ingresos y gastos");
        subTitle.setFont(UITheme.FONT_SMALL);
        subTitle.setForeground(UITheme.MUTED);
 
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subTitle);
        top.add(titleBox, BorderLayout.WEST);
 
        return top;
    }
 
    // ============================================================
    // CONTENIDO PRINCIPAL
    // ============================================================
    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setOpaque(false);
 
        content.add(buildFormulario(), BorderLayout.CENTER);
        content.add(buildDerecha(),    BorderLayout.EAST);
 
        return content;
    }
 
    // ============================================================
    // FORMULARIO IZQUIERDO
    // ============================================================
    private JPanel buildFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
         panel.setBackground(UITheme.CARD);
         panel.setBorder(new CompoundBorder(
            new RoundedBorder(16, UITheme.LINE),
            new EmptyBorder(20, 24, 24, 24)
            ));
 
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
 
        // === TÍTULO FORMULARIO ===
        JLabel lblTitulo = new JLabel("Registrar movimiento");
        lblTitulo.setFont(UITheme.FONT_BOLD);
        lblTitulo.setForeground(UITheme.INK);
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 16, 0));
 
        // === TIPO ===
        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setFont(UITheme.FONT_SMALL);
        lblTipo.setForeground(UITheme.MUTED);
        lblTipo.setAlignmentX(LEFT_ALIGNMENT);
 
        cmbTipo = new JComboBox<>(new String[]{"Ingreso", "Gasto"});
        cmbTipo.setFont(UITheme.FONT_BODY);
        cmbTipo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cmbTipo.setAlignmentX(LEFT_ALIGNMENT);
        cmbTipo.setBackground(UITheme.BG_SUCCESS);
        cmbTipo.setBorder(new RoundedBorder(12, UITheme.VERDE_CLARO));
 
        // === FILA FECHA + DESCRIPCIÓN ===
        JPanel filaFechaDesc = new JPanel(new GridLayout(1, 2, 12, 0));
        filaFechaDesc.setOpaque(false);
        filaFechaDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        filaFechaDesc.setAlignmentX(LEFT_ALIGNMENT);
 
        // Fecha
        JPanel fechaPanel = new JPanel(new BorderLayout(0, 4));
        fechaPanel.setOpaque(false);
        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setFont(UITheme.FONT_SMALL);
        lblFecha.setForeground(UITheme.MUTED);
       txtFecha = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
       txtFecha.setFont(UITheme.FONT_BODY);
       txtFecha.setBackground(UITheme.BG_SUCCESS);
       txtFecha.setBorder(new RoundedBorder(12, UITheme.VERDE_CLARO));
        fechaPanel.add(lblFecha,  BorderLayout.NORTH);
        fechaPanel.add(txtFecha,  BorderLayout.CENTER);
 
        // Descripción
        JPanel descPanel = new JPanel(new BorderLayout(0, 4));
        descPanel.setOpaque(false);
        JLabel lblDesc = new JLabel("Descripción");
        lblDesc.setFont(UITheme.FONT_SMALL);
        lblDesc.setForeground(UITheme.MUTED);
        txtDescripcion = new JTextField();
        txtDescripcion.setFont(UITheme.FONT_BODY);
        txtDescripcion.setBackground(UITheme.BG_SUCCESS);
        txtDescripcion.setBorder(new RoundedBorder(12, UITheme.VERDE_CLARO));
 
        descPanel.add(lblDesc,         BorderLayout.NORTH);
        descPanel.add(txtDescripcion,  BorderLayout.CENTER);
 
        filaFechaDesc.add(fechaPanel);
        filaFechaDesc.add(descPanel);
 
        // === MONTO GRANDE ===
        JLabel lblMonto = new JLabel("Monto ($)");
        lblMonto.setFont(UITheme.FONT_SMALL);
        lblMonto.setForeground(UITheme.MUTED);
        lblMonto.setAlignmentX(LEFT_ALIGNMENT);
 
        txtMonto = new JTextField("0");
        txtMonto.setFont(new Font("Segoe UI", Font.BOLD, 28));
        txtMonto.setForeground(UITheme.VERDE_PRINCIPAL);
        txtMonto.setHorizontalAlignment(JTextField.RIGHT);
        txtMonto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        txtMonto.setAlignmentX(LEFT_ALIGNMENT);
        txtMonto.setBackground(UITheme.BG_SUCCESS);
        txtMonto.setBorder(new RoundedBorder(12, UITheme.VERDE_CLARO));
 
        // === BOTONES ===
// === BOTONES ===
JPanel botonesPanel = new JPanel();
botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
botonesPanel.setOpaque(false);
botonesPanel.setAlignmentX(LEFT_ALIGNMENT);
botonesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

JButton btnRegistrar = new JButton("Registrar movimiento");
btnRegistrar.setFont(UITheme.FONT_BOLD);
btnRegistrar.setForeground(Color.WHITE);
btnRegistrar.setFocusPainted(false);
btnRegistrar.setOpaque(true);
btnRegistrar.putClientProperty("JButton.buttonType", "none");
btnRegistrar.putClientProperty("FlatLaf.style", "background: #1B6B3A; foreground: #FFFFFF");
btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
btnRegistrar.setAlignmentX(LEFT_ALIGNMENT);
btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
btnRegistrar.setBorder(new EmptyBorder(12, 20, 12, 20));
btnRegistrar.addMouseListener(new MouseAdapter() {
    @Override public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(UITheme.VERDE_MEDIO); }
    @Override public void mouseExited(MouseEvent e)  { btnRegistrar.setBackground(UITheme.VERDE_PRINCIPAL); }
});
btnRegistrar.addActionListener(e -> registrarMovimiento());

JButton btnCancelar = new JButton("Cancelar");
btnCancelar.setFont(UITheme.FONT_BOLD);
btnCancelar.setForeground(UITheme.VERDE_OSCURO);
btnCancelar.setFocusPainted(false);
btnCancelar.setOpaque(true);
btnCancelar.putClientProperty("JButton.buttonType", "none");
btnCancelar.putClientProperty("FlatLaf.style", "background: #E6F7ED; foreground: #0D3D21");
btnCancelar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
btnCancelar.setAlignmentX(LEFT_ALIGNMENT);
btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
btnCancelar.setBorder(new EmptyBorder(12, 20, 12, 20));
btnCancelar.addActionListener(e -> limpiarFormulario());

botonesPanel.add(btnRegistrar);
botonesPanel.add(Box.createVerticalStrut(8));
botonesPanel.add(btnCancelar);
 
        // === AGREGAR TODO ===
        inner.add(lblTitulo);
        inner.add(lblTipo);
        inner.add(Box.createVerticalStrut(4));
        inner.add(cmbTipo);
        inner.add(Box.createVerticalStrut(14));
        inner.add(filaFechaDesc);
        inner.add(Box.createVerticalStrut(14));
        inner.add(lblMonto);
        inner.add(Box.createVerticalStrut(4));
        inner.add(txtMonto);
        inner.add(Box.createVerticalStrut(20));
        inner.add(botonesPanel);
        inner.add(Box.createVerticalGlue());
 
        panel.add(inner, BorderLayout.CENTER);
        return panel;
    }
 
    // ============================================================
    // PANEL DERECHO (TOTALES + TABLA)
    // ============================================================
    private JPanel buildDerecha() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(460, 0));
 
        panel.add(buildTotales(), BorderLayout.NORTH);
        panel.add(buildTabla(),   BorderLayout.CENTER);
 
        return panel;
    }
 
    // === TOTALES ===
    private JPanel buildTotales() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 0, 0));
          panel.setBackground(UITheme.CARD);
          panel.setBorder(new CompoundBorder(
             new RoundedBorder(16, UITheme.LINE),
             new EmptyBorder(16, 20, 16, 20)
             ));
 
        panel.add(buildTotalItem("TOTAL INGRESOS", "$0",       UITheme.VERDE_PRINCIPAL, true));
        panel.add(buildTotalItem("TOTAL GASTOS",   "$0",       UITheme.CORAL,           false));
        panel.add(buildTotalItem("SALDO",          "$0",       UITheme.VERDE_PRINCIPAL, false));
 
        // Guardar referencias
        lblTotalIngresos = (JLabel) ((JPanel) panel.getComponent(0)).getComponent(1);
        lblTotalGastos   = (JLabel) ((JPanel) panel.getComponent(1)).getComponent(1);
        lblSaldo         = (JLabel) ((JPanel) panel.getComponent(2)).getComponent(1);
 
        return panel;
    }
 
    private JPanel buildTotalItem(String etiqueta, String valor, Color color, boolean borde) {
        JPanel item = new JPanel(new BorderLayout(0, 4));
        item.setOpaque(false);
        if (borde) item.setBorder(new EmptyBorder(0, 0, 0, 20));
 
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(UITheme.FONT_TINY);
        lblEtiqueta.setForeground(UITheme.MUTED);
 
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValor.setForeground(color);
 
        item.add(lblEtiqueta, BorderLayout.NORTH);
        item.add(lblValor,    BorderLayout.CENTER);
 
        return item;
    }
 
    // === TABLA ===
 private JPanel buildTabla() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setOpaque(false);

    String[] columnas = {"TIPO", "DESCRIPCIÓN", "FECHA", "MONTO"};
    tableModel = new DefaultTableModel(columnas, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    tabla = new JTable(tableModel);
    tabla.setFont(UITheme.FONT_BODY);
    tabla.setRowHeight(46);
    tabla.setShowGrid(false);
    tabla.setIntercellSpacing(new Dimension(0, 0));
    tabla.setBackground(UITheme.CARD);
    tabla.setSelectionBackground(UITheme.BG_SUCCESS);
    tabla.setFocusable(false);

    // === HEADER ===
    JTableHeader header = tabla.getTableHeader();
    header.setFont(UITheme.FONT_BOLD);
    header.setBackground(UITheme.VERDE_PRINCIPAL);
    header.setForeground(UITheme.MENTA);
    header.setPreferredSize(new Dimension(0, 40));
    header.setBorder(BorderFactory.createEmptyBorder());
    header.setDefaultRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val,
                boolean sel, boolean foc, int row, int col) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
            lbl.setBackground(UITheme.VERDE_PRINCIPAL);
            lbl.setForeground(UITheme.MENTA);
            lbl.setFont(UITheme.FONT_BOLD);
            lbl.setHorizontalAlignment(LEFT);
            lbl.setBorder(new EmptyBorder(0, 12, 0, 12));
            lbl.setOpaque(true);
            return lbl;
        }
    });
 
        // === RENDERER ===
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
 
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                lbl.setOpaque(true);
                lbl.setBackground(row % 2 == 0 ? UITheme.CARD : UITheme.BG);
                lbl.setForeground(UITheme.INK);
                lbl.setFont(UITheme.FONT_BODY);
                lbl.setBorder(new EmptyBorder(0, 12, 0, 12));
 
                // === CHIP TIPO (col 0) ===
                if (col == 0 && val != null) {
                    String tipo = val.toString();
                    if (tipo.equals("Ingreso")) {
                        lbl.setBackground(UITheme.BG_SUCCESS);
                        lbl.setForeground(UITheme.VERDE_PRINCIPAL);
                    } else {
                        lbl.setBackground(UITheme.BG_DANGER);
                        lbl.setForeground(UITheme.CORAL);
                    }
                    lbl.setHorizontalAlignment(CENTER);
                }
 
                // === MONTO (col 3) ===
                if (col == 3 && val != null) {
                    String monto = val.toString();
                    if (monto.startsWith("-")) {
                        lbl.setForeground(UITheme.CORAL);
                    } else {
                        lbl.setForeground(UITheme.VERDE_PRINCIPAL);
                    }
                    lbl.setFont(UITheme.FONT_BOLD);
                    lbl.setHorizontalAlignment(RIGHT);
                }
 
                if (col == 2) lbl.setHorizontalAlignment(CENTER);
                else if (col == 1) lbl.setHorizontalAlignment(LEFT);
 
                return lbl;
            }
        });
 
        // === ANCHOS ===
        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
 
        JScrollPane scroll = new JScrollPane(tabla);
         scroll.setBorder(new RoundedBorder(16, UITheme.LINE));
         scroll.setOpaque(false);
         scroll.getViewport().setOpaque(false);
         scroll.getViewport().setBorder(null);
         scroll.setBorder(new RoundedBorder(16, UITheme.LINE));
         scroll.getViewport().setBackground(UITheme.CARD);
 
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
 
    // ============================================================
    // LÓGICA — REGISTRAR MOVIMIENTO
    // ============================================================
    private void registrarMovimiento() {
    String tipo  = (String) cmbTipo.getSelectedItem();
    String fecha = txtFecha.getText().trim();
    String desc  = txtDescripcion.getText().trim();

    if (fecha.isEmpty() || desc.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Completa todos los campos.",
            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        double monto = Double.parseDouble(txtMonto.getText().trim().replace(",", "."));

        if (monto <= 0) {
            JOptionPane.showMessageDialog(this,
                "El monto debe ser mayor a 0.",
                "Valor inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // === BUSCAR SI YA EXISTE EL MISMO TIPO + DESCRIPCIÓN ===
        int filaExistente = -1;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String tipoFila = tableModel.getValueAt(i, 0).toString();
            String descFila = tableModel.getValueAt(i, 1).toString();
            if (tipoFila.equalsIgnoreCase(tipo) && descFila.equalsIgnoreCase(desc)) {
                filaExistente = i;
                break;
            }
        }

        if (filaExistente != -1) {
            // === YA EXISTE — SUMAR AL MONTO ===
            String montoActualStr = tableModel.getValueAt(filaExistente, 3).toString()
                .replace("$", "").replace("-", "").replace(",", "").trim();
            double montoActual = Double.parseDouble(montoActualStr);
            double nuevoMonto  = montoActual + monto;

            String montoFormateado = tipo.equals("Ingreso")
                ? "$" + String.format("%,.0f", nuevoMonto)
                : "-$" + String.format("%,.0f", nuevoMonto);

            tableModel.setValueAt(montoFormateado, filaExistente, 3);
            tableModel.setValueAt(fecha, filaExistente, 2);

        } else {
            // === NUEVO REGISTRO ===
            String montoFormateado = tipo.equals("Ingreso")
                ? "$" + String.format("%,.0f", monto)
                : "-$" + String.format("%,.0f", monto);

            tableModel.insertRow(0, new Object[]{tipo, desc, fecha, montoFormateado});
        }

        // === ACTUALIZAR TOTALES ===
        if (tipo.equals("Ingreso")) {
            totalIngresos += monto;
        } else {
            totalGastos += monto;
        }

        actualizarTotales();
        limpiarFormulario();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
            "Ingresa un monto válido.",
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
 
    private void actualizarTotales() {
        double saldo = totalIngresos - totalGastos;
 
        lblTotalIngresos.setText("$" + String.format("%,.0f", totalIngresos));
        lblTotalGastos.setText("$" + String.format("%,.0f", totalGastos));
        lblSaldo.setText("$" + String.format("%,.0f", saldo));
 
        lblSaldo.setForeground(saldo >= 0 ? UITheme.VERDE_PRINCIPAL : UITheme.CORAL);
    }
 
    private void limpiarFormulario() {
        txtDescripcion.setText("");
        txtMonto.setText("0");
        txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        cmbTipo.setSelectedIndex(0);
    }
 

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
