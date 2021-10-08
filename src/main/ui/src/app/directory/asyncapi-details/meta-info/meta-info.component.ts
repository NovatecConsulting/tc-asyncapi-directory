import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-meta-info',
  templateUrl: './meta-info.component.html',
  styleUrls: ['./meta-info.component.css']
})
export class MetaInfoComponent implements OnInit {
  @Input()
  schema: any;

  constructor() { }

  ngOnInit(): void {
  }

}
