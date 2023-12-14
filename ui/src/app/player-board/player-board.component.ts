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
    this.gameService.isGameInProgrees$.subscribe((disableButtons) => {
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

  disableBoard(pitValue: number): boolean {
    return this.isBoardDisabled || pitValue === 0 || this.isPlayerTurn();
  }

  isPlayerTurn(): boolean {
    const currentPlayerStatus =
      this.playerIndex === 1
        ? this.game.state.status !== GameStatus.PLAYER1_TURN
        : this.game.state.status !== GameStatus.PLAYER2_TURN;

    return currentPlayerStatus;
  }

  getOrderedList(): any[] {
    const pits =
      this.playerIndex === 1
        ? this.game.state.player1Board.pits
        : this.game.state.player2Board.pits.slice().reverse();

    return pits;
  }
}
