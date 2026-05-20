/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import ui.UITheme;
 
/**
 *
 * @author omar.rebolledo
 */
public class PanelDesperdicio extends javax.swing.JPanel {

    // ════════════════════════════════════════════════════════
    //  MODELO — Ingrediente
    // ════════════════════════════════════════════════════════
    public static class Ingrediente {
        private String nombre;
        private double cantidadUsada;
        private double cantidadTotal;
        private String unidad;
 
        public Ingrediente(String nombre, double cantidadUsada, double cantidadTotal, String unidad) {
            this.nombre        = nombre;
            this.cantidadUsada = cantidadUsada;
            this.cantidadTotal = cantidadTotal;
            this.unidad        = unidad;
        }
 
        public String getNombre()        { return nombre; }
        public double getCantidadUsada() { return cantidadUsada; }
        public double getCantidadTotal() { return cantidadTotal; }
        public String getUnidad()        { return unidad; }
        public void setCantidadUsada(double v) { cantidadUsada = v; }
        public void setCantidadTotal(double v) { cantidadTotal = v; }
 
        public double getSobrante() { return cantidadTotal - cantidadUsada; }
 
        public double getPorcentajeDesperdicio() {
            if (cantidadTotal == 0) return 0;
            return (getSobrante() / cantidadTotal) * 100.0;
        }
 
        public String getNivelAlerta() {
            double p = getPorcentajeDesperdicio();
            if (p >= 70) return "CRÍTICO";
            if (p >= 40) return "ALTO";
            if (p >= 20) return "MEDIO";
            return "BAJO";
        }
    }
 
    // ════════════════════════════════════════════════════════
    //  MODELO — SesionDesperdicio
    // ════════════════════════════════════════════════════════
    public static class SesionDesperdicio {
        private final List<Ingrediente> ingredientes = new ArrayList<>();
 
        public SesionDesperdicio() {
            // Datos iniciales de ejemplo
            ingredientes.add(new Ingrediente("Harina",    6.500, 10.000, "kg"));
            ingredientes.add(new Ingrediente("Queso",     1.200,  5.000, "kg"));
            ingredientes.add(new Ingrediente("Tomate",    5.800,  8.000, "kg"));
            ingredientes.add(new Ingrediente("Pepperoni", 2.500, 15.000, "kg"));
            ingredientes.add(new Ingrediente("Aceite",    3.200,  4.000, "L"));
        }
 
        public List<Ingrediente> getIngredientes() { return ingredientes; }
 
        public double getDesperdicioTotal() {
            double sobrante = 0, total = 0;
            for (Ingrediente i : ingredientes) {
                sobrante += i.getSobrante();
                total    += i.getCantidadTotal();
            }
            return total == 0 ? 0 : (sobrante / total) * 100.0;
        }
 
        public String getNivelGlobal() {
            double p = getDesperdicioTotal();
            if (p >= 60) return "CRÍTICO";
            if (p >= 35) return "ALTO";
            if (p >= 20) return "MEDIO";
            return "BAJO";
        }
 
        public void agregarIngrediente(Ingrediente ing)    { ingredientes.add(ing); }
        public void eliminarIngrediente(int idx)           { if (idx >= 0 && idx < ingredientes.size()) ingredientes.remove(idx); }
        public void actualizarIngrediente(int idx, double usado, double total) {
            if (idx >= 0 && idx < ingredientes.size()) {
                ingredientes.get(idx).setCantidadUsada(usado);
                ingredientes.get(idx).setCantidadTotal(total);
            }
        }
    }
 
    // ════════════════════════════════════════════════════════
    //  COMPONENTE — Barra de progreso redondeada
    // ════════════════════════════════════════════════════════
    private static class BarraProgreso extends JComponent {
        private double porcentaje;
        private Color  colorBarra;
 
        BarraProgreso(double porcentaje) {
            this.porcentaje = porcentaje;
            this.colorBarra = colorPor(porcentaje);
            setPreferredSize(new Dimension(200, 9));
        }
 
        void setPorcentaje(double p) { this.porcentaje = p; this.colorBarra = colorPor(p); repaint(); }
 
