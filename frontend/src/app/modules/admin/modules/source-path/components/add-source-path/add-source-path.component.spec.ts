import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSourcePathComponent } from './add-source-path.component';

describe('AddSourcePathComponent', () => {
  let component: AddSourcePathComponent;
  let fixture: ComponentFixture<AddSourcePathComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSourcePathComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSourcePathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
