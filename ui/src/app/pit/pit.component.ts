import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-pit',
  templateUrl: './pit.component.html',
})
export class PitComponent {
  @Input() value!: number;
  @Output() pitClicked = new EventEmitter<void>();
  @Input() disableButton = false;

  onPitClick(): void {
    this.pitClicked.emit();
  }
}
