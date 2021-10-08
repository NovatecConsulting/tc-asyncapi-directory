import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-raw-content',
  templateUrl: './raw-content.component.html',
  styleUrls: ['./raw-content.component.css']
})
export class RawContentComponent implements OnInit {
  @Input()
  schema: any;

  isReadMore = true
  icon = "expand_more";

  showText() {
    if(this.icon == "expand_more") {
      this.icon = "expand_less";
    }else {
      this.icon = "expand_more";
    }

    this.isReadMore = !this.isReadMore
  }

  constructor() { }

  ngOnInit(): void {
  }

}
