import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsyncapiDetailsComponent } from './asyncapi-details.component';

describe('AsyncapiDetailsComponent', () => {
  let component: AsyncapiDetailsComponent;
  let fixture: ComponentFixture<AsyncapiDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsyncapiDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsyncapiDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
