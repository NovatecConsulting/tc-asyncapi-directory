import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { jsonMock} from './jsonmock';
import { MatCardModule } from '@angular/material/card';
import {UpdateDashboardService} from '../services/update-dashboard.service';
import {GetDetailsService} from '../services/get-details.service';
import {Observable} from "rxjs";
import { of } from 'rxjs';
import {FormControl} from "@angular/forms";
import {map, startWith} from "rxjs/operators";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  apis: any[];
  displayedApis: any[];
  options: string[] = [];
  searchValue: string;
  filteredOptions: Observable<string[]>;
  myControl = new FormControl();

  constructor(private router: Router,
              private updateDashboardService: UpdateDashboardService) {
    this.updateDashboardService.getAsyncApiSummary().subscribe(returnedApis => {
      this.apis = returnedApis;
      this.displayedApis = returnedApis;
      this.options = returnedApis.map(a => a.artifactId.toString());
    });
  }

  ngOnInit(): void {
    this.filteredOptions = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  onGetDetails(artifactId: string) {
    this.router.navigate(['details'], { state: { id: artifactId } });
  }

  onSearchEnter(searchValue: string) {
    this.displayedApis = this.apis.filter(str => str.artifactId.toString().includes(searchValue));
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }
}
