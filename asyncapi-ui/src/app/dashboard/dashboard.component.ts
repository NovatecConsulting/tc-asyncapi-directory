import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { jsonMock} from './jsonmock';
import { MatCardModule } from '@angular/material/card';
import {UpdateDashboardService} from '../services/update-dashboard.service';
import {GetDetailsService} from '../services/get-details.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  apis: any[] = [];

  constructor(private router: Router,
              private updateDashboardService: UpdateDashboardService) {
    this.updateDashboardService.getAsyncApiSummary().subscribe(a => {
      this.apis = a;
    });
  }

  ngOnInit(): void {
  }

  onGetDetails(artifactId: string) {
    this.router.navigate(['details'], { state: { id: artifactId } });
  }
}
