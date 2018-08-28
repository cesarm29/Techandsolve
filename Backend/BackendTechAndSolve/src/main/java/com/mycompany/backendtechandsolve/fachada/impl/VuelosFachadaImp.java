/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backendtechandsolve.fachada.impl;

import com.mycompany.backendtechandsolve.dao.IVuelosDao;
import com.mycompany.backendtechandsolve.fachada.IVuelosFachada;
import com.mycompany.backendtechandsolve.vo.ReservacionVo;
import com.mycompany.backendtechandsolve.vo.VuelosVo;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Level;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cesar
 */
@Service("VuelosService")
@Transactional
public class VuelosFachadaImp implements IVuelosFachada {

private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(VuelosFachadaImp.class);
@Autowired
private IVuelosDao vuelosDao;    

    @Override
    public ArrayList<VuelosVo> obtenerVuelos(String fecha, String destino) {
        ArrayList<VuelosVo> vuelosList = new ArrayList<>();
        try {
            vuelosList = vuelosDao.obtenerVuelos(fecha, destino);
        } catch (Exception ex) {
            LOGGER.log(org.apache.logging.log4j.Level.ERROR, ex.getMessage(), ex);
        }
        return vuelosList;
    }

    @Override
    public String guardarReservacion(ReservacionVo reservacion) {
        String mensaje = "";
        boolean banderaInsert = true;
        try {
            //guardo reservacion
            banderaInsert = banderaInsert && vuelosDao.guardarReservacion(reservacion);
            //valido insert    
            if (banderaInsert) {
                mensaje = "Guardado satisfactoriamente";
            } else {
                mensaje = reservacion.getMensaje();
            }
        } catch (Exception e) {
            LOGGER.log(org.apache.logging.log4j.Level.ERROR, e.getMessage(), e);
        }
        return mensaje;
    }

    @Override
    public ArrayList<ReservacionVo> obtenerReservaciones(String cedula) {
         ArrayList<ReservacionVo> reservacionesList = new ArrayList<>();
        try {
            reservacionesList = vuelosDao.obtenerReservaciones(cedula);
        } catch (Exception ex) {
            LOGGER.log(org.apache.logging.log4j.Level.ERROR, ex.getMessage(), ex);
        }
        return reservacionesList;
    }

  
    
}
