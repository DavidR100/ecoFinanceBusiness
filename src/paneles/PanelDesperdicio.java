/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JOptionPane;
import ui.UITheme;

/**
 * PanelDesperdicio — con formulario funcional, historial y recomendaciones
 * @author omar.rebolledo / corregido
 */
public class PanelDesperdicio extends javax.swing.JPanel {

    // === DATOS ===
    private String[] nombresReg    = new String[50];
    private double[] cantidadesReg = new double[50];
    private int numRegistros       = 0;
    private double totalDesperdicio = 0;

    // === COMPONENTES DINÁMICOS ===
    private JLabel   lblPctTotal, lblMensaje, lblTotalKg;
    private JPanel   barraFillTotal, gridCards;
    private JTextField txtIngrediente, txtCantidad;

    public PanelDesperdicio() {
        setBackground(UITheme.BG);
        setOpaque(true);
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

        JLabel title = new JLabel("📊 Módulo 3 — Desperdicio");
        title.setFont(UITheme.FONT_EMOJI_BOLD_NORMAL);
        title.setForeground(UITheme.INK);

        JLabel subTitle = new JLabel("Registro y análisis de desperdicios por ingrediente");
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
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);

        content.add(buildFormulario(),   BorderLayout.NORTH);
        content.add(buildBannerTotal(),  BorderLayout.CENTER);
        content.add(buildGrid(),         BorderLayout.SOUTH);

