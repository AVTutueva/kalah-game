import { Component, Input } from '@angular/core';
import { GameService } from '../game.service';
import { Game } from '../game';

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
    this.gameService.disableButtons$.subscribe((disableButtons) => {
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
}
