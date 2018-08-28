/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backendtechandsolve.fachada;

import com.mycompany.backendtechandsolve.vo.ReservacionVo;
import com.mycompany.backendtechandsolve.vo.VuelosVo;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public interface IVuelosFachada {
    
    //obtiene lista de vuelos
    public ArrayList<VuelosVo> obtenerVuelos(String fecha,String destino);
    //obtiene lista de vuelos
    public ArrayList<ReservacionVo> obtenerReservaciones(String cedula);
    //guarda reserva vuelos
    public String guardarReservacion(ReservacionVo reservacion);
    
}
