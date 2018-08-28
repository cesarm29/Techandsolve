import { Component, OnInit } from '@angular/core';
import {CommonsService} from '../commons.service';
import {Http,Response, Headers, RequestOptions } from '@angular/http';
import { Router, ActivatedRoute } from '@angular/router';   

@Component({
  selector: 'app-consulta-vuelos',
  templateUrl: './consulta-vuelos.component.html',
  styleUrls: ['./consulta-vuelos.component.css']
})
export class ConsultaVuelosComponent implements OnInit {

  msg:string = '';
  //
  model: any = {};
  //var listado vuelos
  listaVuelos : {};

  activarConsulta = false;

  constructor(private newService :CommonsService, private router: Router) { }

  ngOnInit() {
    this.activarConsulta = false;
  }

  //function get vuelos
  getVuelos = function() {   
  this.newService.getVuelos(this.model.destino,this.model.fecha)  
  .subscribe(data =>  {  
  	  this.listaVuelos = data;
  	  if(this.listaVuelos.length > 0 ){
  	  	this.activarConsulta = true;
  	  	console.log(this.listaVuelos);
  	  }else{
        this.activarConsulta = false;
        this.msg = 'Error No existen registros';
      }
  	  	     
  }   
  , error => this.errorMessage = error )  
  }

 reservas(idVuelo, valorVuelo) {
    //almacenamos el id vuelo 
    localStorage.setItem('IdVuelo', idVuelo);
    //almacenamos el id vuelo 
    localStorage.setItem('valorVuelo', valorVuelo);
    //se redirecciona al modulo reservas
    this.router.navigate(["/reservaVuelos"]);
  }

  closeAlert():void {
    this.msg = '';
  }

  close(){
    //se redirecciona al menu
    this.router.navigate(["/"]);
  } 
  

}
