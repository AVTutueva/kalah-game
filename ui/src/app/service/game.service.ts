import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from '../model/game';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private baseApiUrl = 'http://localhost:8080/v1/api/game';

  private gameSubject = new BehaviorSubject<Game | null>(null);
  game$ = this.gameSubject.asObservable();

  private isMoveInProgressSubject = new BehaviorSubject<boolean>(false);
  isMoveInProgrees$ = this.isMoveInProgressSubject.asObservable();

  constructor(private http: HttpClient) {
    this.handleError = this.handleError.bind(this);
  }

  private setGameState(game: Game): void {
    this.gameSubject.next(game);
  }

  private setMoveInProgress(flag: boolean): void {
    this.isMoveInProgressSubject.next(flag);
  }

  public createGame(): Observable<Game> {
    return this.http.post<Game>(`${this.baseApiUrl}`, {}).pipe(
      tap((gameState: Game) => {
        this.setGameState(gameState);
      })
    );
  }

  public makeMove(gameId: string, pitIndex: number): Observable<Game> {
    this.setMoveInProgress(true);
    return this.http
      .put<Game>(`${this.baseApiUrl}/${gameId}?pitIndex=${pitIndex}`, {})
      .pipe(
        tap((updatedGame: Game) => {
          this.setGameState(updatedGame);
          this.setMoveInProgress(false);
        }),
        catchError(this.handleError)
      );
  }

  private handleError(err: HttpErrorResponse): Observable<never> {
    const status = err.status;
    let errorMessage: string = 'Something went wrong';
    const askingMessage = '. Please, Create a new game';

    if (status === 404) {
      errorMessage = 'Game Not Found';
    }
    if (status === 400) {
      errorMessage = 'Move was incrorrect';
    }
    const errorStatus = errorMessage + askingMessage;
    this.setErrorAsStatus(errorStatus);
    this.setMoveInProgress(false);
    return throwError(() => err);
  }

  private setErrorAsStatus(status: string) {
    const currentGame = this.gameSubject.getValue();
    if (currentGame) {
      const updatedGame: Game = {
        ...currentGame,
        state: {
          ...currentGame.state,
          status: status,
        },
      };
      this.setGameState(updatedGame);
    }
  }
}
