=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: mkong
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays:
  The dimensions of the game board is 300x300, so a 300x300 2D array was created.
  Each time a powerup was placed into a given position, it's corresponding location
  in the 2D array was set to "1". By default, every location is "0". This is an
  important feature to make sure that no two powerups in the same powerup queue are
  placed in the same location. A 2D array accomplishes this well since it is quite
  straightforward to represent the game board with a 2D array.

  2. Collections:
  Collections were used in multiple places throughout the game. A LinkedList was
  used for the powerup queue. A LinkedList made the most sense since we only ever
  want to access the head element at any given time. Another LinkedList was used 
  to model the snake. Each snake tail component is appended to the end of the 
  LinkedList. It made sense to use a LinkedList because each element is dependent
  on the elements directly in front of and behind it. Also, a TreeMap was used to
  represent the high scores. A map was used because each high score has the name of
  the player (the key) and the player's score (the value).

  3. I/O
  The top five highest scores as well as the name of the player associated with 
  each score will be written to a text file. These scores will be viewable in game 
  by opening the high scores menu. The data from the text file is parsed and displayed. 
  After each game ends, the name of the player as well as his/her score is added to 
  the high scores list if it is a top five score.

  4. Inheritance:
  The 3 different types of powerups are subclasses of the PowerUp class. This is
  necessary because each powerup all have the same type, but the each do different
  things.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Direction:
  Simple enum that represents the four directions: Up, Down, Left, and Right.
  
  Game:
  The driver class containing the main method.
  
  GameCourt:
  Responsible for updating the state of the game for each tick.
  
  GameObj:
  Superclass for snake objects.
  
  SnakeHead:
  The head of the snake. Determines what position the tail elements follow.
  
  SnakeTail:
  Represents all elements of the snake that are not the head. Each snake tail
  follows the tail in front of it.
  
  PowerUp:
  Superclass for game powerups.
  
  GrowOne:
  Extends PowerUp. Increases length of snake by one.
  
  GrowTwo:
  Extends PowerUp. Increases length of snake by two.
  
  ShrinkOne:
  Extends PowerUp. Decreases length of snake by one.
  
  Position:
  Position objects represent a specific location on the game board.
  
  HighScores:
  Responsible for updating and reading the high scores text file.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  The biggest stumbling block encountered was figured out how to get the tail
  elements of the snake to all follow the tail block directly in front, rather
  than have all of them directly follow the head of the snake.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  Overall I think that there is a good separation of functionality. A plethora
  of classes were created to represent different objects. In addition, I think that
  the game state was modelled extremely well. Object fields were declared private,
  and appropriate accessor and mutator methods were implemented, so private state 
  was well encapsulated.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  Javadocs were frequently referenced (especially for JOptionPane).