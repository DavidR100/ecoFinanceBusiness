/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

/**
 *
 * @author omar.rebolledo
 */
public class FinanceService {

    private double totalIngresos;
    private double totalGastos;

    public void agregarIngreso(double valor) {
        totalIngresos += valor;
    }

    public void agregarGasto(double valor) {
        totalGastos += valor;
    }

    public double getBalance() {
        return totalIngresos - totalGastos;
    }

    public double getIngresos() {
        return totalIngresos;
    }

    public double getGastos() {
        return totalGastos;
    }
}
