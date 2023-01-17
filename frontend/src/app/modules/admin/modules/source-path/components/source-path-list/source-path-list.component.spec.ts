import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourcePathListComponent } from './source-path-list.component';

describe('SourcePathListComponent', () => {
  let component: SourcePathListComponent;
  let fixture: ComponentFixture<SourcePathListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourcePathListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourcePathListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
