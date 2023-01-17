import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourcepathDetailsComponent } from './sourcepath-details.component';

describe('SourcepathDetailsComponent', () => {
  let component: SourcepathDetailsComponent;
  let fixture: ComponentFixture<SourcepathDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourcepathDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourcepathDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
