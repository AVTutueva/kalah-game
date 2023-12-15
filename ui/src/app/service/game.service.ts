import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from '../model/game';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private baseApiUrl = 'http://localhost:8080/v1/api/game';

  private gameSubject = new BehaviorSubject<Game | null>(null);
  game$ = this.gameSubject.asObservable();

  private isMoveInProgressSubject = new BehaviorSubject<boolean>(false);
  isMoveInProgrees$ = this.isMoveInProgressSubject.asObservable();

  constructor(private http: HttpClient) {}

  private updateGameState(game: Game): void {
    this.gameSubject.next(game);
  }

  public createGame(): Observable<Game> {
    return this.http.post<Game>(`${this.baseApiUrl}`, {}).pipe(
      tap((createdGame: Game) => {
        this.updateGameState(createdGame);
      })
    );
  }

  public makeMove(gameId: string, pitIndex: number): Observable<Game> {
    this.isMoveInProgressSubject.next(true);
    return this.http
      .put<Game>(`${this.baseApiUrl}/${gameId}?pitIndex=${pitIndex}`, {})
      .pipe(
        tap((updatedGame: Game) => {
          this.updateGameState(updatedGame);
          this.isMoveInProgressSubject.next(false);
        })
      );
  }
}
