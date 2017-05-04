# -*- mode: prolog; -*-

fibonacci(0, 0) :- !.
fibonacci(N, R) :- fibonacci(N, R, 0, 1).

fibonacci(0, R, _, R) :- !.
fibonacci(N, R, P1, P2):-
   N > 0,
   NN is N - 1,
   NP2 is P1 + P2,
   fibonacci(NN, R, P2, NP2).
