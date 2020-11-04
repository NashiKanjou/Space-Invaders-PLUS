# Project Overview

# Vision Statement
FOR gamers and ordinary people WHO need to pass time, take a quick fun break, or to enjoy themselves playing video games. SPACE INVADERS PLUS is a Java-based video game THAT provides a simple, retro game experience, fit for any skill level, and having basic and straightforward playability for an uncomplicated gaming experience. UNLIKE most modern games which involve thorough and complex tutorials, gameplay mechanics, and a higher-end skill level, OUR product provides a fun and relaxing experience for all ages, skill levels, and most importantly, situations, where jumping in and out of the game is seamless and requires no thought, allowing one to have fun thoughtlessly.

# Personas
## Raymond
Joseph, age 45, is a video game developer for Capcom. Joseph is one of the lead designers for Capcom’s Street Fighter series. He grew up in the era when arcade games such as Pac-Man, Donkey Kong, and Space Invaders were gaining popularity during the early 1980s. These influential arcade games are what started Joseph’s love for video games which later lead to him pursuing a career in video game development. He is very interested in our project Space Invaders PLUS as this game has been very influential in not only his life but the entire gaming industry. Joseph is excited to experience our revamped version of Space Invaders and he cannot wait to see the new features and additions we have added to the game. 

## Chin
Arusu, age 24, is a game designer in Compile Heart. She has a diploma in computer science from Tokyo Institute of Technology. She went to several software companies in Japan. Since she loves games more, she joined the Compile Heart and become a game designer.
Before Arusu actually join a game company, she does not only do programming for work, she also had her own club creating games as a hobby. She released several mini games and those games have lots of positive reviews. Arusu is interested in Space Invaders Plus and want to try and test the game.

## Misha
Anna, age 27, is a lead mobile application software developer for a mid-level company based out of London, England. Her company works on various mobile application projects, mostly games, which are to be implemented and ported onto mobile versions. She manages a group of 20 software developers at her company. She has a Bachelor's Degree from King's College in London, having graduated with a 3.8 GPA. She commutes home where she lives with her family in order to help them out financially. She has a variety of skills, including Android development, Swift development, as well as having worked in numerous game development companies prior to this one.
Anna's experience as a mobile and game developer, both in past and present, allows her to be comfortable and skilled in what she does currently. She enjoys the fact that games allow people to relax and wind down after a long day, and that she can help create them and gift that experience to people. Due to having worked on many complex projects and have seen their outcome, Anna believes that less complex mobile games have a higher success and enjoyability rate than their complex counterparts. She is extremely interested in using and getting to port Space Invaders to a mobile version, as she believes the simple, seamless, and easily-learnable experience of the game is what will allow its success and great reviews amongst the general public.

# Trello/Features Link
##[Trello](https://trello.com/b/qEVfP4xu/space-invaders-plus)

# Project Description

The project is a Java implementation of the classic game “Space Invaders”. There are enemies contained within a rectangular formation, that shoot projectiles towards the player’s direction, which is at the bottom of the screen. The player is free to move around in the horizontal plane, dodging the projectiles shot by the enemies. The goal of the game is to destroy all of the enemies with one’s own projectiles, without being hit by a projectile, and do so in a timely fashion. The game has a start screen allowing the player to either receive help for how to play the game, or to start the game. The game itself continues until the player has been hit with a projectile, in which case they die, and the game displays a game over screen, allowing the player to play again. However, if the player is able to destroy all of the enemies without being hit by a projectile, the winning screen displays, congratulating them. The game implementation utilizes the JFrame and Swing component archistructure, in order to render and display graphical components in real-time onto the screen. Additionally, all enemies, the player, sprites, and projectiles are their own object instances, created from constructors specifying their properties. The application also implements a “commons” interface, which specifies the actual applications properties, such as screen location, screen size parameters, amongst other options.

# Proposed Contributions

I plan to upgrade and overhaul the game, adding many new features, reworking functionality, and upgrading the interface to be more user-friendly, as well as modern. In terms of graphics, I plan to make the graphical user interface more modern and simplistic, configuring text and buttons to be placed uniformly both in location and color, as well as implementing them as graphical JFrame objects, rather than JButton objects, for simplicity and presentation. Additionally, I plan to use different sprites that are more simplistic and minimalist, rather than retro, increasing their size, as well as the application screen’s size as well. I also plan to add background music and gameplay sounds to increase the playability and official-factor of the game.

