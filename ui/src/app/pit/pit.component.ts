import { Component, Input, Output, EventEmitter } from '@angular/core';
import { GameService } from '../service/game.service';

@Component({
  selector: 'app-pit',
  templateUrl: './pit.component.html',
})
export class PitComponent {
  @Input() value!: number;
  @Output() pitClicked = new EventEmitter<void>();
  @Input() disableButton = false;

  constructor(private gameService: GameService) {}

  onPitClick(): void {
    this.pitClicked.emit();
  }
}
