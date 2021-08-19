import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsyncapiReactComponent } from './asyncapi-react.component';

describe('AsyncapiReactComponent', () => {
  let component: AsyncapiReactComponent;
  let fixture: ComponentFixture<AsyncapiReactComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsyncapiReactComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsyncapiReactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
