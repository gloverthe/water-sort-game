# Water sort game

Water sort game is a Java-based game, built using LibGDX where the objective is to sort colored water into tubes. The game includes various features such as collision detection, color-based sorting logic, and sound effects for pouring.

This is a work in progress, the game loop is finished but needs polish, with a UI and more levels.

## Project Structure

- `core/src/com/mygdx/game/`: Contains the main game logic and classes.
  - `DrawTube.java`: Handles the drawing of tubes.
  - `MainGame.java`: The main game loop and input processing.
  - `TestTube.java`: Handles test tube drawing for debugging.
  - `Constants.java`: Defines game constants such as screen dimensions.
  - `ReadInLevels.java`: Reads level data from a JSON file.
  - `LevelReader.java`: Provides methods to parse and return level data.
  - `TestTubeSegments.java`: Handles drawing of segmented tubes.
  - `WaterSortPuzzle.java`: Contains the core puzzle logic.
  - `ReturnTubes.java`: Manages the rendering and positioning of tubes.

## Installation

To run this project, you need to have Java and the LibGDX framework installed.

1. Clone the repository:
```sh
git clone https://github.com/gloverthe/SonjasWaterSort.git
```

2. Navigate to the project directory:
```sh
cd SonjasWaterSort
```

3. Import the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

4. Configure the project to use the LibGDX framework.

## Usage

1. Run the `MainGame.java` class to start the game.
2. Use the mouse to interact with the tubes. Click on a tube to select it, and then click on another tube to pour the water.
3. The game will provide visual feedback and sound effects for valid moves.

## Features

- **Tube Drawing**: The `DrawTube` and `TestTube` classes handle the drawing of tubes with different colors.
- **Puzzle Logic**: The `WaterSortPuzzle` class contains the logic for sorting the water based on color.
- **Level Reading**: The `ReadInLevels` and `LevelReader` classes provide functionality to read level data from a JSON file.
- **Sound Effects**: The game includes sound effects for pouring water.

## Contributors

- **gloverthe** - [gloverthe](https://github.com/gloverthe)

## License

This project does not currently have a license. Please add a license to the repository to clarify the terms under which the code can be used.

