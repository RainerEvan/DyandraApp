import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormatDialogComponent } from './format-dialog.component';

describe('FormatDialogComponent', () => {
  let component: FormatDialogComponent;
  let fixture: ComponentFixture<FormatDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormatDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormatDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
