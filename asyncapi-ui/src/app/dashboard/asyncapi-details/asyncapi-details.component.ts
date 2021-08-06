import { Component, OnInit } from '@angular/core';
import '@asyncapi/web-component/lib/asyncapi-web-component';
import {ActivatedRoute, Router} from '@angular/router';
import {GetDetailsService} from '../../services/get-details.service';

@Component({
  selector: 'app-asyncapi-details',
  templateUrl: './asyncapi-details.component.html',
  styleUrls: ['./asyncapi-details.component.css']
})
export class AsyncapiDetailsComponent implements OnInit {
  schema: string;

  constructor(
      private getDetailsService: GetDetailsService,
      private route: Router) {
    this.getDetailsService.getSpecificApiDefinition(route.getCurrentNavigation().extras.state.id).subscribe(s => {
      this.schema = s;
    });
  }

  ngOnInit(): void {
  }
}
