import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { ConsultaVuelosComponent } from './consulta-vuelos/consulta-vuelos.component';
import { ConsultaReservasComponent } from './consulta-reservas/consulta-reservas.component';
import { ReservaVuelosComponent } from './reserva-vuelos/reserva-vuelos.component';
import { MenuComponent } from './menu/menu.component';
import { AppRoutingModule } from './/app-routing.module';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import {CommonsService} from './commons.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

@NgModule({
  declarations: [
    AppComponent,
    ConsultaVuelosComponent,
    ConsultaReservasComponent,
    ReservaVuelosComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MDBBootstrapModule.forRoot(),   
    HttpModule,
    FormsModule,
    HttpClientModule
  ],
  schemas: [ NO_ERRORS_SCHEMA ],
  providers: [CommonsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
