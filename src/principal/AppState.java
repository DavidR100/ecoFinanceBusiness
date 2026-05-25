/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.text.DecimalFormat;
 
/**
 * AppState.java
 * ─────────────────────────────────────────────────────────
 *  Estado global compartido entre TODOS los paneles.
 *  En lugar de tener variables estáticas en el JFrame,
 *  cada panel accede a esta clase directamente.
 *
 *  Uso desde cualquier panel:
 *    AppState.totalIngresos += monto;
 *    double saldo = AppState.getSaldo();
 *    String nivel = AppState.getNivel();
 * ─────────────────────────────────────────────────────────
 */
public class AppState {
 
    // ── Financiero ────────────────────────────────────────
    public static double totalIngresos           = 0;
    public static double totalGastos             = 0;
    public static int    cantRegistros           = 0;
 
    // ── Sostenibilidad ────────────────────────────────────
    public static double totalCO2Ahorrado        = 0;
    public static int    totalAccionesSostenibles = 0;
    public static double totalDesperdicio = 0;
 
    // ── Inventario ────────────────────────────────────────
    public static String[] nomIngredientes = new String[10];
    public static double[] cantDisponible  = new double[10];
    public static double[] cantUsada       = new double[10];
    public static int      numIngredientes = 0;
 
    // ── Constantes CO2 (kg CO2e evitados por acción) ─────
    public static final double CO2_PLATO    = 0.05;
    public static final double CO2_PORCION  = 0.30;
    public static final double CO2_ACEITE   = 0.80;
    public static final double CO2_CARTON   = 0.20;
    public static final double CO2_ORGANICO = 0.15;
    public static final double CO2_ENVASE   = 0.10;
 
    // ── Formatos de número ────────────────────────────────
    public static final DecimalFormat FMT_MONEY = new DecimalFormat("$#,##0.00");
    public static final DecimalFormat FMT_NUM   = new DecimalFormat("#,##0.000");
 
    // ── Helpers ───────────────────────────────────────────
    public static double getSaldo() {
        return totalIngresos - totalGastos;
    }
 
    public static String getNivel() {
        return getNivel(totalCO2Ahorrado);
    }
 
    public static String getNivel(double co2) {
        if      (co2 == 0)  return "SIN ACCIONES AÚN";
        else if (co2 < 1)   return "INICIO SOSTENIBLE";
        else if (co2 < 3)   return "PIZZERÍA RESPONSABLE";
        else if (co2 < 6)   return "NEGOCIO ECO-AMIGABLE";
        else if (co2 < 10)  return "EMPRESA SOSTENIBLE";
        else                return "LÍDER AMBIENTAL";
    }
 
    public static String getInsignia() {
        if      (totalCO2Ahorrado == 0)   return "—";
        else if (totalCO2Ahorrado < 0.5)  return "🌱 SEMILLA VERDE";
        else if (totalCO2Ahorrado < 2.0)  return "🍃 HOJA SOSTENIBLE";
        else if (totalCO2Ahorrado < 5.0)  return "🌲 ÁRBOL PROTECTOR";
        else                               return "🌍 GUARDIÁN DEL PLANETA";
    }
 
    public static void registrarAccionCO2(double co2) {
        totalCO2Ahorrado        += co2;
        totalAccionesSostenibles++;
    }
}
