/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ayoub.sistemadecontroldehangares;
/**
 *
 * @author Ayoub Atidi Belbaz
 */

import java.util.List;

public class SistemadeControlDeHangares {

    public static void main(String[] args) {
        HagarControl controlador = new HagarControl();

        // Instanciamos  y registramos una nueva nave
        Nave tie = new Nave("TIE Interceptor", "TI-v2", 1, "Critico");
        controlador.registrarNuevaNave(tie);

        // Simulamos una  reparación actualizando el estado a "Estable" mediante su ID
        controlador.actualizarEstadoNave(tie.getId(), "Estable");

        // Escaneamos HQL para listar cazas del modelo TI-V2
        List<Nave> cazas = controlador.obtenerCazasPorModelo("TI-v2");
        for (Nave caza : cazas) {
            System.out.println(caza);
        }

        HibernateUtil.shutdown();
    }
}