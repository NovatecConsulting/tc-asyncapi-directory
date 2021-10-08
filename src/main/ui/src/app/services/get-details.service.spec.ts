import { TestBed } from '@angular/core/testing';

import { GetDetailsService } from './get-details.service';

describe('GetDetailsService', () => {
  let service: GetDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
