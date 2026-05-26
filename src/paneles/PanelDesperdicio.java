/*//GEN-LINE:variables
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import principal.AppState;
import principal.EFBPrincipal;
import ui.UITheme;

/**
 *
 * @author omar.rebolledo
 */
/**
 * PanelDesperdicio.java
 * ───────────────────────────────────────────────────────── Módulo 3 — Control
 * de Desperdicio. Calcula y muestra el % de sobrante por ingrediente, con
 * alertas visuales y recomendaciones de acción.
 * ─────────────────────────────────────────────────────────
 */
public class PanelDesperdicio extends javax.swing.JPanel {

    private final EFBPrincipal app;

    // Componentes dinámicos
    private JPanel cardsPanel;
    private JLabel lblTotal;
    private JLabel lblRecomendacion;
    private JPanel progressContainer;

    /**
     * Creates new form PanelDashboard
     */
    // ── Constructor ───────────────────────────────────────
    public PanelDesperdicio(EFBPrincipal app) {
        this.app = app;
        setBackground(UITheme.BG);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(24, 28, 24, 28));
        buildUI();
    }

    // ── Construcción de la interfaz ───────────────────────
    private void buildUI() {
        // Encabezado con botón actualizar
        JLabel title = new JLabel("📊  Módulo 3 — Control de Desperdicio");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UITheme.INK);

        JButton btnRefresh = UITheme.primaryButton("🔄 Actualizar");
        btnRefresh.addActionListener(e -> refresh());

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.add(title, BorderLayout.WEST);
        topRow.add(btnRefresh, BorderLayout.EAST);
        add(topRow, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(18, 0, 0, 0));

        // Card resumen total
        center.add(buildResumenCard(), BorderLayout.NORTH);

        // Grid de tarjetas por ingrediente
        cardsPanel = new JPanel(new GridLayout(0, 3, 12, 12));
        cardsPanel.setOpaque(false);
        JScrollPane scroll = new JScrollPane(cardsPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        center.add(scroll, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    // ── Card de resumen general ───────────────────────────
    private JPanel buildResumenCard() {
        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(4, 8, 4, 8);

        lblTotal = new JLabel("0.0%");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTotal.setForeground(UITheme.VERDE_MEDIO);

        progressContainer = new JPanel(new BorderLayout());
        progressContainer.setOpaque(false);
        progressContainer.setPreferredSize(new Dimension(0, 14));

        lblRecomendacion = new JLabel(
                "Sin datos. Agregue ingredientes en el módulo de Inventario.");
        lblRecomendacion.setFont(UITheme.FONT_BODY);
        lblRecomendacion.setForeground(UITheme.MUTED);

        JPanel col = new JPanel(new GridLayout(4, 1, 0, 6));
        col.setOpaque(false);
        col.add(UITheme.sectionTitle("Desperdicio total de ingredientes"));
        col.add(lblTotal);
        col.add(progressContainer);
        col.add(lblRecomendacion);

        g.gridx = 0;
        g.gridy = 0;
        g.weightx = 1;
        g.weighty = 1;
        card.add(col, g);
        return card;
    }

    // ── Actualizar datos desde AppState ──────────────────
    public void refresh() {
        cardsPanel.removeAll();
        progressContainer.removeAll();

        if (AppState.numIngredientes == 0) {
            lblTotal.setText("—");
            lblRecomendacion.setText(
                    "Sin datos. Agregue ingredientes en Inventario.");
            cardsPanel.revalidate();
            cardsPanel.repaint();
            return;
        }

        double totalDesp = 0, totalDisp = 0;
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double sobr = AppState.cantDisponible[i] - AppState.cantUsada[i];
            double pDesp = AppState.cantDisponible[i] > 0
                    ? (sobr / AppState.cantDisponible[i]) * 100 : 0;
            totalDesp += sobr;
            totalDisp += AppState.cantDisponible[i];
            cardsPanel.add(buildIngCard(
                    AppState.nomIngredientes[i],
                    AppState.cantDisponible[i],
                    AppState.cantUsada[i],
                    sobr, pDesp
            ));
        }

        // Porcentaje total y color de semáforo
        double pTotal = totalDisp > 0 ? (totalDesp / totalDisp) * 100 : 0;
        Color barColor = pTotal > 30 ? UITheme.CORAL
                : pTotal > 15 ? UITheme.AMBER
                        : UITheme.VERDE_CLARO;

        lblTotal.setText(String.format("%.1f%%", pTotal));
        lblTotal.setForeground(barColor);

        progressContainer.add(
                UITheme.progressBar(pTotal, 100, barColor),
                BorderLayout.CENTER
        );

        if (pTotal <= 10) {
            lblRecomendacion.setText(
                    "✔ Excelente gestión. Desperdicio bajo control.");
        } else if (pTotal <= 25) {
            lblRecomendacion.setText(
                    "⚠ Desperdicio moderado. Lleve sobrantes al módulo 3R.");
        } else {
            lblRecomendacion.setText(
                    "✘ Desperdicio ALTO. Ajuste compras y procese excedentes en módulo 3R.");
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
        revalidate();
        repaint();
    }

    // ── Tarjeta individual por ingrediente ───────────────
    private JPanel buildIngCard(String nom, double disp,
            double usado, double sobr,
            double pDesp) {
        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        Color accent = pDesp > 30 ? UITheme.CORAL
                : pDesp > 15 ? UITheme.AMBER
                        : UITheme.VERDE_MEDIO;

        JLabel nameLbl = new JLabel(nom);
        nameLbl.setFont(UITheme.FONT_BOLD);
        nameLbl.setForeground(UITheme.INK);

        JLabel pctLbl = new JLabel(String.format("%.1f%% sobrante", pDesp));
        pctLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pctLbl.setForeground(accent);

        JPanel bar = UITheme.progressBar(pDesp, 100, accent);

        JLabel dispLbl = new JLabel("Disponible: " + AppState.FMT_NUM.format(disp));
        JLabel usadLbl = new JLabel("Usado:      " + AppState.FMT_NUM.format(usado));
        JLabel sobrLbl = new JLabel("Sobrante:   " + AppState.FMT_NUM.format(sobr));
        for (JLabel l : new JLabel[]{dispLbl, usadLbl, sobrLbl}) {
            l.setFont(UITheme.FONT_SMALL);
            l.setForeground(UITheme.MUTED);
        }

        card.add(nameLbl);
        card.add(Box.createVerticalStrut(4));
        card.add(pctLbl);
        card.add(Box.createVerticalStrut(6));
        card.add(bar);
        card.add(Box.createVerticalStrut(8));
        card.add(dispLbl);
        card.add(usadLbl);
        card.add(sobrLbl);
        return card;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
