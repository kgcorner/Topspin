import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './auth/auth-guard';
import { MerchantsModule } from './modules/merchants/merchants.module';
import { AuthService } from './services/auth.service';
import { OffersModule } from './modules/offers/offers.module';
import { FroalaEditorModule, FroalaViewModule } from 'angular-froala-wysiwyg';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MerchantsModule,
    OffersModule,
    StoreModule.forRoot([]),
    EffectsModule.forRoot([]),
    FroalaEditorModule.forRoot(),
    FroalaViewModule.forRoot()    
  ],
  providers: [
    AuthGuard,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
