import { TestBed } from '@angular/core/testing';

import { UpdateDashboardService } from './update-dashboard.service';

describe('UpdateDashboardService', () => {
  let service: UpdateDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
