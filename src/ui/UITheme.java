/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author omar.rebolledo
 */
public class UITheme {

    // Verdes principales (para fondos de sidebar, headers)
    public static final Color VERDE_OSCURO    = new Color(0x0D3D21);
    public static final Color VERDE_PRINCIPAL = new Color(0x1B6B3A);
    public static final Color VERDE_MEDIO     = new Color(0x2D9B5A);
    public static final Color VERDE_CLARO     = new Color(0x52C47A);
    public static final Color MENTA           = new Color(0xA8EDBE);
    
    // Fondo general de la aplicación
    public static final Color BG    = new Color(0xF2FAF4);
    public static final Color CARD  = Color.WHITE;
    public static final Color LINE  = new Color(0xC8E4CF); // bordes suaves
 
    // Texto
    public static final Color INK   = new Color(0x0A1F0E); // texto principal
    public static final Color MUTED = new Color(0x4D6B56); // texto secundario
 
    // Acentos (para chips, alertas, íconos)
    public static final Color AMBER  = new Color(0xE8A830); // ⚠ advertencia
    public static final Color CORAL  = new Color(0xD4622A); // ✘ error/gastos
    public static final Color BLUE   = new Color(0x1A6EA8); // ℹ info
    public static final Color MORADO = new Color(0x6B3A8A); // reporte/módulo 5
 
    // Estados de semáforo (para tabla de desperdicio)
    public static final Color GREEN_VAL = new Color(0x1B6B3A);
    public static final Color RED_VAL   = new Color(0xC62828);
 
    // Fondos de alerta (muy suaves, para banners)
    public static final Color BG_SUCCESS = new Color(0xE6F7ED);
    public static final Color BG_WARNING = new Color(0xFDF6E3);
    public static final Color BG_DANGER  = new Color(0xFDECE8);
    public static final Color BG_INFO    = new Color(0xE3F2FD);

    // =================================
    //  TIPOGRAFÍA — Segoe UI (incluido en Windows)
    // =================================
     // Para saldos y cifras grandes (ej: $284.500)
    
    public static final Font FONT_EMOJI =  new Font("Segoe UI Emoji", Font.PLAIN, 32); // === "Segoe UI Emoji" → fuente que soporta emojis (Windows)
    public static final Font FONT_EMOJI_BOLD_NORMAL =  new Font("Segoe UI Emoji", Font.BOLD, 20); // === "Segoe UI Emoji" → fuente que soporta emojis (Windows)
    public static final Font FONT_EMOJI_PLAIN_NORMAL =  new Font("Segoe UI Emoji", Font.PLAIN, 20); // === "Segoe UI Emoji" → fuente que soporta emojis (Windows)

            
    public static final Font FONT_HERO  = new Font("Segoe UI", Font.BOLD,  28);
 
    // Para títulos de panel y sección (ej: "Módulo 1 — Finanzas")
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD,  16);
 
    // Para texto en tablas, formularios y contenido general
    public static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
 
    // Para negrita dentro de contenido (ej: nombres en tabla)
    public static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
 
    // Para etiquetas de campo y subtítulos
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
 
    // Para hints, versiones, footers
    public static final Font FONT_TINY  = new Font("Segoe UI", Font.PLAIN, 10);
 
    // Para montos en campos de dinero
    public static final Font FONT_MONEY = new Font("Segoe UI", Font.BOLD,  18);
 
    // Para código y valores en tabla (alineado)
    public static final Font FONT_MONO  = new Font("Consolas",  Font.PLAIN, 12);
 

    // ====================================
    // FORMATOS DE NUMERO
    // ====================================
}
