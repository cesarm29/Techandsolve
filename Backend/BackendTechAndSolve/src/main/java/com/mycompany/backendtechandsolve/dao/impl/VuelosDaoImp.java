/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backendtechandsolve.dao.impl;

import com.mycompany.backendtechandsolve.dao.IVuelosDao;
import com.mycompany.backendtechandsolve.vo.ReservacionVo;
import com.mycompany.backendtechandsolve.vo.VuelosVo;
import com.mycompany.backendtechandsolve.dao.AbstractDao;
import java.util.ArrayList;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cesar
 */
@Repository("VuelosDao")
public class VuelosDaoImp extends AbstractDao implements IVuelosDao {

    //obtiene vuelos
    @Override
    public ArrayList<VuelosVo> obtenerVuelos(String fecha, String destino) {

        ArrayList<VuelosVo> vuelosList = new ArrayList<>();

        Session sesion = getSession();

        String squery = "SELECT  id,aerolinea,origen, destino, fecha, hora, valor, sillas_disponibles  FROM vuelos  WHERE ";
        if (!destino.isEmpty()) {
            squery = squery + " destino = '" + destino + "' ";
        }

        if (!destino.isEmpty() && !fecha.isEmpty()) {
            squery = squery + " AND ";
        }

        if (!fecha.isEmpty()) {
            squery = squery + " fecha like " + "'" + fecha + "'";
        }

        SQLQuery query = sesion.createSQLQuery(squery);
        ArrayList<Object[]> vuelos2Object = (ArrayList<Object[]>) query.list();

        if (vuelos2Object.size() > 0) {
            for (Object[] object : vuelos2Object) {
                VuelosVo vuelosType = mapeoVuelos(object);
                vuelosList.add(vuelosType);
            }
        }

        return vuelosList;
    }

