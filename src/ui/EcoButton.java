/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
// package ecofinance.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * EcoButton.java
 * ─────────────────────────────────────────────────────────────
 *  Botón reutilizable con el estilo de EcoFinance Business.
 *  Tiene 3 variantes: PRIMARY, SECONDARY, DANGER.
 *
 *  USO EN NETBEANS GUI Builder:
 *  1. Arrastra un JButton desde la Palette al panel
 *  2. Haz click derecho → "Customize Code"
 *  3. Cambia el tipo de variable de JButton a EcoButton:
 *       private EcoButton jButton1 = new EcoButton("Registrar", EcoButton.PRIMARY);
 *  4. O simplemente llama EcoButton.apply(jButton1, EcoButton.PRIMARY)
 *     después de initComponents() si quieres mantener el JButton de NetBeans
 *
 *  OPCIÓN MÁS FÁCIL (sin cambiar el tipo):
 *     // En el constructor, después de initComponents():
 *     EcoButton.apply(btnRegistrar,   EcoButton.PRIMARY);
 *     EcoButton.apply(btnCancelar,    EcoButton.SECONDARY);
 *     EcoButton.apply(btnEliminar,    EcoButton.DANGER);
 * ─────────────────────────────────────────────────────────────
 */
public class EcoButton extends JButton {

    // ── Variantes disponibles ─────────────────────────────────
    public static final int PRIMARY   = 0;  // verde — acción principal
    public static final int SECONDARY = 1;  // suave — acción secundaria
    public static final int DANGER    = 2;  // coral — eliminar / alerta

    private int variant;
    private Color bgNormal, bgHover, bgPress, fgColor;

    // ── Constructor principal ─────────────────────────────────
    public EcoButton(String text, int variant) {
        super(text);
        this.variant = variant;
        setup();
    }

    public EcoButton(String text) {
        this(text, PRIMARY);
    }

    // ── Configuración inicial ─────────────────────────────────
    private void setup() {
        switch (variant) {
            case PRIMARY:
                bgNormal = UITheme.VERDE_PRINCIPAL;
                bgHover  = UITheme.VERDE_MEDIO;
                bgPress  = UITheme.VERDE_OSCURO;
                fgColor  = Color.WHITE;
                break;
            case SECONDARY:
                bgNormal = UITheme.BG;
                bgHover  = UITheme.LINE;
                bgPress  = new Color(0xC8E4CF);
                fgColor  = UITheme.VERDE_PRINCIPAL;
                break;
            case DANGER:
            default:
                bgNormal = UITheme.CORAL;
                bgHover  = new Color(0xC45520);
                bgPress  = new Color(0xB04818);
                fgColor  = Color.WHITE;
                break;
        }

        setFont(UITheme.FONT_BOLD);
        setForeground(fgColor);
        setBackground(bgNormal);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 22, 10, 22));
        setOpaque(false);

        // Hover / press
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { setBackground(bgHover);  repaint(); }
            public void mouseExited(MouseEvent e)   { setBackground(bgNormal); repaint(); }
            public void mousePressed(MouseEvent e)  { setBackground(bgPress);  repaint(); }
            public void mouseReleased(MouseEvent e) { setBackground(bgHover);  repaint(); }
        });
    }

    // ── Pintado personalizado (bordes redondeados) ────────────
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        // Borde suave para SECONDARY
        if (variant == SECONDARY) {
            g2.setColor(UITheme.LINE);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    // ── Método estático para aplicar a JButton existente ──────
    /**
     * Aplica el estilo EcoFinance a un JButton del GUI Builder
     * sin cambiar su tipo.
     *
     * Ejemplo de uso después de initComponents():
     *   EcoButton.apply(jButton1, EcoButton.PRIMARY);
     *   EcoButton.apply(jButton2, EcoButton.SECONDARY);
     */
    public static void apply(JButton btn, int variant) {
        Color bgNormal, bgHover, bgPress, fgColor;

        switch (variant) {
            case PRIMARY:
                bgNormal = UITheme.VERDE_PRINCIPAL;
                bgHover  = UITheme.VERDE_MEDIO;
                bgPress  = UITheme.VERDE_OSCURO;
                fgColor  = Color.WHITE;
                break;
            case SECONDARY:
                bgNormal = UITheme.BG;
                bgHover  = UITheme.LINE;
                bgPress  = new Color(0xC8E4CF);
                fgColor  = UITheme.VERDE_PRINCIPAL;
                break;
            case DANGER:
            default:
                bgNormal = UITheme.CORAL;
                bgHover  = new Color(0xC45520);
                bgPress  = new Color(0xB04818);
                fgColor  = Color.WHITE;
                break;
        }

        final Color _bgNormal = bgNormal;
        final Color _bgHover  = bgHover;
        final Color _bgPress  = bgPress;

        btn.setFont(UITheme.FONT_BOLD);
        btn.setForeground(fgColor);
        btn.setBackground(bgNormal);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 22, 10, 22));
        btn.setOpaque(false);

        final int v = variant;
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { btn.setBackground(_bgHover);  btn.repaint(); }
            public void mouseExited(MouseEvent e)   { btn.setBackground(_bgNormal); btn.repaint(); }
            public void mousePressed(MouseEvent e)  { btn.setBackground(_bgPress);  btn.repaint(); }
            public void mouseReleased(MouseEvent e) { btn.setBackground(_bgHover);  btn.repaint(); }
        });

        // Pintar redondeado (override via UIManager trick)
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(b.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                if (v == SECONDARY) {
                    g2.setColor(UITheme.LINE);
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, 0, c.getWidth()-1, c.getHeight()-1, 10, 10);
                }
                g2.dispose();
                super.paint(g, c);
            }
        });
    }
}