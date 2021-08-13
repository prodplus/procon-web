import {
  APP_BASE_HREF,
  CommonModule,
  HashLocationStrategy,
  LocationStrategy,
} from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgxViacepModule } from '@brunoc/ngx-viacep';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {
  GoogleChartsModule,
  GOOGLE_CHARTS_LAZY_CONFIG,
} from 'angular-google-charts';
import { NgxMaskModule } from 'ngx-mask';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import {
  googleChartsConfigFactory,
  GoogleChartsConfigService,
} from './services/charts.service';
import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [AppComponent, HeaderComponent, HomeComponent, LoginComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    NgxMaskModule.forRoot(),
    NgxViacepModule,
    NgbModule,
    GoogleChartsModule,
    SharedModule,
    CoreModule,
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    GoogleChartsConfigService,
    {
      provide: GOOGLE_CHARTS_LAZY_CONFIG,
      useFactory: googleChartsConfigFactory,
      deps: [GoogleChartsConfigService],
    },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent],
})
export class AppModule {}
