export interface Game {
  id: string;
  state: {
    status: string;
    player1Board: {
      pits: Array<{
        index: number;
        value: number;
      }>;
      kalah: number;
    };
    player2Board: {
      pits: Array<{
        index: number;
        value: number;
      }>;
      kalah: number;
    };
  };
}
