/*//GEN-LINE:variables
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
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import ui.UITheme;

/**
 *
 * @author omar.rebolledo
 */
public class PanelSostenibilidad extends javax.swing.JPanel {
    
        // === FACTORES CO2 ===
    private static final double CO2_PLATO   = 0.050;
    private static final double CO2_PORCION = 0.300;
    private static final double CO2_ACEITE  = 0.800;
    private static final double CO2_CARTON  = 0.200;
    private static final double CO2_ORGANICO= 0.150;
    private static final double CO2_ENVASE  = 0.100;
    
    // === CAMPOS DE CANTIDAD ===
    private JTextField txtPlatos, txtPorciones, txtAceite, txtCarton, txtOrganico, txtEnvases;
 
    // === INDICADOR DERECHO ===
    private JLabel lblCO2Total, lblNivel, lblAcciones;
    private JLabel lblInsigniaTitulo, lblInsigniaDesc;
    private JPanel panelInsignia, barraFill;
    private JLabel lblArboles, lblKm, lblBolsas;
 
    private double totalCO2    = 0;
    private int    totalAcciones = 0;

    /**
     * Creates new form PanelDashboard
     */
    public PanelSostenibilidad() {
        initComponents();
        setBackground(UITheme.BG);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(24, 28, 24, 28));
        build();
    }
    
    private void build(){
        add(buildPanelTitle(), BorderLayout.NORTH);
         add(buildContent(), BorderLayout.CENTER);
    }
    
    // ============================================================
    // TÍTULO
    // ============================================================
    
    private JPanel buildPanelTitle(){
         // === TITLE
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false); // === No pintar fondo
        
        JLabel title = new JLabel("Sostenibilidad");  
        title.setFont(UITheme.FONT_EMOJI_BOLD_NORMAL);
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
    
     private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setOpaque(false);
 
        content.add(buildAcciones(),   BorderLayout.CENTER);
        content.add(buildIndicador(),  BorderLayout.EAST);
 
        return content;
    }
     
     // ============================================================
    // PANEL IZQUIERDO — 6 FILAS DE ACCIONES
    // ============================================================
    private JPanel buildAcciones() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 0, 10));
        panel.setOpaque(false);
 
        txtPlatos   = new JTextField("0", 6);
        txtPorciones= new JTextField("0", 6);
        txtAceite   = new JTextField("0", 6);
        txtCarton   = new JTextField("0", 6);
        txtOrganico = new JTextField("0", 6);
        txtEnvases  = new JTextField("0", 6);
 
        panel.add(buildFilaAccion("🍽", "Reutilizar platos",   "0.050 kg CO₂ / unidad · Reutilización", txtPlatos,    CO2_PLATO));
        panel.add(buildFilaAccion("🥗", "Reusar porciones",    "0.300 kg CO₂ / 100g · Reúso",           txtPorciones, CO2_PORCION));
        panel.add(buildFilaAccion("🛢", "Reciclar aceite",     "0.800 kg CO₂ / litro · Recolección",    txtAceite,    CO2_ACEITE));
        panel.add(buildFilaAccion("📦", "Reciclar cartón",     "0.200 kg CO₂ / kg · Recolección",       txtCarton,    CO2_CARTON));
        panel.add(buildFilaAccion("🌿", "Compostar orgánicos", "0.150 kg CO₂ / kg · Recolección",       txtOrganico,  CO2_ORGANICO));
        panel.add(buildFilaAccion("🫙", "Reutilizar envases",  "0.100 kg CO₂ / unidad · Reutilización", txtEnvases,   CO2_ENVASE));
 
        return panel;
    }
 
    private JPanel buildFilaAccion(String emoji, String nombre, String desc, JTextField campo, double factor) {
        JPanel fila = new JPanel(new BorderLayout(12, 0));
        fila.setBackground(UITheme.CARD);
        fila.setBorder(new CompoundBorder(
            new LineBorder(UITheme.LINE, 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));
 
        // === EMOJI ===
        JLabel lblEmoji = new JLabel(emoji);
        lblEmoji.setFont(UITheme.FONT_EMOJI);
        lblEmoji.setBorder(new EmptyBorder(0, 0, 0, 8));
 
        // === TEXTO ===
        JPanel textoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textoPanel.setOpaque(false);
 
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(UITheme.FONT_BOLD);
        lblNombre.setForeground(UITheme.INK);
 
        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(UITheme.FONT_SMALL);
        lblDesc.setForeground(UITheme.MUTED);
 
        textoPanel.add(lblNombre);
        textoPanel.add(lblDesc);
 
        // === CAMPO + BOTÓN ===
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        inputPanel.setOpaque(false);
 
        campo.setFont(UITheme.FONT_BODY);
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setPreferredSize(new Dimension(80, 32));
        campo.setBorder(new CompoundBorder(
            new LineBorder(UITheme.LINE, 1, true),
            new EmptyBorder(4, 8, 4, 8)
        ));
 
        JButton btnOK = new JButton("OK");
        btnOK.setFont(UITheme.FONT_BOLD);
        btnOK.setBackground(UITheme.VERDE_PRINCIPAL);
        btnOK.setForeground(Color.WHITE);
        btnOK.setFocusPainted(false);
        btnOK.setBorderPainted(false);
        btnOK.setPreferredSize(new Dimension(60, 32));
        btnOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 
        btnOK.addActionListener(e -> registrarAccion(campo, factor, nombre));
 
        // === HOVER BOTÓN ===
        btnOK.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnOK.setBackground(UITheme.VERDE_MEDIO); }
            @Override public void mouseExited(MouseEvent e)  { btnOK.setBackground(UITheme.VERDE_PRINCIPAL); }
        });
 
        inputPanel.add(campo);
        inputPanel.add(btnOK);
 
        fila.add(lblEmoji,    BorderLayout.WEST);
        fila.add(textoPanel,  BorderLayout.CENTER);
        fila.add(inputPanel,  BorderLayout.EAST);
 
        return fila;
    }
 
    // ============================================================
    // PANEL DERECHO — INDICADOR DE IMPACTO
    // ============================================================
    private JPanel buildIndicador() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.CARD);
        panel.setBorder(new CompoundBorder(
            new LineBorder(UITheme.LINE, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(300, 0));
 
        // === TÍTULO INDICADOR ===
        JLabel lblTitulo = new JLabel("Indicador de impacto");
        lblTitulo.setFont(UITheme.FONT_BOLD);
        lblTitulo.setForeground(UITheme.INK);
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);
 
        // === SUBTÍTULO ===
        JLabel lblSub = new JLabel("CO₂ EVITADO EN SESIÓN");
        lblSub.setFont(UITheme.FONT_TINY);
        lblSub.setForeground(UITheme.MUTED);
        lblSub.setAlignmentX(LEFT_ALIGNMENT);
        lblSub.setBorder(new EmptyBorder(10, 0, 4, 0));
 
        // === TOTAL CO2 ===
        lblCO2Total = new JLabel("0.000 kg CO₂");
        lblCO2Total.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblCO2Total.setForeground(UITheme.VERDE_PRINCIPAL);
        lblCO2Total.setAlignmentX(LEFT_ALIGNMENT);
 
        // === BARRA DE PROGRESO ===
        JPanel barraCont = new JPanel(new BorderLayout());
        barraCont.setOpaque(false);
        barraCont.setMaximumSize(new Dimension(Integer.MAX_VALUE, 12));
        barraCont.setAlignmentX(LEFT_ALIGNMENT);
        barraCont.setBorder(new EmptyBorder(10, 0, 10, 0));
 
        JPanel barraFondo = new JPanel(new BorderLayout());
        barraFondo.setBackground(UITheme.LINE);
        barraFondo.setPreferredSize(new Dimension(0, 10));
 
        barraFill = new JPanel();
        barraFill.setBackground(UITheme.VERDE_PRINCIPAL);
        barraFill.setPreferredSize(new Dimension(0, 10));
        barraFondo.add(barraFill, BorderLayout.WEST);
        barraCont.add(barraFondo, BorderLayout.CENTER);
 
        // === NIVEL ===
        lblNivel = new JLabel("SIN ACCIONES AÚN");
        lblNivel.setFont(UITheme.FONT_BOLD);
        lblNivel.setForeground(UITheme.VERDE_PRINCIPAL);
        lblNivel.setAlignmentX(LEFT_ALIGNMENT);
 
        // === ACCIONES ===
        lblAcciones = new JLabel("0 acciones sostenibles registradas");
        lblAcciones.setFont(UITheme.FONT_SMALL);
        lblAcciones.setForeground(UITheme.MUTED);
        lblAcciones.setAlignmentX(LEFT_ALIGNMENT);
        lblAcciones.setBorder(new EmptyBorder(4, 0, 16, 0));
 
        // === INSIGNIA ===
        JLabel lblInsigniaLabel = new JLabel("INSIGNIA OBTENIDA");
        lblInsigniaLabel.setFont(UITheme.FONT_TINY);
        lblInsigniaLabel.setForeground(UITheme.MUTED);
        lblInsigniaLabel.setAlignmentX(LEFT_ALIGNMENT);
        lblInsigniaLabel.setBorder(new EmptyBorder(0, 0, 6, 0));
 
        panelInsignia = new JPanel(new BorderLayout(10, 0));
        panelInsignia.setBackground(UITheme.VERDE_OSCURO);
        panelInsignia.setBorder(new CompoundBorder(
            new LineBorder(UITheme.VERDE_OSCURO, 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));
        panelInsignia.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panelInsignia.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel lblInsigniaEmoji = new JLabel("🌱");
        lblInsigniaEmoji.setFont(UITheme.FONT_EMOJI);
 
        JPanel insigniaTexto = new JPanel(new GridLayout(2, 1, 0, 2));
        insigniaTexto.setOpaque(false);
 
        lblInsigniaTitulo = new JLabel("SEMILLA VERDE");
        lblInsigniaTitulo.setFont(UITheme.FONT_BOLD);
        lblInsigniaTitulo.setForeground(Color.WHITE);
 
        lblInsigniaDesc = new JLabel("Pequeño paso, gran dirección");
        lblInsigniaDesc.setFont(UITheme.FONT_SMALL);
        lblInsigniaDesc.setForeground(UITheme.MENTA);
 
        insigniaTexto.add(lblInsigniaTitulo);
        insigniaTexto.add(lblInsigniaDesc);
 
        panelInsignia.add(lblInsigniaEmoji, BorderLayout.WEST);
        panelInsignia.add(insigniaTexto,    BorderLayout.CENTER);
 
        // === EQUIVALENCIAS ===
        JLabel lblEquivLabel = new JLabel("EQUIVALENCIAS");
        lblEquivLabel.setFont(UITheme.FONT_TINY);
        lblEquivLabel.setForeground(UITheme.MUTED);
        lblEquivLabel.setAlignmentX(LEFT_ALIGNMENT);
        lblEquivLabel.setBorder(new EmptyBorder(14, 0, 8, 0));
 
        JPanel equivPanel = new JPanel(new GridLayout(3, 1, 0, 6));
        equivPanel.setOpaque(false);
        equivPanel.setAlignmentX(LEFT_ALIGNMENT);
 
        lblArboles = buildEquivFila("🌳 Árboles equiv.",  "0.00 árboles/año", UITheme.VERDE_PRINCIPAL);
        lblKm      = buildEquivFila("🚗 Km sin recorrer", "0.00 km",          UITheme.BLUE);
        lblBolsas  = buildEquivFila("🛍 Bolsas plásticas","0 bolsas",         UITheme.CORAL);
 
        equivPanel.add(lblArboles);
        equivPanel.add(lblKm);
        equivPanel.add(lblBolsas);
 
        // === AGREGAR TODO ===
        panel.add(lblTitulo);
        panel.add(lblSub);
        panel.add(lblCO2Total);
        panel.add(barraCont);
        panel.add(lblNivel);
        panel.add(lblAcciones);
        panel.add(lblInsigniaLabel);
        panel.add(panelInsignia);
        panel.add(lblEquivLabel);
        panel.add(equivPanel);
        panel.add(Box.createVerticalGlue());
 
        return panel;
    }
 
    private JLabel buildEquivFila(String etiqueta, String valor, Color colorValor) {
        JLabel lbl = new JLabel(
            "<html><table width='240'><tr>" +
            "<td>" + etiqueta + "</td>" +
            "<td align='right'><b><font color='#" + Integer.toHexString(colorValor.getRGB()).substring(2) + "'>" + valor + "</font></b></td>" +
            "</tr></table></html>"
        );
        lbl.setFont(UITheme.FONT_SMALL);
        return lbl;
    }
 
    // ============================================================
    // LÓGICA — REGISTRAR ACCIÓN
    // ============================================================
    private void registrarAccion(JTextField campo, double factor, String nombre) {
        try {
            double cantidad = Double.parseDouble(campo.getText().replace(",", "."));
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Ingresa una cantidad mayor a 0.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
 
            double co2 = cantidad * factor;
            totalCO2 += co2;
            totalAcciones++;
 
            campo.setText("0");
            actualizarIndicador();
 
            JOptionPane.showMessageDialog(this,
                "✅ " + nombre + "\n" +
                "Cantidad: " + cantidad + "\n" +
                "CO₂ evitado: " + String.format("%.3f", co2) + " kg\n" +
                "Total sesión: " + String.format("%.3f", totalCO2) + " kg",
                "Acción registrada", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ============================================================
    // ACTUALIZAR INDICADOR DERECHO
    // ============================================================
    private void actualizarIndicador() {
        // === CO2 TOTAL ===
        lblCO2Total.setText(String.format("%.3f kg CO₂", totalCO2));
 
        // === ACCIONES ===
        lblAcciones.setText(totalAcciones + " acciones sostenibles registradas");
 
        // === NIVEL Y BARRA ===
        String nivel;
        int pct;
        String insigniaTitulo, insigniaDesc, insigniaEmoji;
 
        if (totalCO2 == 0) {
            nivel = "SIN ACCIONES AÚN";           pct = 0;
            insigniaTitulo = "SEMILLA VERDE";
            insigniaDesc   = "Pequeño paso, gran dirección";
            insigniaEmoji  = "🌱";
        } else if (totalCO2 < 1) {
            nivel = "INICIO SOSTENIBLE";           pct = 20;
            insigniaTitulo = "SEMILLA VERDE";
            insigniaDesc   = "Pequeño paso, gran dirección";
            insigniaEmoji  = "🌱";
        } else if (totalCO2 < 3) {
            nivel = "PIZZERÍA RESPONSABLE";        pct = 40;
            insigniaTitulo = "HOJA SOSTENIBLE";
            insigniaDesc   = "Impacto real y medible · < 3 kg CO₂";
            insigniaEmoji  = "🍃";
        } else if (totalCO2 < 6) {
            nivel = "NEGOCIO ECO-AMIGABLE";        pct = 60;
            insigniaTitulo = "ÁRBOL PROTECTOR";
            insigniaDesc   = "Excelente contribución · 3–6 kg CO₂";
            insigniaEmoji  = "🌳";
        } else if (totalCO2 < 10) {
            nivel = "EMPRESA SOSTENIBLE";          pct = 80;
            insigniaTitulo = "ÁRBOL PROTECTOR";
            insigniaDesc   = "Excelente contribución · 5–10 kg CO₂";
            insigniaEmoji  = "🌲";
        } else {
            nivel = "LÍDER AMBIENTAL";             pct = 100;
            insigniaTitulo = "GUARDIÁN DEL PLANETA";
            insigniaDesc   = "¡Impacto extraordinario! · +10 kg CO₂";
            insigniaEmoji  = "🌍";
        }
 
        lblNivel.setText(nivel);
 
        // === BARRA ===
        int anchoTotal = panelInsignia.getParent() != null ? panelInsignia.getParent().getWidth() - 40 : 260;
        barraFill.setPreferredSize(new Dimension((int)(anchoTotal * pct / 100.0), 10));
        barraFill.getParent().revalidate();
 
        // === INSIGNIA ===
        lblInsigniaTitulo.setText(insigniaTitulo);
        lblInsigniaDesc.setText(insigniaDesc);
 
        // Actualizar emoji de insignia (primer componente del panel)
        if (panelInsignia.getComponentCount() > 0 && panelInsignia.getComponent(0) instanceof JLabel) {
            ((JLabel) panelInsignia.getComponent(0)).setText(insigniaEmoji);
        }
 
        // === EQUIVALENCIAS ===
        double arboles = totalCO2 / 21.77;
        double km      = totalCO2 / 0.21;
        double bolsas  = totalCO2 / 0.033;
 
        actualizarEquiv(lblArboles, "🌳 Árboles equiv.",   String.format("%.2f árboles/año", arboles), UITheme.VERDE_PRINCIPAL);
        actualizarEquiv(lblKm,      "🚗 Km sin recorrer",  String.format("%.2f km", km),               UITheme.BLUE);
        actualizarEquiv(lblBolsas,  "🛍 Bolsas plásticas", String.format("%.0f bolsas", bolsas),       UITheme.CORAL);
 
        revalidate();
        repaint();
    }
 
    private void actualizarEquiv(JLabel lbl, String etiqueta, String valor, Color color) {
        lbl.setText(
            "<html><table width='240'><tr>" +
            "<td>" + etiqueta + "</td>" +
            "<td align='right'><b><font color='#" + Integer.toHexString(color.getRGB()).substring(2) + "'>" + valor + "</font></b></td>" +
            "</tr></table></html>"
        );
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