    //Realiza el mapeo del vuelos
    public VuelosVo mapeoVuelos(Object[] retorno) {
        VuelosVo vuelos = new VuelosVo();
        vuelos.setId(retorno[0] != null ? (Integer) retorno[0] : 0);
        vuelos.setAerolinea(retorno[1] != null ? retorno[1].toString() : null);
        vuelos.setOrigen(retorno[2] != null ? retorno[2].toString() : null);
        vuelos.setDestino(retorno[3] != null ? retorno[3].toString() : null);
        vuelos.setFecha(retorno[4] != null ? retorno[4].toString() : null);
        vuelos.setHora(retorno[5] != null ? retorno[5].toString() : null);

        //validaciones de fechas y horas
        Calendar now = Calendar.getInstance();
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE)); // vairables int
        //dia de semana
        int dia = now.get(Calendar.DAY_OF_WEEK);
        //hora
        int hora = now.get(Calendar.HOUR_OF_DAY);
        //validacion dias
        if (dia == Calendar.SUNDAY || dia == Calendar.SATURDAY) {
            //fin de semana
            //valido hora del dia
            if (hora <= 12) {
                int porcentaje = (Integer) retorno[6] * 30 / 100;
                int total = (Integer) retorno[6] + porcentaje;
                vuelos.setValor(total);
            } else {
                int porcentaje = (Integer) retorno[6] * 25 / 100;
                int total = (Integer) retorno[6] + porcentaje;
                vuelos.setValor(total);
            }

        } else {
            //entre semana
            //valido hora del dia
            if (hora <= 12) {
                int porcentaje = (Integer) retorno[6] * 15 / 100;
                int total = (Integer) retorno[6] + porcentaje;
                vuelos.setValor(total);
            } else {
                //entre semana
                int porcentaje = (Integer) retorno[6] * 10 / 100;
                int total = (Integer) retorno[6] + porcentaje;
                vuelos.setValor(total);
            }

        }

        vuelos.setSillas(retorno[7] != null ? (Integer) retorno[7] : 0);
        return vuelos;
    }

    //guarda reservaciones de vuelos
    @Override
    public boolean guardarReservacion(ReservacionVo guardarReservacion) {
        Session session = getSession();
        String squery = "";
        SQLQuery query = null;
        String squeryCount = "";
        SQLQuery queryCount = null;
        int filas = 0;
        //validaciones para obtener  edad
        LocalDate hoy = LocalDate.now();
        LocalDate nacimiento = guardarReservacion.getFechaNacimiento().toInstant().
                atZone(ZoneId.of("GMT+05:00")).toLocalDate();
        long edad = ChronoUnit.YEARS.between(nacimiento, hoy);
        //validacion mayoria de edad
        if (edad >= 18) {

            //validacion  por cedula y fecha 
            BigInteger count = (BigInteger) session.createSQLQuery("SELECT COUNT(id) FROM reservaciones  WHERE cedula= " + guardarReservacion.getCedula() + " and  fecha_solicitud like '" + hoy + "' ").uniqueResult();
            //validacion ingreso si es solo 0 
            if (count.intValue() == 0) {
                //insert  reservacion
                squery = "INSERT INTO reservaciones  (cedula,nombre, apellido, email,telefono,fecha_nacimiento,fecha_solicitud,id_vuelo,valor) values (" + guardarReservacion.getCedula() + ",'" + guardarReservacion.getNombre() + "', '" + guardarReservacion.getApellido() + "', '" + guardarReservacion.getEmail() + "' , '" + guardarReservacion.getTelefono() + "' , '" + nacimiento + "' , '" + hoy + "' , " + guardarReservacion.getIdVuelo() + " , " + guardarReservacion.getValor() + "  ) ";
                query = session.createSQLQuery(squery);
                filas = filas + query.executeUpdate();
            } else {
                //envio mensaje de error mas de una reserva por dia
                guardarReservacion.setMensaje("Error no se puede hacer más de una reserva para el mismo día.");
            }
        } else {
            //envio mensaje de error edad  
            guardarReservacion.setMensaje("Error Solo los mayores de edad pueden hacer reservas de vuelo");
        }

        //validacion de insert
        if (filas == 1) {
            return true;
        } else {
            return false;
        }
    }

    //Obtiene reservaciones
    @Override
    public ArrayList<ReservacionVo> obtenerReservaciones(String cedula) {
        ArrayList<ReservacionVo> reservacionesList = new ArrayList<>();

        Session sesion = getSession();
        String squery = "SELECT  reservaciones.id,reservaciones.cedula,reservaciones.nombre, reservaciones.apellido, reservaciones.email, reservaciones.telefono, reservaciones.fecha_nacimiento, reservaciones.fecha_solicitud , reservaciones.id_vuelo, vuelos.aerolinea, vuelos.origen, vuelos.destino, vuelos.fecha, vuelos.hora, reservaciones.valor  FROM reservaciones INNER JOIN vuelos ON reservaciones.id_vuelo = vuelos.id    where reservaciones.cedula =" + cedula + " ";
        SQLQuery query = sesion.createSQLQuery(squery);
        ArrayList<Object[]> reservasObject = (ArrayList<Object[]>) query.list();

        if (reservasObject.size() > 0) {
            for (Object[] objectReservacion : reservasObject) {
                ReservacionVo reservaType = mapeoReservaciones(objectReservacion);
                reservacionesList.add(reservaType);
            }
        }
        return reservacionesList;
    }

    //Realiza el mapeo del vuelos
    public ReservacionVo mapeoReservaciones(Object[] retorno) {
        ReservacionVo reservaciones = new ReservacionVo();
        //set vuelos
        reservaciones.setId(retorno[0] != null ? (Integer) retorno[0] : 0);
        reservaciones.setCedula(retorno[1] != null ? (Integer) retorno[1] : 0);
        reservaciones.setNombre(retorno[2] != null ? retorno[2].toString() : null);
        reservaciones.setApellido(retorno[3] != null ? retorno[3].toString() : null);
        reservaciones.setEmail(retorno[4] != null ? retorno[4].toString() : null);
        reservaciones.setTelefono(retorno[5] != null ? retorno[5].toString() : null);
        reservaciones.setFechaNacimiento(retorno[6] != null ? (Date) retorno[6] : null);
        reservaciones.setFechaSolicitud(retorno[7] != null ? retorno[7].toString() : null);
        reservaciones.setIdVuelo(retorno[8] != null ? (Integer) retorno[8] : 0);
        //set reservaciones
        reservaciones.setAerolinea(retorno[9] != null ? retorno[9].toString() : null);
        reservaciones.setOrigen(retorno[10] != null ? retorno[10].toString() : null);
        reservaciones.setDestino(retorno[11] != null ? retorno[11].toString() : null);
        reservaciones.setFecha(retorno[12] != null ? retorno[12].toString() : null);
        reservaciones.setHora(retorno[13] != null ? retorno[13].toString() : null);
        reservaciones.setValor(retorno[14] != null ? (Integer) retorno[14] : 0);
        return reservaciones;
    }

}
