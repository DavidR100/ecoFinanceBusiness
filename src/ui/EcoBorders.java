/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

/**
 * EcoBorders.java
 * ─────────────────────────────────────────────────────────
 *  Bordes personalizados reutilizables en toda la app.
 *  Separados en su propio archivo para no contaminar
 *  los paneles ni la clase principal.
 * ─────────────────────────────────────────────────────────
 */
public class EcoBorders {
 
    /**
     * Borde redondeado de un color específico.
     * Uso: new EcoBorders.Rounded(10, UITheme.LINE)
     */
    public static class Rounded extends AbstractBorder {
        private final int   radius;
        private final Color color;
 
        public Rounded(int radius, Color color) {
            this.radius = radius;
            this.color  = color;
        }
 
        @Override
        public void paintBorder(Component c, Graphics g,
                                int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }
 
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2,
                              radius / 2, radius / 2);
        }
    }
 
    /**
     * Borde de acento en la parte inferior (para tarjetas de módulo).
     * Uso: new EcoBorders.Bottom(UITheme.VERDE_MEDIO, 3)
     */
    public static class Bottom extends AbstractBorder {
        private final Color color;
        private final int   thickness;
 
        public Bottom(Color color, int thickness) {
            this.color     = color;
            this.thickness = thickness;
        }
 
        @Override
        public void paintBorder(Component c, Graphics g,
                                int x, int y, int w, int h) {
            g.setColor(color);
            g.fillRect(x, y + h - thickness, w, thickness);
        }
 
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, thickness, 0);
        }
    }
}