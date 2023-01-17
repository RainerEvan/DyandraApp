import { TestBed } from '@angular/core/testing';

import { SourcePathService } from './source-path.service';

describe('SourcePathService', () => {
  let service: SourcePathService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SourcePathService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
