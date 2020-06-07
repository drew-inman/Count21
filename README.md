# Count21
It's the 21 Coins game from Super Mario RPG! This project implements the minimax algorithm (later used in a larger project I worked on) based on a YT video I watched.

Generally, players take turns counting from 0 to 21 in a sequence of up to four numbers. Whoever ends up saying 21 in their sequence of numbers loses. Example:

P1: 1, 2, 3\
P2: 4, 5, 6, 7\
P1: 8\
P2: 9, 10, 11, 12\
P1: 13, 14\
P2: 15, 16, 17\
P1: 18, 19, 20\
P2: 21\
P1 wins!

This project was originally supposed to demonstrate the minimax algorithm, which is why you can only play against the computer for now. The difficulty for the computer player is changed by changing the search depth of the minimax algorithm. Setting the search depth to anything higher than 5 has the same effect as setting the search depth to 5.
