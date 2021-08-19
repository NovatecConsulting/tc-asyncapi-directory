import { BrowserModule } from "@angular/platform-browser";
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule,FormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DatePipe } from '@angular/common';

import { AppComponent } from "./app.component";
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from "./material/material.module";
import { MatCardModule} from "@angular/material/card";
import { DashboardComponent } from './dashboard/dashboard.component';
import { AsyncapiDetailsComponent } from './dashboard/asyncapi-details/asyncapi-details.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { MetaInfoComponent } from './dashboard/asyncapi-details/meta-info/meta-info.component';
import { AsyncapiReactComponent } from './dashboard/asyncapi-details/asyncapi-react/asyncapi-react.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AsyncapiDetailsComponent,
    MetaInfoComponent,
    AsyncapiReactComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MatCardModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }

