# Kalah Game
This web application implements the Kalah game, also known as Mangala. For the general rules of the game, please refer to Wikipedia: [Kalah Rules](https://en.wikipedia.org/wiki/Kalah). The default configuration of the game is 6 stones per pit.


### General rules
- The game is played by 2 people, each with a board that includes a Kalah and a set of 6 Pits.
- Kalah is located on the player's right hand, and sets of pits are located opposite the opponent's set.
- At the start of the game, six stones are placed in each pit.
### Making move
- The player who begins picks up all the stones in any of their own pits and sows the stones to the right, placing one in each of the following pits, including their own Kalah.
- No stones are placed in the opponent's Kalah. If the player's last stone lands in their own Kalah, they get another turn. This can be repeated any number of times before it's the other player's turn.
- If the last stone placed by the player in the pit is the only one there, the player moves it to Kalah, along with all the stones from the opponent’s pit located opposite.
  Scoring
### Scoring
- The game ends when one of the players collects more than half of the stones in the Kalah.
- If, after a move, one of the players has no stones left in their pits, the player with stones moves all the stones from their pits to their Kalah.
- The game can be a draw when both players have the same number of stones.
### Limitations
- Two players have to play on the same screen

# Tech Stack
- Java 17
- Spring Boot 3.2
- Gradle
- Angular 17
- Docker

# How to start game
Clone repository
```
git clone https://github.com/AVTutueva/kalah-game.git
```
Navigate to root and start Docker containers
```
docker-compose up -d
```
After running the Docker containers, browse
http://localhost:4200

#### To run without Docker
Navigate to `api` and do
```
./gradlew build
./gradlew bootRun
```
Navigate to `ui` and do
```
npm install
ng serve
```

#### Run server tests
Navigate to `api`
```
./gradlew test
```

## API documentation
To access API documentation run the project and browse:
http://localhost:8080/v1/api/swagger-ui/index.html

# Author
Aleksandra Tutueva