import {Component, ElementRef, OnDestroy, AfterViewInit, Input, OnInit,} from '@angular/core';
import AsyncApiStandalone from "@asyncapi/react-component/browser/standalone";
import {mock} from "./mock";

@Component({
  selector: 'app-asyncapi-react',
  template: `
    <div id="asyncapi-doc"></div>
  `,
  styleUrls: ['./asyncapi-react.component.css'],
  inputs: ['schema']
})
export class AsyncapiReactComponent implements OnDestroy, OnInit {
  constructor(private element: ElementRef) {}
  stringSchema: string;

  @Input()
  schema: any;

  ngOnInit() {
    this.stringSchema = JSON.stringify(this.schema);
    const schema = this.stringSchema; // AsyncAPI specification, fetched or pasted.
    const config = {}; // Configuration for component. This same as for normal React component
    const container = this.element.nativeElement.querySelector('#asyncapi-doc');
    AsyncApiStandalone.render({ schema, config }, container);
  }

  ngOnDestroy(): void {
    this.element.nativeElement.querySelector('#asyncapi-doc').remove();
  }
}
