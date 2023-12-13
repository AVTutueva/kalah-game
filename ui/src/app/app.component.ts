import { Component, OnInit } from '@angular/core';
import { Game } from './game';
import { GameService } from './game.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  game: Game | null = null;

  constructor(public gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.game$.subscribe((game) => {
      this.game = game;
    });
  }

  createNewGame(): void {
    this.gameService.createGame().subscribe();
  }
}
