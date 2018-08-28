/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backendtechandsolve.dao;

import com.mycompany.backendtechandsolve.vo.ReservacionVo;
import com.mycompany.backendtechandsolve.vo.VuelosVo;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public interface IVuelosDao { 
    //Metodo que obtiene vuelos
    public ArrayList<VuelosVo> obtenerVuelos(String fecha,String destino);
    //Metodo que obtiene reservaciones
    public ArrayList<ReservacionVo> obtenerReservaciones(String cedula);
    //Metodo que guarda reservacion
    public boolean guardarReservacion(ReservacionVo guardarReservacion);
   
    
}
