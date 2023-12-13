import { Component, OnInit } from '@angular/core';
import { Game } from './model/game';
import { GameService } from './service/game.service';
import { GameStatus } from './model/gameStatus';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  game: Game | null = null;
  isGameInProgress: boolean = false;

  constructor(public gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.game$.subscribe((game) => {
      this.game = game;
      this.isGameInProgress =
        this.game?.state?.status == GameStatus.PLAYER1_TURN ||
        this.game?.state?.status == GameStatus.PLAYER2_TURN;
    });
  }

  createNewGame(): void {
    this.isGameInProgress = true;
    this.gameService.createGame().subscribe();
  }
}
