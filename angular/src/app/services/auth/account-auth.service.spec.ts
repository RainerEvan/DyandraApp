import { TestBed } from '@angular/core/testing';

import { AccountAuthService } from './account-auth.service';

describe('AccountAuthService', () => {
  let service: AccountAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
