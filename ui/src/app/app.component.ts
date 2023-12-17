import { Component, OnInit } from '@angular/core';
import { Game } from './model/game';
import { GameService } from './service/game.service';
import { GameStatus } from './model/gameStatus';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  game: Game | null = null;
  isGameInProgress: boolean = false;

  constructor(public gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.game$.subscribe((game) => {
      this.game = game;
      const gameStatus = this.game?.state?.status;

      this.isGameInProgress =
        gameStatus == GameStatus.PLAYER1_TURN ||
        gameStatus == GameStatus.PLAYER2_TURN;
    });
  }

  createNewGame(): void {
    this.isGameInProgress = true;
    this.gameService.createGame().subscribe();
  }

  formatGameStatus(): string {
    return this.game?.state?.status
      ? this.game.state.status.replace(/_/g, ' ')
      : '';
  }
}
