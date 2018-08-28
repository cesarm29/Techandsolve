/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backendtechandsolve.controller;

import com.mycompany.backendtechandsolve.fachada.IVuelosFachada;
import com.mycompany.backendtechandsolve.vo.ReservacionVo;
import com.mycompany.backendtechandsolve.vo.VuelosVo;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cesar
 */
@RestController
@RequestMapping("/api/vuelos")
public class VuelosController {
    
    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(VuelosController.class);
    @Autowired
    private IVuelosFachada vuelosFachada;
    
    // vuelos por fecha o destino
   @RequestMapping(value = "/obtenerVuelos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<VuelosVo>> obtenerVuelos(@RequestParam(value = "fecha") String fecha, @RequestParam(value = "destino") String destino) {
        try {
            LOGGER.log(org.apache.logging.log4j.Level.INFO, "obtenerVuelos");
            ArrayList<VuelosVo> vuelos = new ArrayList<>();
            vuelos = vuelosFachada.obtenerVuelos(fecha, destino);
            HttpHeaders headers = new HttpHeaders();
            LOGGER.log(org.apache.logging.log4j.Level.INFO, "Antes de responder");
            return new ResponseEntity<ArrayList<VuelosVo>>(vuelos, headers, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.log(org.apache.logging.log4j.Level.ERROR, ex.getMessage(), ex);
            return new ResponseEntity<ArrayList<VuelosVo>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // reservaciones por cedula
   @RequestMapping(value = "/obtenerReservaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservacionVo>> obtenerReservaciones(@RequestParam(value = "cedula") String cedula) {
        try {
            LOGGER.log(org.apache.logging.log4j.Level.INFO, "obtenerReservaciones");
            ArrayList<ReservacionVo> reservaciones = new ArrayList<>();
            reservaciones = vuelosFachada.obtenerReservaciones(cedula);
            HttpHeaders headers = new HttpHeaders();
            LOGGER.log(org.apache.logging.log4j.Level.INFO, "Antes de responder");
            return new ResponseEntity<ArrayList<ReservacionVo>>(reservaciones, headers, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.log(org.apache.logging.log4j.Level.ERROR, ex.getMessage(), ex);
            return new ResponseEntity<ArrayList<ReservacionVo>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //guardar reservaciones
    @RequestMapping(value = "/guardarReservaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservacionVo> guardarReservaciones(@RequestBody ReservacionVo guardarReservacion) {

        String mensaje = "";
        try {
            LOGGER.log(org.apache.logging.log4j.Level.INFO, "Entro al servicio guardarAppointmentType");
            ReservacionVo reservacion = new ReservacionVo();
            mensaje = vuelosFachada.guardarReservacion(guardarReservacion);
            reservacion.setMensaje(mensaje);
            HttpHeaders headers = new HttpHeaders();
            LOGGER.log(org.apache.logging.log4j.Level.INFO, "Antes de responder");
            return new ResponseEntity<ReservacionVo>(reservacion, headers, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(org.apache.logging.log4j.Level.ERROR, e.getMessage(), e);
            return new ResponseEntity<ReservacionVo>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
}
