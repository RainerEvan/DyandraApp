import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSourcepathComponent } from './add-sourcepath.component';

describe('AddSourcepathComponent', () => {
  let component: AddSourcepathComponent;
  let fixture: ComponentFixture<AddSourcepathComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSourcepathComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSourcepathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
