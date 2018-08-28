import { Component, OnInit } from '@angular/core';
import {CommonsService} from '../commons.service';
import {Http,Response, Headers, RequestOptions } from '@angular/http';
import { Router, ActivatedRoute } from '@angular/router';  

@Component({
  selector: 'app-consulta-reservas',
  templateUrl: './consulta-reservas.component.html',
  styleUrls: ['./consulta-reservas.component.css']
})
export class ConsultaReservasComponent implements OnInit {
   msg:string = '';
  //
  model: any = {};
  //var listado reservaciones
  listaReservaciones : {};

  activarConsulta = false;

  constructor(private newService :CommonsService, private router: Router) { }

  ngOnInit() {
   this.activarConsulta = false;
  }

  onGetReservas = function() {
  	 this.newService.getReservas(this.model.cedula)  
     .subscribe(data =>  {  
  	  this.listaReservaciones = data;
  	  if(this.listaReservaciones.length > 0 ){
  	  	this.activarConsulta = true;
  	  	console.log(this.listaReservaciones);
  	  }else{
        this.activarConsulta = false;
        this.msg = 'Error No existen registros';
      }
  	  	     
  }   
  , error => this.errorMessage = error )  
  }
  

   closeAlert():void {
    this.msg = '';
  }
  
  close(){
    //se redirecciona al menu
    this.router.navigate(["/"]);
  } 

}
