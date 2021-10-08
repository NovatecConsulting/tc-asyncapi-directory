import { TestBed } from '@angular/core/testing';

import { UpdateDirectoryService } from './update-directory.service';

describe('UpdateDirectoryService', () => {
  let service: UpdateDirectoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateDirectoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
