import { Component, OnInit } from '@angular/core';

import {CommonsService} from '../commons.service';
import {Http,Response, Headers, RequestOptions } from '@angular/http';
import { Router, ActivatedRoute } from '@angular/router';  

@Component({
  selector: 'app-reserva-vuelos',
  templateUrl: './reserva-vuelos.component.html',
  styleUrls: ['./reserva-vuelos.component.css']
})
export class ReservaVuelosComponent implements OnInit {

  model: any = {};
  //var listado vuelos
  listaReservas : {};
  msg:string = '';
  msgex:string = '';	

  constructor(private newService :CommonsService, private router: Router) { }

  ngOnInit() {
  }

  //function get reservas
  sendReservas = function() {
 //recupero id vuelo
  var idVuelo = localStorage.getItem("IdVuelo");
  //recupero el valor del vuelo seleccionado
  var valorVuelo = localStorage.getItem("valorVuelo");
  //get fecha actual
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; 
  var yyyy = today.getFullYear();
  var hoy = yyyy+'-'+mm+'-'+dd;		

  this.newService.sendReservas(this.model.cedula, this.model.nombres,this.model.apellidos,this.model.email,this.model.telefono,this.model.fechanacimiento,hoy,idVuelo,valorVuelo)  
  .subscribe(data =>  {  
  	  this.listaReservas = data;
  	  if(this.listaReservas != null ){
  	  	console.log(this.listaReservas);
  	  	if(this.listaReservas.mensaje == "Guardado satisfactoriamente"){
  	  		this.msgex = this.listaReservas.mensaje;
  	  	}else{
  	  		this.msg = this.listaReservas.mensaje;
  	  	}
  	  	

  	  }
  	  	     
  }   
  , error => this.errorMessage = error )  
  }

  closeAlert():void {
    this.msg = '';
  }

  closeAlertEx():void {
    this.msgex = '';
  } 

  close(){
    //se redirecciona al menu
    this.router.navigate(["/consultaVuelos"]);
  } 


}
