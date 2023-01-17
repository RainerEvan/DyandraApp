import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionDetailComponent } from './connection-detail.component';

describe('ConnectionDetailComponent', () => {
  let component: ConnectionDetailComponent;
  let fixture: ComponentFixture<ConnectionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConnectionDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
