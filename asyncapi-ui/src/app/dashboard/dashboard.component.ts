import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {UpdateDashboardService} from '../services/update-dashboard.service';
import {Observable} from "rxjs";
import {FormControl} from "@angular/forms";
import {map, startWith} from "rxjs/operators";
import {Attribute} from "../models/attribute";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  apis: any[];
  displayedApis: any[];
  optionsArtifactId: string[] = [];
  optionsTeam: string[] = [];
  filteredOptions: Observable<string[]>;
  myControl = new FormControl();
  attributes: Attribute[] = [
    {value: 'artifactId-1', viewValue: 'ArtifactID'},
    {value: 'team-0', viewValue: 'Team'}
  ];
  selectedAttribute: string = this.attributes[0].viewValue;
  searchValue: string;

  constructor(private router: Router,
              private updateDashboardService: UpdateDashboardService) {
    this.updateDashboardService.getAsyncApiSummary().subscribe(returnedApis => {
      this.apis = returnedApis;
      this.displayedApis = returnedApis;
      this.optionsArtifactId = returnedApis.map(a => a.artifactId.toString());
      this.optionsTeam = returnedApis.reduce(function(result, value){
        if ('contact' in value.definition.info) {
          result.push(value.definition.info.contact['x-team-name'].toString());
        }
        return result;
      }, [])
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
    this.router.navigate(["details", artifactId]);
  }

  onSearchEnter(searchValue: string) {
    if(this.selectedAttribute == this.attributes[0].viewValue) {
      this.displayedApis = this.apis.filter(str => str.artifactId.toString().includes(searchValue));
    }else if(this.selectedAttribute == this.attributes[1].viewValue) {
      this.displayedApis = this.apis.filter(function (value) {
        if ('contact' in value.definition.info) {
          if(value.definition.info.contact['x-team-name'].toString().includes(searchValue)) {
            return value;
          }
        }
      });
    }
  }

  onChangeFilter(attribute: Attribute) {
    this.selectedAttribute = attribute.viewValue;
    this.displayedApis = this.apis;
    this.myControl.setValue("");
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    if(this.selectedAttribute == this.attributes[0].viewValue) {
      return this.optionsArtifactId.filter(option => option.toLowerCase().includes(filterValue));
    }else if(this.selectedAttribute == this.attributes[1].viewValue) {
      return this.optionsTeam.filter(option => option.toLowerCase().includes(filterValue));
    }
  }
}
