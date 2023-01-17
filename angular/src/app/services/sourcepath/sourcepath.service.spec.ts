import { TestBed } from '@angular/core/testing';

import { SourcepathService } from './sourcepath.service';

describe('SourcepathService', () => {
  let service: SourcepathService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SourcepathService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
