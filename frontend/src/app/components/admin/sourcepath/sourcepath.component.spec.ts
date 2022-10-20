import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourcepathComponent } from './sourcepath.component';

describe('SourcepathComponent', () => {
  let component: SourcepathComponent;
  let fixture: ComponentFixture<SourcepathComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourcepathComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourcepathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
