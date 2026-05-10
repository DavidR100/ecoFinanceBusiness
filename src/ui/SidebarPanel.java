/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author omar.rebolledo
 */
public class SidebarPanel extends javax.swing.JPanel {
    
    // === Button Nav
    String[][] navItems = {
        {"🏠", "Inicio",           "dashboard"},
        {"💰", "Finanzas",         "finanzas"},
        {"📦", "Inventario",       "inventario"},
        {"📊", "Desperdicio",      "desperdicio"},
        {"♻️", "Sostenibilidad 3R","sostenibilidad"},
        {"📋", "Reporte General",  "reporte"}
    };
    
    private NavigationListener listener;

    /**
     * Creates new form Sidebar
     */
    public SidebarPanel(NavigationListener listener) {
        this.listener = listener;
        initComponents();
        build();
    }
    
    
    public interface NavigationListener {
        void onNavigate(String route);
    }
/*
    public SidebarPanel(NavigationListener listener) {
        buildUI(listener);
    }*/
    
    private void build(){
        /*
        BoxLayout organiza los componentes en una sola dirección:
        BoxLayout.Y_AXIS → vertical (de arriba hacia abajo)
        BoxLayout.X_AXIS → horizontal (de izquierda a derecha)*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //add(new Button("Nuevo boton"));
        setBackground(UITheme.VERDE_OSCURO);
        setPreferredSize(new Dimension(200, 0)); // El cero no significa que mida 0, solo se ajuta al Layout
        setBorder(new EmptyBorder(0, 0, 0, 0)); // === (top, left, bottom, right)
        
        builLogo();
        
        add(separador());
        
        add(Box.createVerticalStrut(12));
        
        ButtonGroup btnGroup = new ButtonGroup();
        
        for (String[] navItem : navItems) {
            JToggleButton btn = buildNavButton(navItem[0], navItem[1], navItem[2]);
            btnGroup.add(btn);
            add(btn);
            add(Box.createVerticalStrut(4));
            if(navItem[2].equals("dashboard")) btn.setSelected(true);
        }
        
        add(Box.createVerticalGlue());
        
        
   
    }
    
    private void builLogo(){
        
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(UITheme.VERDE_OSCURO);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(new EmptyBorder(24, 20, 20, 20));
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // ICONO
        JLabel logoIcon = new JLabel("🌿");
        logoIcon.setFont(UITheme.FONT_EMOJI);
        logoIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // LABEL
        JLabel logoTitle = new JLabel("EcoFinance");
        logoTitle.setFont(UITheme.FONT_TITLE);
        logoTitle.setForeground(Color.WHITE);
        logoTitle.setAlignmentX((Component.LEFT_ALIGNMENT));
        
        // LABEL VERSION
        JLabel logoVersion = new JLabel("Business V1");
        logoVersion.setFont(UITheme.FONT_SMALL);
        logoVersion.setForeground(UITheme.VERDE_CLARO);
        logoVersion.setAlignmentX((Component.LEFT_ALIGNMENT));
        
        // === ADD
        logoPanel.add(logoIcon);
        logoPanel.add(Box.createVerticalStrut(8));
        logoPanel.add(logoTitle);
        logoPanel.add(logoVersion);
        add(logoPanel);   
    }
    
    private JSeparator separador(){
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 30));
        sep.setMaximumSize(new Dimension(200, 1));
        
        return sep;
    }
   
    private JToggleButton buildNavButton(String icon, String label, String panelName){
        
        JToggleButton btn = new JToggleButton(icon + " " + label){
           @Override
           protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if(isSelected()){
                    g2.setColor(UITheme.VERDE_MEDIO);
                    
                } else if(getModel().isRollover()){ // Indica que el ratón está sobre el botón. verdadero si el ratón está sobre el botón
                    g2.setColor(new Color(255, 255, 255, 15));
                }
                super.paintComponent(g);
            }
        };
        
        btn.setFont(UITheme.FONT_BODY);
        btn.setForeground(Color.WHITE);
        //btn.setBackground(new Color(0, 0, 0, 0));
        btn.setContentAreaFilled(false); // No pintamos el fondo por defecto.
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 16, 10, 16));
        btn.setMaximumSize(new Dimension(196, 42));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            if(e != null){
                listener.onNavigate(panelName);
            }
        });
        
        return btn;
    }
    
    private void showPanel(String name){
        
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
