import { TestBed } from '@angular/core/testing';

import { PivotService } from './pivot.service';

describe('PivotService', () => {
  let service: PivotService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PivotService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