        return content;
    }

    // ============================================================
    // FORMULARIO DE REGISTRO
    // ============================================================
    private JPanel buildFormulario() {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.CARD);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 8, 0, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        // === LABEL INGREDIENTE ===
        JLabel lblIng = new JLabel("Ingrediente:");
        lblIng.setFont(UITheme.FONT_BOLD);
        lblIng.setForeground(UITheme.MUTED);
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        card.add(lblIng, c);

        // === CAMPO INGREDIENTE ===
        txtIngrediente = new JTextField();
        txtIngrediente.setFont(UITheme.FONT_BODY);
        txtIngrediente.setForeground(UITheme.INK);
        txtIngrediente.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.LINE, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        txtIngrediente.setPreferredSize(new Dimension(200, 36));
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0;
        card.add(txtIngrediente, c);

        // === LABEL CANTIDAD ===
        JLabel lblCant = new JLabel("Cantidad (kg):");
        lblCant.setFont(UITheme.FONT_BOLD);
        lblCant.setForeground(UITheme.MUTED);
        c.gridx = 2; c.gridy = 0; c.weightx = 0;
        card.add(lblCant, c);

        // === CAMPO CANTIDAD ===
        txtCantidad = new JTextField();
        txtCantidad.setFont(UITheme.FONT_BODY);
        txtCantidad.setForeground(UITheme.INK);
        txtCantidad.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.LINE, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        txtCantidad.setPreferredSize(new Dimension(120, 36));
        c.gridx = 3; c.gridy = 0; c.weightx = 0.5;
        card.add(txtCantidad, c);

        // === BOTÓN REGISTRAR ===
        JButton btnRegistrar = crearBoton("＋  Registrar", UITheme.VERDE_PRINCIPAL);
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnRegistrar.setBackground(UITheme.VERDE_MEDIO); }
            @Override public void mouseExited(MouseEvent e)  { btnRegistrar.setBackground(UITheme.VERDE_PRINCIPAL); }
        });
        btnRegistrar.addActionListener(e -> registrarDesperdicio());
        c.gridx = 4; c.gridy = 0; c.weightx = 0;
        card.add(btnRegistrar, c);

        // === BOTÓN VER RESUMEN ===
        JButton btnResumen = crearBoton("📋  Ver Resumen", UITheme.BLUE);
        btnResumen.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnResumen.setBackground(new Color(0x155A8A)); }
            @Override public void mouseExited(MouseEvent e)  { btnResumen.setBackground(UITheme.BLUE); }
        });
        btnResumen.addActionListener(e -> mostrarResumen());
        c.gridx = 5; c.gridy = 0; c.weightx = 0;
        card.add(btnResumen, c);

        return card;
    }

    // ============================================================
    // BANNER TOTAL
    // ============================================================
    private JPanel buildBannerTotal() {
        JPanel banner = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.CARD);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
            }
        };
        banner.setOpaque(false);
        banner.setBorder(new EmptyBorder(16, 20, 16, 20));

        JPanel izq = new JPanel(new BorderLayout(0, 6));
        izq.setOpaque(false);

        JLabel lblEtiqueta = new JLabel("DESPERDICIO TOTAL REGISTRADO");
        lblEtiqueta.setFont(UITheme.FONT_TINY);
        lblEtiqueta.setForeground(UITheme.MUTED);

        lblTotalKg = new JLabel("0.000 kg");
        lblTotalKg.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotalKg.setForeground(UITheme.VERDE_PRINCIPAL);

        JPanel barraCont = new JPanel(new BorderLayout());
        barraCont.setOpaque(false);
        barraCont.setPreferredSize(new Dimension(0, 14));

        JPanel barraFondo = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.LINE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
            }
        };
        barraFondo.setOpaque(false);

        barraFillTotal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
            }
        };
        barraFillTotal.setOpaque(false);
        barraFillTotal.setBackground(UITheme.VERDE_PRINCIPAL);
        barraFillTotal.setPreferredSize(new Dimension(0, 10));
        barraFondo.add(barraFillTotal, BorderLayout.WEST);
        barraCont.add(barraFondo, BorderLayout.CENTER);

        lblMensaje = new JLabel("✓ Sin desperdicios registrados.");
        lblMensaje.setFont(UITheme.FONT_SMALL);
        lblMensaje.setForeground(UITheme.VERDE_PRINCIPAL);

        izq.add(lblEtiqueta, BorderLayout.NORTH);
        izq.add(lblTotalKg,  BorderLayout.CENTER);

        JPanel barraYMensaje = new JPanel(new BorderLayout(0, 6));
        barraYMensaje.setOpaque(false);
        barraYMensaje.add(barraCont,  BorderLayout.NORTH);
        barraYMensaje.add(lblMensaje, BorderLayout.CENTER);

        JPanel bannerTop = new JPanel(new BorderLayout());
        bannerTop.setOpaque(false);
        bannerTop.add(izq, BorderLayout.WEST);

        banner.add(bannerTop,     BorderLayout.NORTH);
        banner.add(barraYMensaje, BorderLayout.CENTER);

        return banner;
    }

    // ============================================================
    // GRID DE TARJETAS
    // ============================================================
    private JPanel buildGrid() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        gridCards = new JPanel(new GridLayout(0, 3, 12, 12));
        gridCards.setOpaque(false);

        wrapper.add(gridCards, BorderLayout.NORTH);
        return wrapper;
    }

    // ============================================================
    // REGISTRAR DESPERDICIO
    // ============================================================
    private void registrarDesperdicio() {
        String ingrediente = txtIngrediente.getText().trim();
        String cantTexto   = txtCantidad.getText().trim();

        if (ingrediente.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingresa el nombre del ingrediente.",
                "Campo vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantTexto.replace(",", "."));
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingresa una cantidad valida mayor a 0.",
                "Cantidad invalida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Guardar registro
        nombresReg[numRegistros]    = ingrediente;
        cantidadesReg[numRegistros] = cantidad;
        numRegistros++;
        totalDesperdicio += cantidad;

        // Limpiar campos
        txtIngrediente.setText("");
        txtCantidad.setText("");

        // Actualizar vista
        actualizarBannerTotal();
        actualizarGrid();
    }

    // ============================================================
    // ACTUALIZAR BANNER
    // ============================================================
    private void actualizarBannerTotal() {
        String nivel, mensaje;
        Color color;

        if (totalDesperdicio == 0) {
            nivel   = "";
            color   = UITheme.VERDE_PRINCIPAL;
            mensaje = "✓ Sin desperdicios registrados.";
        } else if (totalDesperdicio <= 10) {
            nivel   = " — ACEPTABLE ✓";
            color   = UITheme.VERDE_PRINCIPAL;
            mensaje = "✓ Desperdicio bajo control. ¡Buen trabajo!";
        } else if (totalDesperdicio <= 25) {
            nivel   = " — MODERADO !";
            color   = UITheme.AMBER;
            mensaje = "! Desperdicio moderado. Ajuste porciones.";
        } else {
            nivel   = " — CRÍTICO ⚠";
            color   = UITheme.CORAL;
            mensaje = "⚠ Desperdicio ALTO. Tome acciones inmediatas.";
        }

        lblTotalKg.setText(String.format("%.3f kg%s", totalDesperdicio, nivel));
        lblTotalKg.setForeground(color);
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(color);
        barraFillTotal.setBackground(color);

        SwingUtilities.invokeLater(() -> {
            int anchoTotal = barraFillTotal.getParent() != null
                ? barraFillTotal.getParent().getWidth() : 260;
            double pct = Math.min(totalDesperdicio / 25.0, 1.0);
            barraFillTotal.setPreferredSize(new Dimension((int)(anchoTotal * pct), 10));
            if (barraFillTotal.getParent() != null)
                barraFillTotal.getParent().revalidate();
        });
    }

    // ============================================================
    // ACTUALIZAR GRID DE TARJETAS
    // ============================================================
    private void actualizarGrid() {
        gridCards.removeAll();
        for (int i = 0; i < numRegistros; i++) {
            gridCards.add(buildCard(nombresReg[i], cantidadesReg[i]));
        }
        gridCards.revalidate();
        gridCards.repaint();
    }

    private JPanel buildCard(String nombre, double cantidad) {
        Color colorCant;
        String estado;

        if (cantidad <= 5) {
            colorCant = UITheme.VERDE_PRINCIPAL;
            estado    = "Bajo control";
        } else if (cantidad <= 15) {
            colorCant = UITheme.AMBER;
            estado    = "Nivel moderado";
        } else {
            colorCant = UITheme.CORAL;
            estado    = "⚠ Nivel crítico";
        }

        JPanel card = new JPanel(new BorderLayout(0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.CARD);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(UITheme.FONT_BOLD);
        lblNombre.setForeground(UITheme.INK);

        JLabel lblCantidad = new JLabel(String.format("%.3f kg", cantidad));
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCantidad.setForeground(colorCant);

        JLabel lblEstado = new JLabel(estado);
        lblEstado.setFont(UITheme.FONT_SMALL);
        lblEstado.setForeground(colorCant);

        JPanel centro = new JPanel(new BorderLayout(0, 4));
        centro.setOpaque(false);
        centro.add(lblCantidad, BorderLayout.NORTH);
        centro.add(lblEstado,   BorderLayout.CENTER);

        card.add(lblNombre, BorderLayout.NORTH);
        card.add(centro,    BorderLayout.CENTER);

        return card;
    }

    // ============================================================
    // VER RESUMEN CON RECOMENDACIONES
    // ============================================================
    private void mostrarResumen() {
        if (numRegistros == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay desperdicios registrados aun.",
                "Sin registros", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("======================================\n");
        sb.append("       RESUMEN DE DESPERDICIO\n");
        sb.append("======================================\n\n");

        sb.append("  Registros:\n");
        for (int i = 0; i < numRegistros; i++) {
            sb.append(String.format("  • %-20s %.3f kg%n",
                nombresReg[i], cantidadesReg[i]));
        }

        sb.append(String.format("%n  Total desperdiciado: %.3f kg%n", totalDesperdicio));

        String nivel;
        String recomendaciones;

        if (totalDesperdicio <= 10) {
            nivel = "OK ACEPTABLE";
            recomendaciones =
                "  * Excelente gestion. Desperdicio bajo control.\n" +
                "  * Continue monitoreando porciones diariamente.\n" +
                "  * Mantenga el registro para detectar tendencias.";
        } else if (totalDesperdicio <= 25) {
            nivel = "! MODERADO";
            recomendaciones =
                "  * Desperdicio moderado. Ajuste el tamano de porciones.\n" +
                "  * Identifique los ingredientes con mas sobrante.\n" +
                "  * Considere reusar sobrantes en el menu del dia.\n" +
                "  * Lleve los excedentes al modulo de Sostenibilidad 3R.";
        } else {
            nivel = "CRITICO - Accion inmediata necesaria";
            recomendaciones =
                "  * Reduzca las compras del ingrediente con mas sobrante.\n" +
                "  * Ajuste el menu para aprovechar existencias actuales.\n" +
                "  * Procese excedentes en el modulo de Sostenibilidad.\n" +
                "  * Capacite al equipo en control de porciones.\n" +
                "  * Evalue cambiar proveedores o cantidades de pedido.";
        }

        sb.append(String.format("  Nivel: %s%n%n", nivel));
        sb.append("  RECOMENDACIONES:\n");
        sb.append(recomendaciones);
        sb.append("\n\n======================================");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(UITheme.BG);
        textArea.setForeground(UITheme.INK);
        textArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(480, 340));
        scroll.setBorder(null);

        JOptionPane.showMessageDialog(this, scroll,
            "Resumen de Desperdicio", JOptionPane.PLAIN_MESSAGE);
    }

    // ============================================================
    // HELPER — CREAR BOTÓN
    // ============================================================
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(UITheme.FONT_BOLD);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setPreferredSize(new Dimension(150, 38));
        return btn;
    }


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
