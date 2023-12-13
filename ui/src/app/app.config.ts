import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app.routes';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { PitComponent } from './pit/pit.component';
import { PlayerBoardComponent } from './player-board/player-board.component';

@NgModule({
  declarations: [AppComponent, PitComponent, PlayerBoardComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
