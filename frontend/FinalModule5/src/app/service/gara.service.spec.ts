import { TestBed } from '@angular/core/testing';

import { GaraService } from './gara.service';

describe('GaraService', () => {
  let service: GaraService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GaraService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
