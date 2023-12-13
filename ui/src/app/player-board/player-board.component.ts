import { Component, Input } from '@angular/core';
import { Game } from '../model/game';
import { GameStatus } from '../model/gameStatus';
import { GameService } from '../service/game.service';

@Component({
  selector: 'app-player-board',
  templateUrl: './player-board.component.html',
  styleUrl: './player-board.component.css',
})
export class PlayerBoardComponent {
  @Input() game!: Game;
  @Input() playerIndex!: number;
  disableButtons = false;

  constructor(private gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.isGameInProgrees$.subscribe((disableButtons) => {
      this.disableButtons = disableButtons;
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

  shouldDisableButton(pitValue: number): boolean {
    return this.disableButtons || pitValue === 0 || this.isPlayerTurn();
  }

  isPlayerTurn(): boolean {
    if (this.playerIndex === 1) {
      return this.game.state.status !== GameStatus.PLAYER1_TURN;
    } else return this.game.state.status !== GameStatus.PLAYER2_TURN;
  }

  getOrderedList(): any[] {
    if (this.playerIndex === 1) {
      return this.game.state.player1Board.pits;
    } else {
      return this.game.state.player2Board.pits.slice().reverse();
    }
  }

  getKalahValue(): number {
    return this.playerIndex === 1
      ? this.game.state.player1Board.kalah
      : this.game.state.player2Board.kalah;
  }
}
