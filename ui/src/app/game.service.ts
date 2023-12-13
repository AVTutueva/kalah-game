import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from './game';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private baseApiUrl = 'http://localhost:8080/v1/api';
  private gameSubject = new BehaviorSubject<Game | null>(null);
  game$ = this.gameSubject.asObservable();

  private disableButtonsSubject = new BehaviorSubject<boolean>(false);
  disableButtons$ = this.disableButtonsSubject.asObservable();

  constructor(private http: HttpClient) {}

  private updateGameState(game: Game): void {
    this.gameSubject.next(game);
  }

  public createGame(): Observable<Game> {
    return this.http.post<Game>(`${this.baseApiUrl}/game`, {}).pipe(
      tap((createdGame: Game) => {
        this.updateGameState(createdGame);
      })
    );
  }

  public makeMove(gameId: string, pitIndex: number): Observable<Game> {
    this.disableButtonsSubject.next(true);
    return this.http
      .put<Game>(`${this.baseApiUrl}/game/${gameId}?pitIndex=${pitIndex}`, {})
      .pipe(
        tap((updatedGame: Game) => {
          this.updateGameState(updatedGame);
          this.disableButtonsSubject.next(false);
        })
      );
  }
}
