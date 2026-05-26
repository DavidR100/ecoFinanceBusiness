/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paneles;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import principal.AppState;
import principal.EFBPrincipal;
import ui.UITheme;

/**
 *
 * @author omar.rebolledo
 */
/**
 * PanelReporte.java
 * ─────────────────────────────────────────────────────────
 *  Módulo 5 — Reporte General.
 *  Muestra la visión integrada de todos los módulos:
 *  finanzas, inventario e impacto ambiental.
 *
 *  Opciones de descarga:
 *    - TXT  : reporte de texto plano, siempre disponible
 *    - PDF  : usa java.awt.print (sin librerías externas)
 * ─────────────────────────────────────────────────────────
 */
public class PanelReporte extends JPanel {
 
    private final EFBPrincipal app;
 
    // Labels que se actualizan en refresh()
    private JLabel lblIngresos;
    private JLabel lblGastos;
    private JLabel lblSaldo;
    private JLabel lblEstadoFin;
    private JLabel lblIngCount;
    private JLabel lblIngAlerta;
    private JLabel lblCO2;
    private JLabel lblAcciones;
    private JLabel lblNivel;
    private JLabel lblInsignia;
    private JLabel lblConclu;
    private JLabel lblFecha;
 
    // Panel de equivalencias (actualizado en refresh)
    private JLabel lblArboles;
    private JLabel lblKm;
    private JLabel lblBolsas;
 
