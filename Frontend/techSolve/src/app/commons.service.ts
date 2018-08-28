import { Injectable } from '@angular/core';   
import {Http,Response, Headers, RequestOptions } from '@angular/http';    
import { Observable } from 'rxjs/Observable';  
import 'rxjs/add/operator/map';  
import 'rxjs/add/operator/do';  
  
@Injectable()  
export class CommonsService {  
  
  constructor(private http: Http) { }  
  //service to mysql get vuelos all
  getVuelos(destino, fecha){
    //validacion de datos antes de envio
    if(destino == undefined){
      destino = "";
    }
    if(fecha == undefined){
      fecha = "";
    }
    var body = "fecha="+fecha+"&destino="+destino;
    let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
    let options = new RequestOptions({ headers: headers });     
    return this.http.post('http://localhost:8081/BackendTechAndSolve/api/vuelos/obtenerVuelos/', body, options)  
            .map((response: Response) =>response.json())              
  }



  //service to mysql get reservas all
  getReservas(item){ 
    var body = "cedula=" + item ;
    let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
    let options = new RequestOptions({ headers: headers });     
    return this.http.post('http://localhost:8081/BackendTechAndSolve/api/vuelos/obtenerReservaciones/', body, options)  
            .map((response: Response) =>response.json())              
  }


  //service to mysql send reservas to save
  sendReservas(cedula, nombres, apellidos, email, telefono, fechanacimineto, fechasolicitud, idVuelo, valorVuelo){ 
    var body = {
    "cedula": cedula,
    "nombre": nombres,
    "apellido": apellidos,
    "email": email,
    "telefono": telefono,
    "fechaNacimiento": fechanacimineto,
    "fechasolicitud": fechasolicitud,
    "idVuelo": idVuelo,
    "valor": valorVuelo
    };
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers }); 
    return this.http.post('http://localhost:8081/BackendTechAndSolve/api/vuelos/guardarReservaciones/', body, options)  
            .map((response: Response) =>response.json())              
  }



  





    
} 
