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
import principal.AppState;
import principal.EFBPrincipal;
import ui.UITheme;

/**
 *
 * @author omar.rebolledo
 */
public class PanelDesperdicio2 extends javax.swing.JPanel {

      private final EFBPrincipal app;
 
    // ── Banner de totales (kg) ────────────────────────────
    private JLabel  lblTotalKg;
    private JLabel  lblMensaje;
    private JPanel  barraFillTotal;
 
    // ── Resumen de porcentaje global ──────────────────────
    private JLabel  lblPctTotal;
    private JLabel  lblRecomendacion;
    private JPanel  progressContainer;
 
    // ── Grid de tarjetas por ingrediente ─────────────────
    private JPanel  gridCards;
 
    // ── Constructor ───────────────────────────────────────
    public PanelDesperdicio2(EFBPrincipal app) {
        this.app = app;
        initComponents();
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
    // TÍTULO + BOTONES
    // ============================================================
    private JPanel buildTitle() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 20, 0));
 
        // Título y subtítulo
        JLabel title = new JLabel("📊  Módulo 3 — Control de Desperdicio");
        title.setFont(UITheme.FONT_EMOJI_BOLD_NORMAL);
        title.setForeground(UITheme.INK);
 
        JLabel subTitle = new JLabel(
            "Análisis automático de sobrantes por ingrediente (desde Inventario)");
        subTitle.setFont(UITheme.FONT_SMALL);
        subTitle.setForeground(UITheme.MUTED);
 
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subTitle);
 
        // Botones a la derecha
        JButton btnActualizar = crearBoton("🔄  Actualizar", UITheme.VERDE_PRINCIPAL);
        btnActualizar.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnActualizar.setBackground(UITheme.VERDE_MEDIO); }
            @Override public void mouseExited(MouseEvent e)  { btnActualizar.setBackground(UITheme.VERDE_PRINCIPAL); }
        });
        btnActualizar.addActionListener(e -> refresh());
 
        JButton btnResumen = crearBoton("📋  Ver Resumen", UITheme.BLUE);
        btnResumen.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnResumen.setBackground(new Color(0x155A8A)); }
            @Override public void mouseExited(MouseEvent e)  { btnResumen.setBackground(UITheme.BLUE); }
        });
        btnResumen.addActionListener(e -> mostrarResumen());
 
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botonesPanel.setOpaque(false);
        botonesPanel.add(btnActualizar);
        botonesPanel.add(btnResumen);
 
        top.add(titleBox,    BorderLayout.WEST);
        top.add(botonesPanel,BorderLayout.EAST);
        return top;
    }
 
    // ============================================================
    // CONTENIDO PRINCIPAL
    // ============================================================
    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);
 
        // Fila superior: banner kg  +  card % global (lado a lado)
        JPanel filaTop = new JPanel(new GridLayout(1, 2, 14, 0));
        filaTop.setOpaque(false);
        filaTop.add(buildBannerTotal());
        filaTop.add(buildResumenPct());
        content.add(filaTop, BorderLayout.NORTH);
 
        // Tarjetas por ingrediente
        content.add(buildGrid(), BorderLayout.CENTER);
 
        return content;
    }
 
    // ============================================================
    // BANNER — KG TOTALES SOBRANTES  (UI del Panel 1)
    // ============================================================
    private JPanel buildBannerTotal() {
        JPanel banner = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.CARD);
                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
            }
        };
        banner.setOpaque(false);
        banner.setBorder(new EmptyBorder(16, 20, 16, 20));
 
        JLabel lblEtiqueta = new JLabel("SOBRANTE TOTAL (kg)");
        lblEtiqueta.setFont(UITheme.FONT_TINY);
        lblEtiqueta.setForeground(UITheme.MUTED);
 
        lblTotalKg = new JLabel("0.000 kg");
        lblTotalKg.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotalKg.setForeground(UITheme.VERDE_PRINCIPAL);
 
        // Barra animada de fondo
        JPanel barraFondo = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.LINE);
                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
            }
        };
        barraFondo.setOpaque(false);
        barraFondo.setPreferredSize(new Dimension(0, 10));
 
        barraFillTotal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
            }
        };
        barraFillTotal.setOpaque(false);
        barraFillTotal.setBackground(UITheme.VERDE_PRINCIPAL);
        barraFillTotal.setPreferredSize(new Dimension(0, 10));
        barraFondo.add(barraFillTotal, BorderLayout.WEST);
 
        lblMensaje = new JLabel("Sin datos. Agregue ingredientes en Inventario.");
        lblMensaje.setFont(UITheme.FONT_SMALL);
        lblMensaje.setForeground(UITheme.MUTED);
 
        JPanel inner = new JPanel(new BorderLayout(0, 6));
        inner.setOpaque(false);
        inner.add(lblEtiqueta,  BorderLayout.NORTH);
        inner.add(lblTotalKg,   BorderLayout.CENTER);
        inner.add(barraFondo,   BorderLayout.SOUTH);
 
        banner.add(inner,      BorderLayout.NORTH);
        banner.add(lblMensaje, BorderLayout.CENTER);
        return banner;
    }
 
    // ============================================================
    // CARD — % GLOBAL DE DESPERDICIO  (del Panel 2)
    // ============================================================
    private JPanel buildResumenPct() {
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.CARD);
                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
 
        JLabel lblEtiqueta = new JLabel("% DESPERDICIO GLOBAL");
        lblEtiqueta.setFont(UITheme.FONT_TINY);
        lblEtiqueta.setForeground(UITheme.MUTED);
 
        lblPctTotal = new JLabel("— %");
        lblPctTotal.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblPctTotal.setForeground(UITheme.VERDE_MEDIO);
 
        progressContainer = new JPanel(new BorderLayout());
        progressContainer.setOpaque(false);
        progressContainer.setPreferredSize(new Dimension(0, 10));
 
        lblRecomendacion = new JLabel("Actualiza para ver el análisis.");
        lblRecomendacion.setFont(UITheme.FONT_SMALL);
        lblRecomendacion.setForeground(UITheme.MUTED);
 
        JPanel inner = new JPanel(new BorderLayout(0, 6));
        inner.setOpaque(false);
        inner.add(lblEtiqueta,       BorderLayout.NORTH);
        inner.add(lblPctTotal,       BorderLayout.CENTER);
        inner.add(progressContainer, BorderLayout.SOUTH);
 
        card.add(inner,           BorderLayout.NORTH);
        card.add(lblRecomendacion,BorderLayout.CENTER);
        return card;
    }
 
    // ============================================================
    // GRID DE TARJETAS
    // ============================================================
    private JPanel buildGrid() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
 
        gridCards = new JPanel(new GridLayout(0, 3, 12, 12));
        gridCards.setOpaque(false);
 
        JScrollPane scroll = new JScrollPane(gridCards);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
 
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }
 
    // ============================================================
    // REFRESH — Lee AppState y actualiza toda la UI
    // ============================================================
    public void refresh() {
        gridCards.removeAll();
        progressContainer.removeAll();
 
        if (AppState.numIngredientes == 0) {
            lblPctTotal.setText("— %");
            lblPctTotal.setForeground(UITheme.MUTED);
            lblTotalKg.setText("0.000 kg");
            lblTotalKg.setForeground(UITheme.MUTED);
            lblMensaje.setText("Sin datos. Agregue ingredientes en Inventario.");
            lblMensaje.setForeground(UITheme.MUTED);
            lblRecomendacion.setText("Sin datos. Vaya al módulo de Inventario.");
            gridCards.revalidate();
            gridCards.repaint();
            return;
        }
 
        // ── Calcular totales desde AppState ──────────────
        double totalSobranteKg = 0;
        double totalDispKg     = 0;
 
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double sobr  = AppState.cantDisponible[i] > AppState.cantUsada[i] ? 
                    AppState.cantDisponible[i] - AppState.cantUsada[i] : AppState.cantDisponible[i];
            System.out.println("cantDisponible" +  AppState.cantDisponible[i]);
            System.out.println("cantUsada" +  AppState.cantUsada[i]);
            System.out.println("cantDisponible" +  sobr);
            double pDesp = AppState.cantDisponible[i] > 0
                ? (sobr / AppState.cantDisponible[i]) * 100 : 0;
 
            totalSobranteKg += Math.max(sobr, 0); // solo sobrantes positivos
            totalDispKg     += AppState.cantDisponible[i];
 
            // Tarjeta individual con % y barra (del Panel 2)
            gridCards.add(buildIngCard(
                AppState.nomIngredientes[i],
                AppState.cantDisponible[i],
                AppState.cantUsada[i],
                Math.max(sobr, 0),
                pDesp
            ));
        }
 
        // ── % global ──────────────────────────────────────
        double pTotal  = totalDispKg > 0
            ? (totalSobranteKg / totalDispKg) * 100 : 0;
 
        Color barColor = pTotal > 30 ? UITheme.CORAL
                       : pTotal > 15 ? UITheme.AMBER
                       : UITheme.VERDE_CLARO;
 
        // Actualizar % global card
        lblPctTotal.setText(String.format("%.1f%%", pTotal));
        lblPctTotal.setForeground(barColor);
        progressContainer.add(
            UITheme.progressBar(pTotal, 100, barColor),
            BorderLayout.CENTER
        );
 
        // Actualizar recomendación automática
        if (pTotal <= 10) {
            lblRecomendacion.setText("✔ Excelente gestión. Desperdicio bajo control.");
            lblRecomendacion.setForeground(UITheme.GREEN_VAL);
        } else if (pTotal <= 25) {
            lblRecomendacion.setText("⚠ Desperdicio moderado. Lleve sobrantes al módulo 3R.");
            lblRecomendacion.setForeground(UITheme.AMBER);
        } else {
            lblRecomendacion.setText("✘ Desperdicio ALTO. Ajuste compras y vaya al módulo 3R.");
            lblRecomendacion.setForeground(UITheme.CORAL);
        }
 
        // ── Actualizar banner de kg totales (UI del Panel 1) ──
        actualizarBannerKg(totalSobranteKg, pTotal, barColor);
 
        gridCards.revalidate();
        gridCards.repaint();
        revalidate();
        repaint();
    }
 
    // ── Actualiza el banner de kg con barra animada ───────
    private void actualizarBannerKg(double totalKg, double pTotal, Color color) {
        String nivel;
        String mensaje;
 
        if (totalKg == 0) {
            nivel   = "";
            mensaje = "✓ Sin sobrantes. ¡Uso perfecto del inventario!";
            color   = UITheme.VERDE_PRINCIPAL;
        } else if (pTotal <= 10) {
            nivel   = "  — ACEPTABLE ✓";
            mensaje = "✓ Sobrante bajo control. ¡Buen trabajo!";
        } else if (pTotal <= 25) {
            nivel   = "  — MODERADO  !";
            mensaje = "! Sobrante moderado. Ajuste porciones o el menú del día.";
        } else {
            nivel   = "  — CRÍTICO  ⚠";
            mensaje = "⚠ Sobrante ALTO. Procese excedentes en el módulo 3R.";
        }
 
        lblTotalKg.setText(String.format("%.3f kg%s", totalKg, nivel));
        lblTotalKg.setForeground(color);
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(color);
        barraFillTotal.setBackground(color);
 
        // Barra animada proporcional al umbral crítico (25 kg = 100%)
        final Color finalColor = color;
        SwingUtilities.invokeLater(() -> {
            JPanel parent = (JPanel) barraFillTotal.getParent();
            if (parent != null) {
                int ancho = parent.getWidth();
                double pct = Math.min(totalKg / 25.0, 1.0);
                barraFillTotal.setPreferredSize(
                    new Dimension((int)(ancho * pct), 10));
                parent.revalidate();
                parent.repaint();
            }
        });
    }
 
    // ============================================================
    // TARJETA INDIVIDUAL POR INGREDIENTE  (fusión Panel1 + Panel2)
    // ============================================================
    private JPanel buildIngCard(String nom, double dispActual,
                                double usado, double sobr, double pDesp) {
 
        // Color semáforo según % de sobrante
        Color accent = pDesp > 30 ? UITheme.CORAL
                     : pDesp > 15 ? UITheme.AMBER
                     : UITheme.VERDE_MEDIO;
 
        String estadoTexto;
        if      (pDesp > 30) estadoTexto = "⚠ Nivel crítico";
        else if (pDesp > 15) estadoTexto = "! Nivel moderado";
        else                 estadoTexto = "✓ Bajo control";
 
        // Card con bordes redondeados (del Panel 1)
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.CARD);
                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(14, 16, 14, 16));
 
        // Nombre del ingrediente
        JLabel lblNombre = new JLabel(nom);
        lblNombre.setFont(UITheme.FONT_BOLD);
        lblNombre.setForeground(UITheme.INK);
 
        // % sobrante grande (del Panel 2)
        JLabel lblPct = new JLabel(String.format("%.1f%% sobrante", pDesp));
        lblPct.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPct.setForeground(accent);
 
        // Kg sobrante (del Panel 1)
        JLabel lblKgSobr = new JLabel(String.format("%.3f kg/unidades sin usar", sobr));
        lblKgSobr.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblKgSobr.setForeground(accent);
 
        // Barra de progreso del % (del Panel 2)
        JPanel bar = UITheme.progressBar(pDesp, 100, accent);
 
        // Estado textual (del Panel 1)
        JLabel lblEstado = new JLabel(estadoTexto);
        lblEstado.setFont(UITheme.FONT_SMALL);
        lblEstado.setForeground(accent);
 
        // Datos numéricos detallados (del Panel 2)
        double totalOriginal = dispActual + usado;
        JLabel lblTotal = new JLabel("Total original:  " +
            AppState.FMT_NUM.format(totalOriginal));
        JLabel lblUsado = new JLabel("Usado:           " +
            AppState.FMT_NUM.format(usado));
        JLabel lblDisp  = new JLabel("Disponible:      " +
            AppState.FMT_NUM.format(dispActual));
        JLabel lblSobr  = new JLabel("Sobrante:        " +
            AppState.FMT_NUM.format(sobr));
 
        for (JLabel l : new JLabel[]{lblTotal, lblUsado, lblDisp, lblSobr}) {
            l.setFont(UITheme.FONT_SMALL);
            l.setForeground(UITheme.MUTED);
        }
 
        // Panel central con datos
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(lblPct);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblKgSobr);
        centro.add(Box.createVerticalStrut(6));
        centro.add(bar);
        centro.add(Box.createVerticalStrut(4));
        centro.add(lblEstado);
        centro.add(Box.createVerticalStrut(8));
        centro.add(lblTotal);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblUsado);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblDisp);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblSobr);
 
        card.add(lblNombre, BorderLayout.NORTH);
        card.add(centro,    BorderLayout.CENTER);
        return card;
    }
 
    // ============================================================
    // BOTÓN VER RESUMEN CON RECOMENDACIONES  (del Panel 1)
    // ============================================================
    private void mostrarResumen() {
        if (AppState.numIngredientes == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay ingredientes en el inventario.\n"
                + "Agregue ingredientes en el módulo de Inventario primero.",
                "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
 
        // Calcular totales
        double totalSobr = 0, totalDisp = 0, totalUsado = 0;
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double sobr = Math.max(AppState.cantDisponible[i]
                                 - AppState.cantUsada[i], 0);
            totalSobr  += sobr;
            totalDisp  += AppState.cantDisponible[i];
            totalUsado += AppState.cantUsada[i];
        }
        double pTotal = totalDisp > 0 ? (totalSobr / totalDisp) * 100 : 0;
 
        // Construir texto del resumen
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════\n");
        sb.append("       RESUMEN DE DESPERDICIO\n");
        sb.append("       EcoFinance Business\n");
        sb.append("══════════════════════════════════════════\n\n");
 
        sb.append(String.format("  %-22s %10s %10s %10s  %s%n",
            "INGREDIENTE", "TOTAL", "USADO", "SOBRANTE", "ESTADO"));
        sb.append("  " + "─".repeat(66) + "\n");
 
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double disp  = AppState.cantDisponible[i];
            double usado = AppState.cantUsada[i];
            double sobr  = Math.max(disp - usado, 0);
            double orig  = disp + usado;
            double pct   = orig > 0 ? (sobr / orig) * 100 : 0;
            String est   = pct > 30 ? "ALTO"
                         : pct > 15 ? "MEDIO" : "OK";
            sb.append(String.format("  %-22s %10.3f %10.3f %10.3f  [%s %.0f%%]%n",
                AppState.nomIngredientes[i], orig, usado, sobr, est, pct));
        }
 
        sb.append("  " + "─".repeat(66) + "\n");
        sb.append(String.format("  %-22s %10.3f %10.3f %10.3f  [%.1f%%]%n%n",
            "TOTAL", totalDisp + totalUsado, totalUsado, totalSobr, pTotal));
 
        // Nivel y recomendaciones
        String nivel;
        String reco;
        if (pTotal <= 10) {
            nivel = "✔  OK — ACEPTABLE";
            reco  = "  * Excelente gestión. Desperdicio bajo control.\n"
                  + "  * Continúe monitoreando porciones diariamente.\n"
                  + "  * Mantenga el registro para detectar tendencias.";
        } else if (pTotal <= 25) {
            nivel = "!  MODERADO";
            reco  = "  * Desperdicio moderado. Ajuste el tamaño de porciones.\n"
                  + "  * Identifique los ingredientes con más sobrante.\n"
                  + "  * Reutilice sobrantes en el menú del día.\n"
                  + "  * Lleve los excedentes al módulo de Sostenibilidad 3R.";
        } else {
            nivel = "⚠  CRÍTICO — Acción inmediata necesaria";
            reco  = "  * Reduzca las compras del ingrediente con más sobrante.\n"
                  + "  * Ajuste el menú para aprovechar existencias actuales.\n"
                  + "  * Procese excedentes en el módulo de Sostenibilidad 3R.\n"
                  + "  * Capacite al equipo en control de porciones.\n"
                  + "  * Evalúe cambiar proveedores o cantidades de pedido.";
        }
 
        sb.append("  Nivel: ").append(nivel).append("\n\n");
        sb.append("  RECOMENDACIONES:\n").append(reco);
        sb.append("\n\n══════════════════════════════════════════");
 
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(UITheme.BG);
        textArea.setForeground(UITheme.INK);
        textArea.setBorder(new EmptyBorder(8, 8, 8, 8));
 
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(560, 380));
        scroll.setBorder(null);
 
        JOptionPane.showMessageDialog(this, scroll,
            "Resumen de Desperdicio", JOptionPane.PLAIN_MESSAGE);
    }
 
    // ============================================================
    // HELPER — CREAR BOTÓN REDONDEADO  (del Panel 1)
    // ============================================================
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 10, 10));
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
        btn.setPreferredSize(new Dimension(160, 38));
        return btn;
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
