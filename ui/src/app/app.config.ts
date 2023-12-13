// import { ApplicationConfig } from '@angular/core';
// import { provideRouter } from '@angular/router';

// import { routes } from './app.routes';

// export const appConfig: ApplicationConfig = {
//   providers: [provideRouter(routes)]
// };

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app.routes';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { PitComponent } from './pit/pit.component';
import { Player1BoardComponent } from './player1-board/player1-board.component';
import { Player2BoardComponent } from './player2-board/player2-board.component';

@NgModule({
  declarations: [
    AppComponent,
    PitComponent,
    Player1BoardComponent,
    Player2BoardComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
