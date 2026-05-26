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
import principal.AppState;

/**
 * @author omar.rebolledo
 */
public class PanelInventario extends javax.swing.JPanel {

    // === COMPONENTES FORMULARIO ===
    private JTextField txtNombre, txtDisponible;
    private JLabel     lblContador;
 
    // === TABLA ===
    private DefaultTableModel tableModel;
    private JTable            tabla;
 
    // ── Constructor ───────────────────────────────────────
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
 
        JLabel title = new JLabel("📦  Módulo 2 — Inventario");
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
        panel.setPreferredSize(new Dimension(340, 0));
 
        // Título
        JLabel lblTitulo = new JLabel("Agregar ingrediente");
        lblTitulo.setFont(UITheme.FONT_BOLD);
        lblTitulo.setForeground(UITheme.INK);
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 16, 0));
 
        // Campo: Nombre
        JLabel lblNombre = new JLabel("Nombre del ingrediente");
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
 
        // Campo: Cantidad disponible
        JLabel lblDisp = new JLabel("Cantidad disponible (kg / unidades)");
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
 
        // Botón: Agregar
        JButton btnAgregar = new JButton("+ Agregar ingrediente");
        btnAgregar.setFont(UITheme.FONT_BOLD);
        btnAgregar.setBackground(UITheme.VERDE_PRINCIPAL);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnAgregar.setAlignmentX(LEFT_ALIGNMENT);
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnAgregar.setBackground(UITheme.VERDE_MEDIO); }
            @Override public void mouseExited(MouseEvent e)  { btnAgregar.setBackground(UITheme.VERDE_PRINCIPAL); }
        });
        btnAgregar.addActionListener(e -> agregarIngrediente());
 
        // Botón: Registrar uso
        JButton btnRegistrarUso = new JButton("✏ Registrar uso");
        btnRegistrarUso.setFont(UITheme.FONT_BOLD);
        btnRegistrarUso.setBackground(UITheme.MENTA);
        btnRegistrarUso.setForeground(UITheme.VERDE_OSCURO);
        btnRegistrarUso.setFocusPainted(false);
        btnRegistrarUso.setBorderPainted(false);
        btnRegistrarUso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnRegistrarUso.setAlignmentX(LEFT_ALIGNMENT);
        btnRegistrarUso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegistrarUso.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnRegistrarUso.setBackground(UITheme.VERDE_CLARO); }
            @Override public void mouseExited(MouseEvent e)  { btnRegistrarUso.setBackground(UITheme.MENTA); }
        });
        btnRegistrarUso.addActionListener(e -> registrarUso());
 
        // Ensamblar
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
 
        String[] columnas = {"N.", "INGREDIENTE", "TOTAL", "USADO", "RESTANTE", "ESTADO"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
 
        tabla = new JTable(tableModel);
        tabla.setFont(UITheme.FONT_BODY);
        tabla.setRowHeight(44);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBackground(UITheme.CARD);
        tabla.setSelectionBackground(UITheme.BG_SUCCESS);
        tabla.setSelectionForeground(UITheme.INK);
        tabla.setFocusable(false);
 
        // Header
        JTableHeader header = tabla.getTableHeader();
        header.setFont(UITheme.FONT_BOLD);
        header.setBackground(UITheme.VERDE_OSCURO);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(
                        t, val, sel, foc, row, col);
                l.setBackground(UITheme.VERDE_OSCURO);
                l.setForeground(Color.WHITE);
                l.setFont(UITheme.FONT_BOLD);
                l.setBorder(new EmptyBorder(0, 12, 0, 12));
                l.setOpaque(true);
                return l;
            }
        });
 
        // Renderer por celda
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        t, val, sel, foc, row, col);
                lbl.setBorder(new EmptyBorder(0, 12, 0, 12));
                lbl.setFont(col == 1 ? UITheme.FONT_BOLD : UITheme.FONT_BODY);
                lbl.setBackground(row % 2 == 0 ? UITheme.CARD : UITheme.BG);
                lbl.setForeground(UITheme.INK);
                lbl.setOpaque(true);
 
                // Columna ESTADO con color semáforo
                if (col == 5 && val != null) {
                    String estado = val.toString();
                    if      (estado.startsWith("OK"))    { lbl.setBackground(UITheme.BG_SUCCESS); lbl.setForeground(UITheme.GREEN_VAL); }
                    else if (estado.startsWith("MEDIO")) { lbl.setBackground(UITheme.BG_WARNING); lbl.setForeground(UITheme.AMBER); }
                    else if (estado.startsWith("ALTO"))  { lbl.setBackground(UITheme.BG_DANGER);  lbl.setForeground(UITheme.RED_VAL); }
                    lbl.setHorizontalAlignment(CENTER);
                } else if (col == 0) {
                    lbl.setHorizontalAlignment(CENTER);
                } else if (col >= 2) {
                    lbl.setHorizontalAlignment(RIGHT);
                } else {
                    lbl.setHorizontalAlignment(LEFT);
                }
                return lbl;
            }
        });
 
        // Anchos
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(90);
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(new LineBorder(UITheme.LINE, 1, true));
        scroll.getViewport().setBackground(UITheme.CARD);
 
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
 
    // ============================================================
    // LÓGICA — AGREGAR INGREDIENTE  (base-0 corregido)
    // ============================================================
    private void agregarIngrediente() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Ingresa el nombre del ingrediente.",
                "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        double disp;
        try {
            disp = Double.parseDouble(txtDisponible.getText().replace(",", "."));
            if (disp <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingresa una cantidad válida mayor a 0.",
                "Valor inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // ── Buscar si ya existe (base-0) ─────────────────
        int posExistente = -1;
        for (int i = 0; i < AppState.numIngredientes; i++) {
            if (AppState.nomIngredientes[i].equalsIgnoreCase(nombre)) {
                posExistente = i;
                break;
            }
        }
 
        if (posExistente != -1) {
            // Ingrediente existe → sumar stock
            AppState.cantDisponible[posExistente] += disp;
            actualizarTabla();
            JOptionPane.showMessageDialog(this,
                "✅ Ingrediente ya registrado.\n"
                + "Se sumaron " + disp + " al stock de '"
                + AppState.nomIngredientes[posExistente] + "'.\n"
                + "Stock total: " + AppState.cantDisponible[posExistente],
                "Stock actualizado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Ingrediente nuevo → verificar límite
            if (AppState.numIngredientes >= 10) {
                JOptionPane.showMessageDialog(this,
                    "Inventario lleno. Máximo 10 ingredientes.",
                    "Inventario lleno", JOptionPane.WARNING_MESSAGE);
                return;
            }
 
            // ── CORRECCIÓN BUG: guardar en [numIngredientes] ANTES de incrementar
            // Antes (buggy):  numIngredientes++; arr[numIngredientes] = valor; → base-1
            // Ahora (correcto): arr[numIngredientes] = valor; numIngredientes++;  → base-0
            AppState.nomIngredientes[AppState.numIngredientes] = nombre;
            AppState.cantDisponible[AppState.numIngredientes]  = disp;
            AppState.cantUsada[AppState.numIngredientes]       = 0;
            AppState.numIngredientes++;                         // ← incrementar AL FINAL
 
            actualizarTabla();
            lblContador.setText(AppState.numIngredientes
                + " ingrediente(s) registrado(s)");
 
            JOptionPane.showMessageDialog(this,
                "✅ Ingrediente agregado correctamente.\n"
                + "'" + nombre + "' → " + disp + " unidades disponibles.",
                "Ingrediente agregado", JOptionPane.INFORMATION_MESSAGE);
        }
 
        // Limpiar formulario
        txtNombre.setText("");
        txtDisponible.setText("0.00");
    }
 
    // ============================================================
    // LÓGICA — REGISTRAR USO  (base-0 corregido)
    // ============================================================
    private void registrarUso() {
        if (AppState.numIngredientes == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay ingredientes registrados.",
                "Sin ingredientes", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // ── Construir opciones desde índice 0 ────────────
        String[] opciones = new String[AppState.numIngredientes];
        for (int i = 0; i < AppState.numIngredientes; i++) {
            opciones[i] = (i + 1) + ". "
                + AppState.nomIngredientes[i]           // lee base-0 correctamente
                + "  (disponible: "
                + String.format("%.3f", AppState.cantDisponible[i]) + ")";
        }
 
        String seleccion = (String) JOptionPane.showInputDialog(
            this, "Selecciona el ingrediente a usar:",
            "Registrar uso", JOptionPane.PLAIN_MESSAGE,
            null, opciones, opciones[0]);
        if (seleccion == null) return;
 
        // ── Extraer índice real (número del string - 1) ──
        // El string tiene forma "1. Harina  (disponible: 5.000)"
        // El número visible es base-1 para el usuario, pero el índice real es base-0
        int numVisible = Integer.parseInt(
            seleccion.substring(0, seleccion.indexOf(".")));
        int idx = numVisible - 1;           // ← CORRECCIÓN: convertir a base-0
 
        String cantStr = JOptionPane.showInputDialog(this,
            "Cantidad a usar de '" + AppState.nomIngredientes[idx] + "'\n"
            + "Disponible: " + String.format("%.3f", AppState.cantDisponible[idx]));
        if (cantStr == null) return;
 
        double cantidad;
        try {
            cantidad = Double.parseDouble(cantStr.trim().replace(",", "."));
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingresa un número válido mayor a 0.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
 
        if (cantidad > AppState.cantDisponible[idx]) {
            JOptionPane.showMessageDialog(this,
                "Stock insuficiente.\nDisponible: "
                + String.format("%.3f", AppState.cantDisponible[idx]),
                "Sin stock", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // Actualizar AppState (base-0)
        AppState.cantDisponible[idx] -= cantidad;
        AppState.cantUsada[idx]      += cantidad;
 
        actualizarTabla();
 
        JOptionPane.showMessageDialog(this,
            "✅ Uso registrado correctamente.\n"
            + "Ingrediente: " + AppState.nomIngredientes[idx] + "\n"
            + "Cantidad usada: " + String.format("%.3f", cantidad) + "\n"
            + "Restante: " + String.format("%.3f", AppState.cantDisponible[idx]),
            "Uso registrado", JOptionPane.INFORMATION_MESSAGE);
    }
 
    // ============================================================
    // ACTUALIZAR TABLA  (base-0 corregido)
    // ============================================================
    public void actualizarTabla() {
        tableModel.setRowCount(0);
 
        // ── CORRECCIÓN: iterar de 0 a numIngredientes-1 (base-0) ──
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double disponible = AppState.cantDisponible[i];
            double usado      = AppState.cantUsada[i];
            // "total original" = disponible actual + lo que ya se usó
            double totalOriginal = disponible + usado;
            // Porcentaje de uso sobre el total original
            int pctUso = totalOriginal > 0
                ? (int)((usado / totalOriginal) * 100) : 0;
            // Porcentaje de sobrante (para desperdicio)
            double pctSobrante = totalOriginal > 0
                ? (disponible / totalOriginal) * 100 : 0;
 
            String estado;
            if      (pctSobrante > 30) estado = "ALTO ("   + String.format("%.0f", pctSobrante) + "%)";
            else if (pctSobrante > 15) estado = "MEDIO ("  + String.format("%.0f", pctSobrante) + "%)";
            else                       estado = "OK ("      + String.format("%.0f", pctSobrante) + "%)";
 
            tableModel.addRow(new Object[]{
                String.valueOf(i + 1),              // número visible base-1
                AppState.nomIngredientes[i],
                String.format("%.3f", totalOriginal),
                String.format("%.3f", usado),
                String.format("%.3f", disponible),  // restante
                estado
            });
        }
    }

    // ── Actualizar tabla con datos de AppState ────────────
   /* 
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double restante = AppState.cantDisponible[i] - AppState.cantUsada[i];
            double pDesp = AppState.cantDisponible[i] > 0
                    ? (restante / AppState.cantDisponible[i]) * 100 : 0;
            String estado = pDesp > 30 ? "ALTO"
                    : pDesp > 15 ? "MEDIO" : "OK";
            tableModel.addRow(new Object[]{
                i + 1,
                AppState.nomIngredientes[i],
                AppState.FMT_NUM.format(AppState.cantDisponible[i]),
                AppState.FMT_NUM.format(AppState.cantUsada[i]),
                AppState.FMT_NUM.format(restante),
                estado + " (" + String.format("%.0f", pDesp) + "%)"
            });
        }
    }
*/
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