In terms of functionality, I plan to add many upgrades and new features for the gameplay of the game. First of all, the player will be able to move not only horizontally, but also vertically across the lower half of the screen. The enemies will also not be restrained in place, and will be able to move around the screen towards the player, as well as remain stationary, dependent on their type, and the level of the game. The enemies will have differing behaviors as previously mentioned, being able to attack the player “melee-style”, or instead shoot the players from further away, without contact. Additionally, there will be power-ups throughout the entirety of the level(s), which will provide the player with temporary advancements to their character, such as having invincibility or a faster firing rate, for a short duration of time. The contributions will also include the game itself having several levels, which the player will have to advance through in order to progress to the next one, with each level increasing in difficulty. Not only this, but the player will have a health bar, instead of dying from a single projectile hit, which will allow them to remain in each level for a greater amount of time. This will be counterbalanced with a decrease in damage from the projectiles fired from the player, now taking one or more projectiles to destroy an enemy. The increasing difficulty in levels will be due to both the number of projectiles fired at the player, as well as the number and type of enemies. There will also be a scorecard, which will keep track of different players’ scores, retaining these scores on a leaderboard, no matter if the application has been shut down and restarted or not, meaning permanently. Each player will be able to enter their name, with their score being displayed on the leaderboard.

For this project, we have a list of new features to add in the original space invaders. We’ll make the players and enemies to move in 2D, having different powerups, language selection, be able to use controller to move, virtual update, leaderboard, health function, and different levels. I will contribute the language selection, health functionality & health bar and powerups including health, shield, increase firing speed, double trajectory projectiles.

# Educational Goals

**_Object Oriented Design_** - The player, projectiles, enemies, powerup, amongst other things displayed and interacted with within the game will be constructors, with new instances of these objects being created during the game.

**_Test Driven Development_** - The game will be thoroughly tested, with each single level played in every fashion imaginable, where every possibility of movement, strategy, and game-state will be considered and tried out, to ensure that there are no bugs or glitches that can occur in the game.

**_Unified Modeling Language (UML)_** - A large and complex UML diagram will be created for the application, showing the relationships between each class and its corresponding member variables and methods, to ensure a complete understanding of how the game’s working parts come together to form the entire application and the experience that stems from it.

**_Debugging_** - The game will be thoroughly tested, as previously mentioned, and then its code will be analyzed for possible bugs. Each game member and all of its functionality will be caught for exceptions and possible mishaps, to prevent crashes in the middle of the game.

**_Code profiling and optimization_** - The game will be optimized and its code will be profiled to utilize the current and modern Java methods and functionalities, in order to make use of newer technology and avoid possible code malfunctionality from older methods and Java-included classes.

**_On-the-fly coding_** - Since the game will have difficulty selection, the game’s code will then be able to be modified and its functionality changed during run time, thus utilizing on-the-fly coding. Not only this, but the movement of the player and its projectiles will also change during runtime, corresponding to the user’s key input.

**_Graphic User Interface_** - The game will contain a simple and functional GUI that will offer players the option to play the game, receive help for playing it, lower the difficulty, as well as the losing, winning, starting, and leaderboard screens being displayed. Of course, the graphical user interface in the game itself will be dynamic, changing with the player controls and game functionality in real-time.

**_Access to Database_** - The scorecard will utilize SQL to create a database of players and their respective scores, which then will be sorted using SQL statements/scripts in descending order, automatically being input and re-calculated after each game.

# Whiteboard Presentation Slide

![Whiteboard_Slide](https://github.com/mishagolikov/Space-Invaders-PLUS/blob/master/Space%20Invaders%20SLIDE.png)

# UML Diagram

![UML Diagram](https://github.com/mishagolikov/Space-Invaders-PLUS/blob/master/UML%20Diagram%20Space%20Invaders%20PLUS.png)

# Game Screenshot

![Game Screen](https://raw.githubusercontent.com/tatilattanzi/space-invaders/master/screens/space-invaders-game-screen.png)
