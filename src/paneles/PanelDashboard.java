/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import principal.AppState;
import principal.EFBPrincipal;
import ui.EcoBorders;
import ui.RoundedBorder;
import ui.UITheme;

/**
 *
 * @author omar.rebolledo
 */
public class PanelDashboard extends javax.swing.JPanel {
    
    private EFBPrincipal app;

    private JLabel lblSaldo2, lblIngresos, lblGastos, lblRegistros;
    
       // Chips de estadísticas (ingresos, gastos, registros)
    private JLabel chipValIngresos;
    private JLabel chipValGastos;
    private JLabel chipValRegistros;
    
     // Labels que se actualizan en refresh()
    private JLabel lblSaldo;
    private JLabel lblCO2;
    private JLabel lblNivelCO2;
    private JLabel lblAcciones;
    private JPanel progressContainer;

    /**
     * Creates new form PanelDashboard
     */
    public PanelDashboard(EFBPrincipal app) {
        this.app = app;
        //initComponents();
        setBackground(UITheme.BG);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(24, 28, 24, 28));
        buildUI();
    }
    
        // ── Construcción de la interfaz ───────────────────────
    private void buildUI() {
        // Encabezado
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UITheme.INK);
        JLabel sub = new JLabel("Pizzería Sostenible · Vista general");
        sub.setFont(UITheme.FONT_SMALL);
        sub.setForeground(UITheme.MUTED);
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(sub);
        top.add(titleBox, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);
 
        // Grid central de cards
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(18, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.BOTH;
 
        // Card de saldo
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; gbc.weightx = 2; gbc.weighty = 1;
        center.add(buildCardSaldo(), gbc);
 
        // Card de CO₂
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.gridwidth = 1; gbc.weightx = 1; gbc.weighty = 1;
        center.add(buildCardCO2(), gbc);
 
        // Cards de módulos (fila 2)
        String[][] mods = {
            {"💰", "Finanzas",       "Ingresos y gastos",   "finanzas"},
            {"📦", "Inventario",     "Control de stock",    "inventario"},
            {"📊", "Desperdicio",    "Análisis sobrantes",  "desperdicio"},
            {"♻️", "3R Sostenible",  "Impacto ambiental",   "sostenibilidad"},
            {"📋", "Reporte",        "Visión integrada",    "reporte"},
        };
        Color[] modColors = {
            UITheme.VERDE_MEDIO, UITheme.BLUE,
            UITheme.AMBER, UITheme.CORAL, new Color(0x6B3A8A)
        };
 
        for (int i = 0; i < mods.length; i++) {
            final String panelName = mods[i][3];
            JPanel card = buildModuleCard(
                mods[i][0], mods[i][1], mods[i][2], modColors[i]
            );
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    app.showPanel(panelName);
                }
                public void mouseEntered(MouseEvent e) {
                    card.setBorder(new EmptyBorder(13, 15, 13, 15));
                }
                public void mouseExited(MouseEvent e) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                        new EcoBorders.Bottom(modColors[0], 3),
                        new EmptyBorder(12, 14, 12, 14)
                    ));
                }
            });
            gbc.gridx = i; gbc.gridy = 1;
            gbc.gridwidth = 1; gbc.weightx = 1; gbc.weighty = 0.8;
            center.add(card, gbc);
        }
 
        add(center, BorderLayout.CENTER);
    }
 
    // ── Card: Saldo principal ─────────────────────────────
    private JPanel buildCardSaldo() {
        JPanel card = UITheme.card(new GridBagLayout());
        card.setBackground(UITheme.VERDE_OSCURO);
        card.setOpaque(true);
        card.setBorder(new EmptyBorder(20, 24, 20, 24));
 
        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
 
        JLabel lbl = new JLabel("SALDO ACTUAL");
        lbl.setFont(UITheme.FONT_TINY);
        lbl.setForeground(UITheme.MENTA);
 
        lblSaldo = new JLabel(AppState.FMT_MONEY.format(0));
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblSaldo.setForeground(Color.WHITE);
 
        JPanel statsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        statsRow.setOpaque(false);
 
        // Chips de estadísticas
        JPanel chipIngresos  = buildStatChip("Ingresos",  "$10.00",   UITheme.VERDE_CLARO);
        JPanel chipGastos    = buildStatChip("Gastos",    "$0.00",   UITheme.CORAL);
        JPanel chipRegistros = buildStatChip("Registros", "0",       Color.WHITE);
 
        // Guardamos referencia al label de valor (2° componente de cada chip)
        chipValIngresos  = (JLabel) chipIngresos.getComponent(1);
        chipValGastos    = (JLabel) chipGastos.getComponent(1);
        chipValRegistros = (JLabel) chipRegistros.getComponent(1);
 
        statsRow.add(chipIngresos);
        statsRow.add(chipGastos);
        statsRow.add(chipRegistros);
 
        inner.add(lbl);
        inner.add(Box.createVerticalStrut(6));
        inner.add(lblSaldo);
        inner.add(Box.createVerticalStrut(14));
        inner.add(statsRow);
 
        card.add(inner);
        return card;
    }
 
    private JPanel buildStatChip(String label, String value, Color valueColor) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(255, 255, 255, 20));
        p.setBorder(new CompoundBorder(
            new EcoBorders.Rounded(8, new Color(255, 255, 255, 30)),
            new EmptyBorder(6, 12, 6, 12)
        ));
        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.FONT_TINY);
        lbl.setForeground(UITheme.MENTA);
        JLabel val = new JLabel(value);
        val.setFont(UITheme.FONT_BOLD);
        val.setForeground(valueColor);
        p.add(lbl);
        p.add(val);
        return p;
    }
 
    // ── Card: Impacto CO₂ ─────────────────────────────────
    private JPanel buildCardCO2() {
        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
 
        JLabel ico = new JLabel("🌱");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
 
        JLabel title = UITheme.sectionTitle("Impacto ambiental");
 
        lblCO2 = UITheme.heroAmount("0.000 kg", UITheme.VERDE_MEDIO);
        lblCO2.setFont(new Font("Segoe UI", Font.BOLD, 18));
 
        JLabel co2Sub = new JLabel("CO₂ evitado en sesión");
        co2Sub.setFont(UITheme.FONT_SMALL);
        co2Sub.setForeground(UITheme.MUTED);
 
        progressContainer = new JPanel(new BorderLayout());
        progressContainer.setOpaque(false);
        progressContainer.setPreferredSize(new Dimension(0, 10));
 
        lblNivelCO2 = new JLabel("SIN ACCIONES AÚN");
        lblNivelCO2.setFont(UITheme.FONT_BOLD);
        lblNivelCO2.setForeground(UITheme.VERDE_MEDIO);
 
        lblAcciones = new JLabel("0 acciones registradas");
        lblAcciones.setFont(UITheme.FONT_SMALL);
        lblAcciones.setForeground(UITheme.MUTED);
 
        card.add(ico);
        card.add(Box.createVerticalStrut(4));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(lblCO2);
        card.add(co2Sub);
        card.add(Box.createVerticalStrut(10));
        card.add(progressContainer);
        card.add(Box.createVerticalStrut(6));
        card.add(lblNivelCO2);
        card.add(Box.createVerticalStrut(4));
        card.add(lblAcciones);
        return card;
    }
 
    // ── Card: acceso rápido a módulo ──────────────────────
    private JPanel buildModuleCard(String icon, String title,
                                   String sub, Color accent) {
        JPanel card = UITheme.card(new BorderLayout(10, 0));
        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(UITheme.FONT_BOLD);
        titleLbl.setForeground(UITheme.INK);
        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(UITheme.FONT_SMALL);
        subLbl.setForeground(UITheme.MUTED);
        JPanel text = new JPanel(new GridLayout(2, 1, 0, 2));
        text.setOpaque(false);
        text.add(titleLbl);
        text.add(subLbl);
        card.setBorder(BorderFactory.createCompoundBorder(
            new EcoBorders.Bottom(accent, 3),
            new EmptyBorder(12, 14, 12, 14)
        ));
        card.add(ico,  BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        return card;
    }
 
    // ── Actualizar datos ──────────────────────────────────
    public void refresh() {
        double saldo = AppState.getSaldo();
        lblSaldo.setText(AppState.FMT_MONEY.format(saldo));
        lblSaldo.setForeground(saldo >= 0 ? UITheme.VERDE_CLARO : UITheme.CORAL);
 
        chipValIngresos.setText(AppState.FMT_MONEY.format(AppState.totalIngresos));
        chipValGastos.setText(AppState.FMT_MONEY.format(AppState.totalGastos));
        chipValRegistros.setText(String.valueOf(AppState.cantRegistros));
 
        lblCO2.setText(AppState.FMT_NUM.format(AppState.totalCO2Ahorrado) + " kg CO₂");
        lblNivelCO2.setText(AppState.getNivel());
        lblAcciones.setText(AppState.totalAccionesSostenibles + " acciones registradas");
 
        // Reconstruir barra de progreso CO₂
        progressContainer.removeAll();
        progressContainer.add(
            UITheme.progressBar(AppState.totalCO2Ahorrado, 10, UITheme.VERDE_CLARO),
            BorderLayout.CENTER
        );
 
        revalidate();
        repaint();
    }

    /*private void build() {
        add(buildPanelTitle(), BorderLayout.NORTH);
        add(contentCenter(), BorderLayout.CENTER);
    }

    private JPanel buildPanelTitle() {
        // === TITLE
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false); // === No pintar fondo

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UITheme.INK);

        // === SUB TITLE
        JLabel subTitle = new JLabel("Pizzería Sostenible · Vista general");
        subTitle.setFont(UITheme.FONT_SMALL);
        subTitle.setForeground(UITheme.MUTED);

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subTitle);

        top.add(titleBox, BorderLayout.WEST);

        return top;
    }

    private JPanel contentCenter() {

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(18, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;

        
                gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 2;
        gbc.weighty = 1;
        center.add(saldoPrincipal(gbc), gbc);
        return center;

    }

    // Card 1: Saldo principal
    private JPanel saldoPrincipal(GridBagConstraints gbc) {
        //JPanel center = contentCenter();
        JPanel cardSaldo = buildCardSaldo();

        //center.add(cardSaldo, gbc);
        
        return cardSaldo;
    }

    private JPanel buildCardSaldo() {
        JPanel card = UITheme.card(new GridBagLayout());
        card.setBackground(UITheme.VERDE_OSCURO);
        card.setOpaque(true);
        card.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("SALDO ACTUAL");
        lbl.setFont(UITheme.FONT_TINY);
        lbl.setForeground(UITheme.MENTA);

        lblSaldo = new JLabel(UITheme.FMT_MONEY.format(0));
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblSaldo.setForeground(Color.WHITE);

        JPanel statsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        statsRow.setOpaque(false);

        lblIngresos = statChip("Ingresos", "$0.00", UITheme.VERDE_CLARO);
        lblGastos = statChip("Gastos", "$0.00", UITheme.CORAL);
        lblRegistros = statChip("Registros", "0", Color.WHITE);

        statsRow.add(lblIngresos);
        statsRow.add(lblGastos);
        statsRow.add(lblRegistros);

        inner.add(lbl);
        inner.add(Box.createVerticalStrut(6));
        inner.add(lblSaldo);
        inner.add(Box.createVerticalStrut(14));
        inner.add(statsRow);

        card.add(inner);
        return card;
    }
    
    private JLabel statChip(String label, String value, Color valueColor) {
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBackground(new Color(255,255,255,20));
            p.setBorder(new CompoundBorder(
                new RoundedBorder(8, new Color(255,255,255,30)),
                new EmptyBorder(6,12,6,12)
            ));
            JLabel lbl = new JLabel(label);
            lbl.setFont(UITheme.FONT_TINY);
            lbl.setForeground(UITheme.MENTA);
            JLabel val = new JLabel(value);
            val.setFont(UITheme.FONT_BOLD);
            val.setForeground(valueColor);
            val.setName(label); // Para poder buscarlo luego
            p.add(lbl); p.add(val);
            return val; // RETIFICAR
        }
*/
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
