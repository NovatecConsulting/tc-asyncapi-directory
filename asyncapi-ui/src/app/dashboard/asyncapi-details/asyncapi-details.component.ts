import {Component, CUSTOM_ELEMENTS_SCHEMA, NgModule, OnInit} from '@angular/core';
import '@asyncapi/web-component/lib/asyncapi-web-component';
import {ActivatedRoute, Router} from '@angular/router';
import {GetDetailsService} from '../../services/get-details.service';
import {Subscription} from "rxjs";
import {MetaInfoComponent} from "./meta-info/meta-info.component";

@Component({
  selector: 'app-asyncapi-details',
  templateUrl: './asyncapi-details.component.html',
  styleUrls: ['./asyncapi-details.component.css']
})

export class AsyncapiDetailsComponent implements OnInit {
  schema: any;
  numbers: any[] = [];
  currentVersion: number;
  artifactId: any;

  private routeSub: Subscription;

  constructor(private getDetailsService: GetDetailsService,
              private route: Router,
              private activatedRoute: ActivatedRoute) {

  }

  ngOnInit() {
    this.routeSub = this.activatedRoute.params.subscribe(params => {
      this.artifactId = params['id'];
    });

    this.getDetailsService.getLatestApiDefinition(this.artifactId)
      .subscribe(s => {
        this.schema = s.definition;
      });

    this.getDetailsService.getLatestVersionNumber(this.artifactId)
      .subscribe(v => {
        this.numbers.length = 0;
        let obj = JSON.parse(v);
        this.currentVersion = obj.highestVersion;
        this.numbers = Array(obj.highestVersion).fill(0).map((x, i) => i + 1);
      });
  }

  onChangeVersion(newVersion: number) {
    this.getDetailsService.getSpecificApiDefinition(this.artifactId, newVersion)
      .subscribe(s => {
        this.schema = s.definition;
        this.currentVersion = newVersion;
    });
  }
}
