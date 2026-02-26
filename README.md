BlackJack Counter (Kártyaszámoló Program)

BlackJack Counter is a Java-based desktop application designed to teach users how to count cards in Blackjack using the Hi-Lo strategy. Inspired by the movie 21, this program offers various difficulty levels to practice tracking the running count of a deck.
+1

📖 Table of Contents
About the Project

Features

Game Modes

How to Play

Project Structure

Technologies

🧐 About the Project
The application simulates a Blackjack deck environment to help users practice the Hi-Lo counting system. In this system:

+1: Cards 2 through 6.

0: Cards 7 through 9.

-1: 10, Jack, Queen, King, Ace.

The goal is to maintain an accurate running count to determine the player's statistical advantage.

✨ Features
Multiple Difficulty Levels: Includes Learning, Beginner, and Advanced modes.

Customizable Deck Count: Users can select between 2 and 6 decks for gameplay.

Score Tracking: Tracks how many cards the user successfully counted. High scores are saved and displayed in a table.

State Recovery: The program can continue a game in case of an unexpected stop.

Graphical Interface: Built with Java Swing, featuring card visualizations.
+2

🎮 Game Modes
1. Learning Mode (Tanuló)

Goal: Learn the basics of assigning values to cards.

Mechanics: Cards appear one by one. The current "Running Count" is displayed at the bottom of the screen, updating automatically based on the card shown.

Failure Condition: None. The user stops the game manually.

2. Beginner Mode (Kezdő)

Goal: Practice maintaining the count without visual aid.

Mechanics: The count is hidden. Every 4-5 cards, the game pauses and presents a multiple-choice question (3 options) asking for the current count.

Lives: The player has 3 lives. A wrong answer deducts a life.
+1

Failure Condition: The game ends after 3 incorrect answers.

3. Advanced Mode (Haladó)

Goal: Master the count under pressure.

Mechanics: Similar to Beginner mode, but there are no multiple-choice options. The user must type the exact count into a text field every 4-5 cards.

Failure Condition: "Sudden Death" — the game ends immediately upon the first wrong answer.
+1

🚀 How to Play
Launch the Application: The main menu will appear with options for the game modes and the scoreboard.

Select a Mode: Choose between Tanuló (Learning), Kezdő (Beginner), or Haladó (Advanced).

Configure Decks: Enter the number of decks you wish to play with (Input range: 2–6).

Controls:

Next Card: Press the Space bar or click the "Következő Kártya" button.

Exit/Save: Click "Kilépés és Mentés" to stop and save your progress.
+1

Scoring: Your score represents the number of cards you successfully counted before the game ended or the deck ran out.

🏗 Project Structure
The project follows an Object-Oriented design using the following key classes:

App / Menu: Handles the GUI and main menu navigation.
+2

Card: Represents a single card with a value and image.

Deck: Manages the collection of cards using an ArrayList and handles shuffling via Collections.shuffle().
+1

Jatek (Abstract Class): The base class for game logic, containing the Deck.
+1

LearningGame: Implements learning mode logic.

Beginer: Implements beginner logic (lives, multiple choice).

AdvancedGame: Implements advanced logic (text input, instant loss).

ScoreManager: Handles saving and loading scores to a file.

Score: Represents a player's score object.

💻 Technologies
Language: Java 
+1

GUI Framework: Swing (JFrame, JPanel, JButton) 
+1

Persistence: File I/O for saving scores 

Author: Dénes Péter István


