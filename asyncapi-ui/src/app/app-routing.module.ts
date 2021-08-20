import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AsyncapiDetailsComponent } from './dashboard/asyncapi-details/asyncapi-details.component';

const routes: Routes = [
  { path: '', redirectTo: '/directory', pathMatch: 'full' },
  { path: 'directory', component: DashboardComponent },
  { path: 'directory/details/:id', component: AsyncapiDetailsComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
