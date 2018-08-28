import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu/menu.component';
import { ConsultaVuelosComponent } from './consulta-vuelos/consulta-vuelos.component';
import { ConsultaReservasComponent } from './consulta-reservas/consulta-reservas.component';
import { ReservaVuelosComponent } from './reserva-vuelos/reserva-vuelos.component';

//const routes
const routes: Routes = [
		{
            path: '',
            component: MenuComponent,
        },
        {
            path: 'consultaVuelos',
            component: ConsultaVuelosComponent,
        },
        {
            path: 'consultaReservas',
            component: ConsultaReservasComponent,
        },
        {
            path: 'reservaVuelos',
            component: ReservaVuelosComponent,
        }
    ];


    @NgModule({
        imports: [
            RouterModule.forRoot(routes)
        ],
        exports: [
            RouterModule
        ],
        declarations: []
    })
    export class AppRoutingModule { }
