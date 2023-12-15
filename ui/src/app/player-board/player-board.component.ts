import { Component, Input } from '@angular/core';
import { Game } from '../model/game';
import { GameStatus } from '../model/gameStatus';
import { GameService } from '../service/game.service';

@Component({
  selector: 'app-player-board',
  templateUrl: './player-board.component.html',
})
export class PlayerBoardComponent {
  @Input() game!: Game;
  @Input() playerIndex!: number;
  isBoardDisabled = false;

  constructor(private gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.isMoveInProgrees$.subscribe((disableButtons) => {
      this.isBoardDisabled = disableButtons;
    });
  }

  makeMove(pitIndex: number): void {
    this.gameService
      .makeMove(this.game.id, pitIndex)
      .subscribe((updatedGame) => {
        console.log('make move ' + this.playerIndex + 'with index ' + pitIndex);
        console.log(updatedGame);
        this.game = updatedGame;
      });
  }

  disableBoardButtons(pitValue: number): boolean {
    return this.isBoardDisabled || pitValue === 0 || this.isPlayerTurn();
  }

  isPlayerTurn(): boolean {
    const gameStatus = this.game?.state?.status;

    const currentPlayerStatus =
      this.playerIndex === 1
        ? gameStatus !== GameStatus.PLAYER1_TURN
        : gameStatus !== GameStatus.PLAYER2_TURN;

    return currentPlayerStatus;
  }

  getOrderedList(): any[] {
    const gameState = this.game.state;

    const pits =
      this.playerIndex === 1
        ? gameState.player1Board.pits
        : gameState.player2Board.pits.slice().reverse();

    return pits;
  }
}
