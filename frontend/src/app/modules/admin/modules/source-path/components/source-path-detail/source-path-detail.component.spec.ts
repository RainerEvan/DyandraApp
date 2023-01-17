import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourcePathDetailComponent } from './source-path-detail.component';

describe('SourcePathDetailComponent', () => {
  let component: SourcePathDetailComponent;
  let fixture: ComponentFixture<SourcePathDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourcePathDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourcePathDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