        private Color colorPor(double p) {
            if (p >= 70) return new Color(220,  50,  50);
            if (p >= 40) return new Color(230, 140,  20);
            if (p >= 20) return new Color(230, 190,  10);
            return              new Color( 50, 160,  80);
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), arc = h;
            g2.setColor(new Color(220, 235, 220));
            g2.fillRoundRect(0, 0, w, h, arc, arc);
            int fillW = (int)(w * Math.min(porcentaje / 100.0, 1.0));
            if (fillW > 0) { g2.setColor(colorBarra); g2.fillRoundRect(0, 0, fillW, h, arc, arc); }
        }
    }
 
    // ════════════════════════════════════════════════════════
    //  COMPONENTE — Tarjeta individual de ingrediente
    // ════════════════════════════════════════════════════════
    private static class TarjetaIngrediente extends JPanel {
        private final Ingrediente    ing;
        private final JLabel         lblPct;
        private final JLabel         lblSobrante;
        private final BarraProgreso  barra;
 
        TarjetaIngrediente(Ingrediente ing) {
            this.ing = ing;
            setLayout(new BorderLayout(0, 8));
            setBackground(Color.WHITE);
            setBorder(new CompoundBorder(
                new LineBorder(new Color(225, 238, 225), 1, true),
                new EmptyBorder(14, 16, 14, 16)
            ));
 
            JLabel lblNombre = new JLabel(ing.getNombre());
            lblNombre.setFont(UITheme.FONT_BOLD);
            lblNombre.setForeground(UITheme.INK);
 
            lblPct = new JLabel(fmt(ing.getPorcentajeDesperdicio()));
            lblPct.setFont(new Font(UITheme.FONT_BOLD.getName(), Font.BOLD, 26));
            lblPct.setForeground(color(ing.getPorcentajeDesperdicio()));
 
            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            top.add(lblNombre, BorderLayout.NORTH);
            top.add(lblPct,    BorderLayout.CENTER);
 
            barra = new BarraProgreso(ing.getPorcentajeDesperdicio());
            barra.setPreferredSize(new Dimension(0, 8));
 
            lblSobrante = new JLabel(sobraStr());
            lblSobrante.setFont(UITheme.FONT_SMALL);
            lblSobrante.setForeground(color(ing.getPorcentajeDesperdicio()));
 
            add(top,         BorderLayout.NORTH);
            add(barra,       BorderLayout.CENTER);
            add(lblSobrante, BorderLayout.SOUTH);
        }
 
        void actualizar() {
            double p = ing.getPorcentajeDesperdicio();
            lblPct.setText(fmt(p));
            lblPct.setForeground(color(p));
            barra.setPorcentaje(p);
            lblSobrante.setText(sobraStr());
            lblSobrante.setForeground(color(p));
        }
 
        private String fmt(double p) { return String.format("%.1f%%", p); }
        private String sobraStr() {
            double p    = ing.getPorcentajeDesperdicio();
            String icon = p >= 70 ? " ⚠" : p >= 40 ? " !" : "";
            return String.format("Sobrante: %.3f %s%s", ing.getSobrante(), ing.getUnidad(), icon);
        }
        private Color color(double p) {
            if (p >= 70) return new Color(200,  40,  40);
            if (p >= 40) return new Color(210, 120,  10);
            if (p >= 20) return new Color(180, 150,   0);
            return              new Color( 40, 140,  60);
        }
    }
 
    // ════════════════════════════════════════════════════════
    //  DIÁLOGO — Agregar / Editar ingrediente
    // ════════════════════════════════════════════════════════
    private static class DialogoIngrediente extends JDialog {
        private final JTextField txtNombre = new JTextField();
        private final JTextField txtUsado  = new JTextField();
        private final JTextField txtTotal  = new JTextField();
        private final JTextField txtUnidad = new JTextField("kg");
        private boolean     confirmado = false;
        private Ingrediente resultado  = null;
 
        DialogoIngrediente(Frame owner, Ingrediente existente) {
            super(owner, existente == null ? "Agregar Ingrediente" : "Editar Ingrediente", true);
            if (existente != null) {
                txtNombre.setText(existente.getNombre());
                txtUsado .setText(String.valueOf(existente.getCantidadUsada()));
                txtTotal .setText(String.valueOf(existente.getCantidadTotal()));
                txtUnidad.setText(existente.getUnidad());
            }
            buildUI();
        }
 
        private void buildUI() {
            setLayout(new BorderLayout(8, 8));
            getContentPane().setBackground(UITheme.BG);
 
            JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
            form.setOpaque(false);
            form.setBorder(new EmptyBorder(20, 20, 10, 20));
 
            form.add(lbl("Ingrediente:"));    form.add(txtNombre);
            form.add(lbl("Cantidad usada:")); form.add(txtUsado);
            form.add(lbl("Cantidad total:")); form.add(txtTotal);
            form.add(lbl("Unidad (kg/L):")); form.add(txtUnidad);
 
            JPanel bots = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            bots.setOpaque(false);
 
            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setFocusPainted(false);
            btnCancelar.addActionListener(e -> dispose());
 
            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.setBackground(new Color(30, 100, 50));
            btnGuardar.setForeground(Color.WHITE);
            btnGuardar.setFont(UITheme.FONT_BOLD);
            btnGuardar.setFocusPainted(false);
            btnGuardar.addActionListener(e -> guardar());
 
            bots.add(btnCancelar);
            bots.add(btnGuardar);
 
            add(form, BorderLayout.CENTER);
            add(bots, BorderLayout.SOUTH);
            setSize(380, 240);
            setLocationRelativeTo(getOwner());
        }
 
        private JLabel lbl(String txt) {
            JLabel l = new JLabel(txt);
            l.setFont(UITheme.FONT_BOLD);
            l.setForeground(UITheme.INK);
            return l;
        }
 
        private void guardar() {
            try {
                String nombre = txtNombre.getText().trim();
                double usado  = Double.parseDouble(txtUsado.getText().trim());
                double total  = Double.parseDouble(txtTotal.getText().trim());
                String unidad = txtUnidad.getText().trim();
 
                if (nombre.isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
                if (total <= 0)       throw new IllegalArgumentException("La cantidad total debe ser > 0.");
                if (usado < 0)        throw new IllegalArgumentException("La cantidad usada no puede ser negativa.");
                if (usado > total)    throw new IllegalArgumentException("Lo usado no puede superar el total.");
 
                resultado  = new Ingrediente(nombre, usado, total, unidad);
                confirmado = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.ERROR_MESSAGE);
            }
        }
 
        boolean     isConfirmado() { return confirmado; }
        Ingrediente getResultado() { return resultado; }
    }
 
    // ════════════════════════════════════════════════════════
    //  CAMPOS DEL PANEL
    // ════════════════════════════════════════════════════════
    private final SesionDesperdicio        sesion   = new SesionDesperdicio();
    private final List<TarjetaIngrediente> tarjetas = new ArrayList<>();
    private JLabel        lblPct;
    private JLabel        lblNivel;
    private BarraProgreso barraGlobal;
    private JLabel        lblAlerta;
    private JPanel        panelTarjetas;
 
    // ════════════════════════════════════════════════════════
    //  CONSTRUCTOR  (mismo patrón que tus compañeros)
    // ════════════════════════════════════════════════════════
    public PanelDesperdicio() {
        initComponents();
        setBackground(UITheme.BG);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(24, 28, 24, 28));
        build();
    }
 
    // ════════════════════════════════════════════════════════
    //  BUILD
    // ════════════════════════════════════════════════════════
    private void build() {
        add(buildPanelTitle(), BorderLayout.NORTH);
        add(buildContent(),    BorderLayout.CENTER);
    }
 
    // ── Título (respeta tu código original) ──────────────────
    private JPanel buildPanelTitle() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 18, 0));
 
        JLabel title = new JLabel("📊 Desperdicio");
        title.setFont(UITheme.FONT_EMOJI_BOLD_NORMAL);
        title.setForeground(UITheme.INK);
 
        JLabel subTitle = new JLabel("Pizzería Sostenible · Análisis de sobrantes por ingrediente");
        subTitle.setFont(UITheme.FONT_SMALL);
        subTitle.setForeground(UITheme.MUTED);
 
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subTitle);
 
        // Botones acción
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acciones.setOpaque(false);
 
        JButton btnAdd = mkBtn("+ Agregar",    new Color( 50, 140,  70));
        JButton btnEdt = mkBtn("✏ Editar",     new Color( 60, 100, 160));
        JButton btnDel = mkBtn("🗑 Eliminar",  new Color(180,  50,  50));
        JButton btnUpd = mkBtn("⟳ Actualizar", new Color( 30,  80,  50));
 
        btnAdd.addActionListener(e -> agregarIngrediente());
        btnEdt.addActionListener(e -> editarIngrediente());
        btnDel.addActionListener(e -> eliminarIngrediente());
        btnUpd.addActionListener(e -> actualizar());
 
        acciones.add(btnAdd); acciones.add(btnEdt);
        acciones.add(btnDel); acciones.add(btnUpd);
 
        top.add(titleBox, BorderLayout.WEST);
        top.add(acciones, BorderLayout.EAST);
        return top;
    }
 
    // ── Contenido ─────────────────────────────────────────────
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 16));
        wrapper.setOpaque(false);
        wrapper.add(buildBanner(),   BorderLayout.NORTH);
        wrapper.add(buildTarjetas(), BorderLayout.CENTER);
        return wrapper;
    }
 
    // ── Banner global ─────────────────────────────────────────
    private JPanel buildBanner() {
        double pct   = sesion.getDesperdicioTotal();
        String nivel = sesion.getNivelGlobal();
 
        JPanel banner = new JPanel(new BorderLayout(0, 10));
        banner.setBackground(Color.WHITE);
        banner.setBorder(new CompoundBorder(
            new LineBorder(new Color(218, 232, 218), 1),
            new EmptyBorder(16, 22, 16, 22)
        ));
 
        JLabel lblTit = new JLabel("DESPERDICIO TOTAL DE LA SESIÓN");
        lblTit.setFont(new Font(UITheme.FONT_SMALL.getName(), Font.BOLD, 10));
        lblTit.setForeground(UITheme.MUTED);
 
        JButton btn3R = new JButton("Ir a módulo 3R →");
        btn3R.setBackground(new Color(220, 100, 30));
        btn3R.setForeground(Color.WHITE);
        btn3R.setFont(UITheme.FONT_BOLD);
        btn3R.setFocusPainted(false);
        btn3R.setBorder(new EmptyBorder(7, 14, 7, 14));
        btn3R.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Módulo Sostenibilidad 3R:\nReciclar · Reducir · Reutilizar\n\n"
            + String.format("Desperdicio actual: %.1f%% (%s)",
                sesion.getDesperdicioTotal(), sesion.getNivelGlobal()),
            "Módulo 3R", JOptionPane.INFORMATION_MESSAGE));
 
        JPanel bannerTop = new JPanel(new BorderLayout());
        bannerTop.setOpaque(false);
        bannerTop.add(lblTit, BorderLayout.WEST);
        bannerTop.add(btn3R,  BorderLayout.EAST);
 
        lblPct   = new JLabel(String.format("%.1f%%", pct));
        lblNivel = new JLabel("— " + nivel + (pct >= 35 ? " ⚠" : ""));
        lblPct  .setFont(new Font(UITheme.FONT_BOLD.getName(), Font.BOLD, 34));
        lblNivel.setFont(new Font(UITheme.FONT_BOLD.getName(), Font.BOLD, 34));
        lblPct  .setForeground(colorNivel(nivel));
        lblNivel.setForeground(colorNivel(nivel));
 
        JPanel rowPct = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        rowPct.setOpaque(false);
        rowPct.add(lblPct); rowPct.add(lblNivel);
 
        barraGlobal = new BarraProgreso(pct);
        barraGlobal.setPreferredSize(new Dimension(0, 11));
 
        lblAlerta = new JLabel(mensaje(nivel));
        lblAlerta.setFont(UITheme.FONT_SMALL);
        lblAlerta.setForeground(colorNivel(nivel));
 
        JPanel bannerSur = new JPanel(new BorderLayout(0, 5));
        bannerSur.setOpaque(false);
        bannerSur.add(barraGlobal, BorderLayout.NORTH);
        bannerSur.add(lblAlerta,   BorderLayout.SOUTH);
 
        banner.add(bannerTop, BorderLayout.NORTH);
        banner.add(rowPct,    BorderLayout.CENTER);
        banner.add(bannerSur, BorderLayout.SOUTH);
        return banner;
    }
 
    // ── Grid de tarjetas ─────────────────────────────────────
    private JScrollPane buildTarjetas() {
        panelTarjetas = new JPanel(new GridLayout(0, 3, 14, 14));
        panelTarjetas.setOpaque(false);
        construirTarjetas();
 
        JScrollPane scroll = new JScrollPane(panelTarjetas);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
 
    // ════════════════════════════════════════════════════════
    //  LÓGICA DE INTERACCIÓN
    // ════════════════════════════════════════════════════════
    private void construirTarjetas() {
        panelTarjetas.removeAll();
        tarjetas.clear();
        for (Ingrediente ing : sesion.getIngredientes()) {
            TarjetaIngrediente t = new TarjetaIngrediente(ing);
            tarjetas.add(t);
            panelTarjetas.add(t);
        }
        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }
 
    private void actualizar() {
        double pct   = sesion.getDesperdicioTotal();
        String nivel = sesion.getNivelGlobal();
        lblPct  .setText(String.format("%.1f%%", pct));
        lblNivel.setText("— " + nivel + (pct >= 35 ? " ⚠" : ""));
        lblPct  .setForeground(colorNivel(nivel));
        lblNivel.setForeground(colorNivel(nivel));
        barraGlobal.setPorcentaje(pct);
        lblAlerta.setText(mensaje(nivel));
        lblAlerta.setForeground(colorNivel(nivel));
        tarjetas.forEach(TarjetaIngrediente::actualizar);
    }
 
    private void agregarIngrediente() {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        DialogoIngrediente dlg = new DialogoIngrediente(owner, null);
        dlg.setVisible(true);
        if (dlg.isConfirmado()) {
            sesion.agregarIngrediente(dlg.getResultado());
            construirTarjetas();
            actualizar();
        }
    }
 
    private void editarIngrediente() {
        List<Ingrediente> lista = sesion.getIngredientes();
        if (lista.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay ingredientes para editar."); return; }
        String sel = elegir(lista, "Seleccione ingrediente a editar:", "Editar");
        if (sel == null) return;
        int idx = buscarIdx(lista, sel);
        if (idx < 0) return;
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        DialogoIngrediente dlg = new DialogoIngrediente(owner, lista.get(idx));
        dlg.setVisible(true);
        if (dlg.isConfirmado()) {
            Ingrediente n = dlg.getResultado();
            sesion.actualizarIngrediente(idx, n.getCantidadUsada(), n.getCantidadTotal());
            construirTarjetas();
            actualizar();
        }
    }
 
    private void eliminarIngrediente() {
        List<Ingrediente> lista = sesion.getIngredientes();
        if (lista.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay ingredientes para eliminar."); return; }
        String sel = elegir(lista, "Seleccione ingrediente a eliminar:", "Eliminar");
        if (sel == null) return;
        int idx = buscarIdx(lista, sel);
        if (idx < 0) return;
        int ok = JOptionPane.showConfirmDialog(this,
            "¿Eliminar \"" + sel + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            sesion.eliminarIngrediente(idx);
            construirTarjetas();
            actualizar();
        }
    }
 
    // ════════════════════════════════════════════════════════
    //  HELPERS
    // ════════════════════════════════════════════════════════
    private JButton mkBtn(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setFont(UITheme.FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(7, 13, 7, 13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
 
    private Color colorNivel(String nivel) {
        switch (nivel) {
            case "CRÍTICO": return new Color(200,  40,  40);
            case "ALTO":    return new Color(200, 100,  10);
            case "MEDIO":   return new Color(160, 130,   0);
            default:        return new Color( 40, 140,  60);
        }
    }
 
    private String mensaje(String nivel) {
        switch (nivel) {
            case "CRÍTICO": return "✗  Desperdicio CRÍTICO. Procese excedentes en el módulo de Sostenibilidad 3R.";
            case "ALTO":    return "✗  Desperdicio ALTO. Procese excedentes en el módulo de Sostenibilidad 3R.";
            case "MEDIO":   return "⚠  Desperdicio MEDIO. Considere revisar las cantidades usadas.";
            default:        return "✔  Desperdicio bajo control. ¡Buen trabajo!";
        }
    }
 
    private String elegir(List<Ingrediente> lista, String msg, String titulo) {
        String[] nombres = lista.stream().map(Ingrediente::getNombre).toArray(String[]::new);
        return (String) JOptionPane.showInputDialog(this, msg, titulo,
                JOptionPane.PLAIN_MESSAGE, null, nombres, nombres[0]);
    }
 
    private int buscarIdx(List<Ingrediente> lista, String nombre) {
        for (int i = 0; i < lista.size(); i++)
            if (lista.get(i).getNombre().equals(nombre)) return i;
        return -1;
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
