import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaReservasComponent } from './consulta-reservas.component';

describe('ConsultaReservasComponent', () => {
  let component: ConsultaReservasComponent;
  let fixture: ComponentFixture<ConsultaReservasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultaReservasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultaReservasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