    // ── Constructor ───────────────────────────────────────
    public PanelReporte(EFBPrincipal app) {
        this.app = app;
        setBackground(UITheme.BG);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(24, 28, 24, 28));
        buildUI();
    }
 
    // ════════════════════════════════════════════════════
    //  CONSTRUCCIÓN DE LA INTERFAZ
    // ════════════════════════════════════════════════════
    private void buildUI() {
 
        // ── Encabezado con botones de descarga ───────────
        JLabel title = new JLabel("📋  Módulo 5 — Reporte General");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UITheme.INK);
 
        JButton btnTxt = UITheme.secondaryButton("⬇ Descargar TXT");
        btnTxt.setForeground(UITheme.VERDE_PRINCIPAL);
        btnTxt.addActionListener(e -> descargarTXT());
 
        JButton btnPdf = UITheme.primaryButton("⬇ Descargar PDF");
        btnPdf.addActionListener(e -> descargarPDF());
 
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnRow.setOpaque(false);
        btnRow.add(btnTxt);
        btnRow.add(btnPdf);
 
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.add(title,  BorderLayout.WEST);
        topRow.add(btnRow, BorderLayout.EAST);
        add(topRow, BorderLayout.NORTH);
 
        // ── Contenido central ─────────────────────────────
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(18, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.BOTH;
 
        // Fila 1 — tres cards de resumen
        gbc.gridy = 0; gbc.weighty = 1;
 
        gbc.gridx = 0; gbc.weightx = 1;
        center.add(buildCardFinanciero(), gbc);
 
        gbc.gridx = 1; gbc.weightx = 1;
        center.add(buildCardInventario(), gbc);
 
        gbc.gridx = 2; gbc.weightx = 1;
        center.add(buildCardEco(), gbc);
 
        // Fila 2 — conclusión integrada (ancho completo)
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 3; gbc.weightx = 1; gbc.weighty = 0.5;
        center.add(buildCardConclusion(), gbc);
 
        add(center, BorderLayout.CENTER);
    }
 
    // ── [1] Card Financiero ───────────────────────────────
    private JPanel buildCardFinanciero() {
        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.add(UITheme.sectionTitle("[1] Resumen Financiero"));
        card.add(Box.createVerticalStrut(12));
 
        lblIngresos  = statRow(card, "Total ingresos", "$0.00",   UITheme.GREEN_VAL);
        lblGastos    = statRow(card, "Total gastos",   "$0.00",   UITheme.RED_VAL);
        lblSaldo     = statRow(card, "Saldo final",    "$0.00",   UITheme.VERDE_MEDIO);
        card.add(Box.createVerticalStrut(6));
        lblEstadoFin = new JLabel("—");
        lblEstadoFin.setFont(UITheme.FONT_SMALL);
        lblEstadoFin.setForeground(UITheme.MUTED);
        card.add(lblEstadoFin);
        return card;
    }
 
    // ── [2] Card Inventario ───────────────────────────────
    private JPanel buildCardInventario() {
        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.add(UITheme.sectionTitle("[2] Inventario"));
        card.add(Box.createVerticalStrut(12));
 
        lblIngCount  = statRow(card, "Ingredientes registrados", "0", UITheme.BLUE);
        lblIngAlerta = statRow(card, "Con alerta de sobrante",   "0", UITheme.CORAL);
        return card;
    }
 
    // ── [3] Card Impacto Ambiental ────────────────────────
    private JPanel buildCardEco() {
        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.add(UITheme.sectionTitle("[3] Impacto Ambiental"));
        card.add(Box.createVerticalStrut(12));
 
        lblCO2      = statRow(card, "CO₂ evitado",       "0.000 kg", UITheme.VERDE_MEDIO);
        lblAcciones = statRow(card, "Acciones 3R",        "0",        UITheme.VERDE_MEDIO);
        lblNivel    = statRow(card, "Nivel alcanzado",    "—",        UITheme.VERDE_MEDIO);
 
        card.add(Box.createVerticalStrut(6));
        lblInsignia = new JLabel("—");
        lblInsignia.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        card.add(lblInsignia);
 
        card.add(Box.createVerticalStrut(10));
        card.add(UITheme.sectionTitle("Equivalencias"));
        card.add(Box.createVerticalStrut(4));
        lblArboles = eqLabel("🌳 —");
        lblKm      = eqLabel("🚗 —");
        lblBolsas  = eqLabel("🛍️ —");
        card.add(lblArboles);
        card.add(Box.createVerticalStrut(2));
        card.add(lblKm);
        card.add(Box.createVerticalStrut(2));
        card.add(lblBolsas);
        return card;
    }
 
    // ── [4] Card Conclusión ───────────────────────────────
    private JPanel buildCardConclusion() {
        JPanel card = UITheme.card(new BorderLayout(14, 0));
        card.setBackground(UITheme.VERDE_OSCURO);
        card.setOpaque(true);
        card.setBorder(new EmptyBorder(18, 22, 18, 22));
 
        JLabel icoLbl = new JLabel("⭐");
        icoLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
 
        JPanel right = new JPanel(new GridLayout(3, 1, 0, 4));
        right.setOpaque(false);
 
        JLabel concluTitle = UITheme.sectionTitle("Conclusión integrada");
        concluTitle.setForeground(UITheme.MENTA);
 
        lblConclu = new JLabel("Complete los módulos para ver la conclusión.");
        lblConclu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblConclu.setForeground(Color.BLACK);
 
        lblFecha = new JLabel("—");
        lblFecha.setFont(UITheme.FONT_TINY);
        lblFecha.setForeground(new Color(255, 255, 255, 120));
 
        right.add(concluTitle);
        right.add(lblConclu);
        right.add(lblFecha);
 
        card.add(icoLbl, BorderLayout.WEST);
        card.add(right,  BorderLayout.CENTER);
        return card;
    }
 
    // ── Helper: fila de estadística ───────────────────────
    private JLabel statRow(JPanel card, String label,
                           String value, Color color) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        row.setBorder(new MatteBorder(0, 0, 1, 0, UITheme.LINE));
 
        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.FONT_BODY);
        lbl.setForeground(UITheme.MUTED);
 
        JLabel val = new JLabel(value);
        val.setFont(UITheme.FONT_BOLD);
        val.setForeground(color);
 
        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.EAST);
        card.add(row);
        card.add(Box.createVerticalStrut(6));
        return val;
    }
 
    private JLabel eqLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.INK);
        return l;
    }
 
    // ════════════════════════════════════════════════════
    //  ACTUALIZAR DATOS
    // ════════════════════════════════════════════════════
    public void refresh() {
        // Financiero
        double saldo = AppState.getSaldo();
        lblIngresos.setText(AppState.FMT_MONEY.format(AppState.totalIngresos));
        lblGastos.setText(AppState.FMT_MONEY.format(AppState.totalGastos));
        lblSaldo.setText(AppState.FMT_MONEY.format(saldo));
        lblSaldo.setForeground(saldo >= 0 ? UITheme.GREEN_VAL : UITheme.RED_VAL);
        lblEstadoFin.setText(saldo >= 0
            ? "✔ Estado positivo — la pizzería genera ganancias"
            : "✘ Estado negativo — revisar y reducir gastos");
        lblEstadoFin.setForeground(saldo >= 0 ? UITheme.VERDE_MEDIO : UITheme.CORAL);
 
        // Inventario
        int conAlerta = 0;
        for (int i = 0; i < AppState.numIngredientes; i++) {
            double rest = AppState.cantDisponible[i] - AppState.cantUsada[i];
            double pct  = AppState.cantDisponible[i] > 0
                ? (rest / AppState.cantDisponible[i]) * 100 : 0;
            if (pct > 30) conAlerta++;
        }
        lblIngCount.setText(String.valueOf(AppState.numIngredientes));
        lblIngAlerta.setText(String.valueOf(conAlerta));
        lblIngAlerta.setForeground(conAlerta > 0 ? UITheme.CORAL : UITheme.GREEN_VAL);
 
        // Ambiental
        double co2 = AppState.totalCO2Ahorrado;
        lblCO2.setText(AppState.FMT_NUM.format(co2) + " kg CO₂");
        lblAcciones.setText(String.valueOf(AppState.totalAccionesSostenibles));
        lblNivel.setText(AppState.getNivel());
        lblInsignia.setText(AppState.getInsignia());
 
        double arboles = co2 / 21.77;
        double km      = co2 / 0.21;
        double bolsas  = co2 / 0.033;
        lblArboles.setText(String.format("🌳 %.2f árbol(es) / año", arboles));
        lblKm.setText(String.format("🚗 %.1f km no recorridos", km));
        lblBolsas.setText(String.format("🛍️ %.0f bolsas plásticas evitadas", bolsas));
 
        // Conclusión
        boolean finBien = saldo >= 0;
        boolean ecoBien = co2   >  0;
        if      (finBien && ecoBien)
            lblConclu.setText("La pizzería opera con GANANCIA y RESPONSABILIDAD AMBIENTAL. ¡Excelente!");
        else if (finBien)
            lblConclu.setText("Finanzas positivas. Integre acciones sostenibles para un negocio más completo.");
        else if (ecoBien)
            lblConclu.setText("Buenas prácticas sostenibles. Mejore el control financiero.");
        else
            lblConclu.setText("Complete los módulos de finanzas y sostenibilidad.");
 
        // Fecha y hora del reporte
        lblFecha.setText("Generado: "
            + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
 
        revalidate();
        repaint();
    }
 
    // ════════════════════════════════════════════════════
    //  DESCARGA TXT
    // ════════════════════════════════════════════════════
    private void descargarTXT() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte como TXT");
        chooser.setFileFilter(
            new FileNameExtensionFilter("Archivo de texto (*.txt)", "txt"));
        chooser.setSelectedFile(new File("Reporte_EcoFinance_"
            + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ".txt"));
 
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
 
        File archivo = chooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            archivo = new File(archivo.getAbsolutePath() + ".txt");
        }
 
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                    new FileOutputStream(archivo), "UTF-8"))) {
 
            String linea = "=".repeat(54);
            String sep   = "-".repeat(54);
            String fecha = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
 
            pw.println(linea);
            pw.println("   ECOFINANCE BUSINESS - Reporte General");
            pw.println("   Pizzeria Sostenible");
            pw.println("   Fecha: " + fecha);
            pw.println(linea);
 
            // [1] Financiero
            pw.println();
            pw.println("[1] RESUMEN FINANCIERO");
            pw.println(sep);
            pw.printf("  Total ingresos : %s%n",
                AppState.FMT_MONEY.format(AppState.totalIngresos));
            pw.printf("  Total gastos   : %s%n",
                AppState.FMT_MONEY.format(AppState.totalGastos));
            pw.printf("  Saldo final    : %s%n",
                AppState.FMT_MONEY.format(AppState.getSaldo()));
            pw.printf("  Movimientos    : %d%n", AppState.cantRegistros);
            pw.println("  Estado: " + (AppState.getSaldo() >= 0
                ? "POSITIVO - La pizzeria genera ganancias."
                : "NEGATIVO - Revisar y reducir gastos."));
 
            // [2] Inventario
            pw.println();
            pw.println("[2] INVENTARIO");
            pw.println(sep);
            if (AppState.numIngredientes == 0) {
                pw.println("  Sin ingredientes registrados.");
            } else {
                pw.printf("  %-20s %10s %10s %10s%n",
                    "Ingrediente", "Disponib.", "Usado", "Restante");
                pw.println("  " + "-".repeat(50));
                for (int i = 0; i < AppState.numIngredientes; i++) {
                    double rest = AppState.cantDisponible[i]
                                - AppState.cantUsada[i];
                    double pct  = AppState.cantDisponible[i] > 0
                        ? (rest / AppState.cantDisponible[i]) * 100 : 0;
                    String est  = pct > 30 ? "ALTO"
                                : pct > 15 ? "MEDIO" : "OK";
                    pw.printf("  %-20s %10.3f %10.3f %10.3f  [%s]%n",
                        AppState.nomIngredientes[i],
                        AppState.cantDisponible[i],
                        AppState.cantUsada[i],
                        rest, est);
                }
            }
 
            // [3] Ambiental
            double co2 = AppState.totalCO2Ahorrado;
            pw.println();
            pw.println("[3] IMPACTO AMBIENTAL");
            pw.println(sep);
            pw.printf("  Acciones 3R registradas : %d%n",
                AppState.totalAccionesSostenibles);
            pw.printf("  CO2 evitado total       : %.3f kg%n", co2);
            pw.println("  Nivel alcanzado         : " + AppState.getNivel());
            pw.println("  Insignia                : " + AppState.getInsignia()
                .replaceAll("[^\\x00-\\x7F]", "").trim()); // ASCII para TXT
            pw.println();
            pw.println("  Equivalencias:");
            pw.printf("    * %.2f arbol(es) absorbiendo CO2 por 1 anio%n",
                co2 / 21.77);
            pw.printf("    * %.1f km que un auto no recorrio%n",
                co2 / 0.21);
            pw.printf("    * %.0f bolsas plasticas no producidas%n",
                co2 / 0.033);
 
            // [4] Conclusión
            pw.println();
            pw.println("[4] CONCLUSION INTEGRADA");
            pw.println(sep);
            boolean finBien = AppState.getSaldo() >= 0;
            boolean ecoBien = co2 > 0;
            if      (finBien && ecoBien)
                pw.println("  La pizzeria opera con GANANCIA y RESPONSABILIDAD AMBIENTAL.");
            else if (finBien)
                pw.println("  Finanzas positivas. Falta integrar acciones sostenibles.");
            else if (ecoBien)
                pw.println("  Buenas practicas sostenibles. Mejorar el control financiero.");
            else
                pw.println("  Complete los modulos de finanzas y sostenibilidad.");
 
            pw.println();
            pw.println(linea);
            pw.println("  EcoFinance Business - Proyecto Integrador 2026");
            pw.println("  \"Finanzas que alimentan el futuro\"");
            pw.println(linea);
 
            JOptionPane.showMessageDialog(this,
                "✔ Reporte TXT guardado en:\n" + archivo.getAbsolutePath(),
                "Descarga exitosa", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar el archivo:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ════════════════════════════════════════════════════
    //  DESCARGA PDF  (java.awt.print — sin librerías extra)
    // ════════════════════════════════════════════════════
    private void descargarPDF() {
        // Elegir dónde guardar
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte como PDF");
        chooser.setFileFilter(
            new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        chooser.setSelectedFile(new File("Reporte_EcoFinance_"
            + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ".pdf"));
 
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
 
        File archivo = chooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }
 
        // Usar PrinterJob con destino a archivo PDF
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("EcoFinance_Reporte");
 
        // Atributos de impresión → destino = archivo
        javax.print.attribute.PrintRequestAttributeSet attrs =
            new javax.print.attribute.HashPrintRequestAttributeSet();
        attrs.add(new javax.print.attribute.standard.Destination(
            archivo.toURI()));
 
        // PageFormat — tamaño carta
        PageFormat pf = job.defaultPage();
        Paper paper   = new Paper();
        double margin = 36; // 0.5 pulgada en puntos (1pt = 1/72 in)
        paper.setSize(612, 792); // carta: 8.5x11 pulgadas en puntos
        paper.setImageableArea(margin, margin,
            612 - 2 * margin, 792 - 2 * margin);
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.PORTRAIT);
 
        // Contenido del PDF (dibujado como Graphics2D)
        final File archivoFinal = archivo;
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
 
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 
            double x  = pageFormat.getImageableX();
            double y  = pageFormat.getImageableY();
            double w  = pageFormat.getImageableWidth();
            g2.translate(x, y);
 
            int yPos = 0;
 
            // ── HEADER ─────────────────────────────────
            g2.setColor(new Color(0x0D3D21));
            g2.fillRect(0, yPos, (int) w, 60);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            g2.drawString("EcoFinance Business", 16, yPos + 22);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.drawString("Reporte General - Pizzeria Sostenible", 16, yPos + 38);
            g2.drawString("Fecha: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                16, yPos + 52);
            yPos += 72;
 
            // ── [1] FINANCIERO ──────────────────────────
            yPos = pdfSeccion(g2, "[1] Resumen Financiero",
                              (int) w, yPos, new Color(0x1B6B3A));
            yPos = pdfFila(g2, "Total ingresos:",
                AppState.FMT_MONEY.format(AppState.totalIngresos),
                (int) w, yPos, false);
            yPos = pdfFila(g2, "Total gastos:",
                AppState.FMT_MONEY.format(AppState.totalGastos),
                (int) w, yPos, true);
            yPos = pdfFila(g2, "Saldo final:",
                AppState.FMT_MONEY.format(AppState.getSaldo()),
                (int) w, yPos, false);
            yPos = pdfFila(g2, "Movimientos:",
                String.valueOf(AppState.cantRegistros),
                (int) w, yPos, true);
            yPos += 10;
 
            // ── [2] INVENTARIO ──────────────────────────
            yPos = pdfSeccion(g2, "[2] Inventario",
                              (int) w, yPos, new Color(0x1A6EA8));
            yPos = pdfFila(g2, "Ingredientes registrados:",
                String.valueOf(AppState.numIngredientes),
                (int) w, yPos, false);
 
            if (AppState.numIngredientes > 0) {
                // Mini-tabla de ingredientes
                yPos += 4;
                g2.setColor(new Color(0x0D3D21));
                g2.fillRect(0, yPos, (int) w, 18);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
                g2.drawString("Ingrediente",    8, yPos + 12);
                g2.drawString("Disponible", (int)(w*0.42), yPos + 12);
                g2.drawString("Usado",      (int)(w*0.60), yPos + 12);
                g2.drawString("Restante",   (int)(w*0.76), yPos + 12);
                g2.drawString("Estado",     (int)(w*0.89), yPos + 12);
                yPos += 18;
 
                for (int i = 0; i < AppState.numIngredientes; i++) {
                    double rest = AppState.cantDisponible[i]
                                - AppState.cantUsada[i];
                    double pct  = AppState.cantDisponible[i] > 0
                        ? (rest / AppState.cantDisponible[i]) * 100 : 0;
                    String est  = pct > 30 ? "ALTO"
                                : pct > 15 ? "MEDIO" : "OK";
                    Color rowBg = i % 2 == 0
                        ? new Color(0xF2FAF4) : Color.WHITE;
                    g2.setColor(rowBg);
                    g2.fillRect(0, yPos, (int) w, 16);
                    g2.setColor(new Color(0x0A1F0E));
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                    g2.drawString(AppState.nomIngredientes[i], 8, yPos + 11);
                    g2.drawString(String.format("%.2f", AppState.cantDisponible[i]),
                        (int)(w*0.42), yPos + 11);
                    g2.drawString(String.format("%.2f", AppState.cantUsada[i]),
                        (int)(w*0.60), yPos + 11);
                    g2.drawString(String.format("%.2f", rest),
                        (int)(w*0.76), yPos + 11);
                    Color estColor = "ALTO".equals(est) ? new Color(0xD4622A)
                        : "MEDIO".equals(est)          ? new Color(0xE8A830)
                        : new Color(0x1B6B3A);
                    g2.setColor(estColor);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
                    g2.drawString(est, (int)(w*0.89), yPos + 11);
                    yPos += 16;
                }
            }
            yPos += 10;
 
            // ── [3] AMBIENTAL ───────────────────────────
            double co2 = AppState.totalCO2Ahorrado;
            yPos = pdfSeccion(g2, "[3] Impacto Ambiental",
                              (int) w, yPos, new Color(0x2D9B5A));
            yPos = pdfFila(g2, "CO2 evitado:",
                AppState.FMT_NUM.format(co2) + " kg", (int) w, yPos, false);
            yPos = pdfFila(g2, "Acciones 3R:",
                String.valueOf(AppState.totalAccionesSostenibles),
                (int) w, yPos, true);
            yPos = pdfFila(g2, "Nivel:",
                AppState.getNivel(), (int) w, yPos, false);
            yPos += 4;
            g2.setColor(new Color(0x4D6B56));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.drawString("Equivalencias:", 8, yPos + 10);
            yPos += 14;
            g2.drawString(String.format(
                "  * %.2f arbol(es) absorbiendo CO2 por 1 anio  |  "
                + "%.1f km no recorridos  |  %.0f bolsas plasticas evitadas",
                co2 / 21.77, co2 / 0.21, co2 / 0.033), 8, yPos + 10);
            yPos += 20;
 
            // ── [4] CONCLUSIÓN ──────────────────────────
            boolean finBien = AppState.getSaldo() >= 0;
            boolean ecoBien = co2 > 0;
            String  conclu;
            if      (finBien && ecoBien)
                conclu = "La pizzeria opera con GANANCIA y RESPONSABILIDAD AMBIENTAL.";
            else if (finBien)
                conclu = "Finanzas positivas. Falta integrar acciones sostenibles.";
            else if (ecoBien)
                conclu = "Buenas practicas sostenibles. Mejorar el control financiero.";
            else
                conclu = "Complete los modulos de finanzas y sostenibilidad.";
 
            g2.setColor(new Color(0x0D3D21));
            g2.fillRect(0, yPos, (int) w, 50);
            g2.setColor(new Color(0xA8EDBE));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
            g2.drawString("[4] CONCLUSION INTEGRADA", 12, yPos + 14);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.drawString(conclu, 12, yPos + 32);
            yPos += 62;
 
            // ── FOOTER ──────────────────────────────────
            g2.setColor(new Color(0x4D6B56));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 8));
            g2.drawString(
                "EcoFinance Business - Proyecto Integrador 2026 | "
                + "\"Finanzas que alimentan el futuro\"",
                8, (int) pageFormat.getImageableHeight() - 8);
 
            return Printable.PAGE_EXISTS;
        });
 
        try {
            job.print(attrs);
            JOptionPane.showMessageDialog(this,
                "✔ Reporte PDF guardado en:\n" + archivoFinal.getAbsolutePath(),
                "Descarga exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (PrinterException ex) {
            // Fallback: si el PDF falla, ofrecer TXT
            int resp = JOptionPane.showConfirmDialog(this,
                "No se pudo generar el PDF en este sistema.\n"
                + "¿Desea descargar el reporte en formato TXT en su lugar?",
                "Error PDF", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) descargarTXT();
        }
    }
 
    // ── Helpers para dibujar el PDF ───────────────────────
    private int pdfSeccion(Graphics2D g2, String titulo,
                           int w, int y, Color color) {
        g2.setColor(color);
        g2.fillRect(0, y, w, 20);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
        g2.drawString(titulo, 8, y + 14);
        return y + 24;
    }
 
    private int pdfFila(Graphics2D g2, String label,
                        String value, int w, int y, boolean shade) {
        if (shade) {
            g2.setColor(new Color(0xF2FAF4));
            g2.fillRect(0, y, w, 16);
        }
        g2.setColor(new Color(0x4D6B56));
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        g2.drawString(label, 8, y + 12);
        g2.setColor(new Color(0x0A1F0E));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
        g2.drawString(value, w - 8 -
            g2.getFontMetrics().stringWidth(value), y + 12);
        return y + 16;
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
