import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RawContentComponent } from './raw-content.component';

describe('RawContentComponent', () => {
  let component: RawContentComponent;
  let fixture: ComponentFixture<RawContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RawContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RawContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
