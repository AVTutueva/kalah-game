import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-kalah',
  templateUrl: './kalah.component.html',
})
export class KalahComponent {
  @Input() value!: number;
}
