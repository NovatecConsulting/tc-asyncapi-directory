import { Component } from "@angular/core";
import { Router } from '@angular/router';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent {
  title = 'AsyncAPI';
  navLinks: any[];
  activeLinkIndex = -1;

  constructor(private router: Router) {
    this.navLinks = [
                    { link: './details', label: 'AsyncAPI Details'}
    ]
  }

  ngOnInit(): void {
  }
}
