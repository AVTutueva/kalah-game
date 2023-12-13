import { Component, Input } from '@angular/core';
import { GameService } from '../service/game.service';
import { Game } from '../model/game';
import { GameStatus } from '../model/gameStatus';

@Component({
  selector: 'app-player1-board',
  templateUrl: './player1-board.component.html',
  styleUrl: './player1-board.component.css',
})
export class Player1BoardComponent {
  @Input() game!: Game;
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
        console.log('make move in Player1board with index ' + pitIndex);
        console.log(updatedGame);
        this.game = updatedGame;
      });
  }

  shouldDisableButton(pitValue: number): boolean {
    return (
      this.disableButtons ||
      pitValue === 0 ||
      this.game.state.status !== GameStatus.PLAYER1_TURN
    );
  }
}
