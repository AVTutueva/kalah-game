import { Component, Input } from '@angular/core';
import { GameService } from '../game.service';
import { Game } from '../game';

@Component({
  selector: 'app-player2-board',
  templateUrl: './player2-board.component.html',
  styleUrl: './player2-board.component.css',
})
export class Player2BoardComponent {
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
        console.log('make move in Player2board with index ' + pitIndex);
        this.game = updatedGame;
      });
  }
}
