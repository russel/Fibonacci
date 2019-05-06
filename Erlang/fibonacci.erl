-module(fibonacci).
-author('Russel Winder').
-export([naiveRecursive/1, tailRecursive/1, iterativeState/1]).

-ifdef(TEST).
-include_lib("eunit/include/eunit.hrl").
-endif.

naiveRecursive(0) -> 0;
naiveRecursive(1) -> 1;
naiveRecursive(N) when N > 1 -> naiveRecursive(N - 1) + naiveRecursive(N - 2);
naiveRecursive(N) when N < 0 -> erlang:error({fibonacciNaiveRecursiveNegativeArgument, N}).

tailRecursive(0) -> 0;
tailRecursive(1) -> 1;
tailRecursive(N) when N > 1 -> iterateTailRecursive(N, 1, 0);
tailRecursive(N) when N < 0 -> erlang:error({fibonacciTailRecursiveNegativeArgument, N}).

iterateTailRecursive(2, Next, Result) ->  Next + Result;
iterateTailRecursive(N, Next, Result) -> iterateTailRecursive(N - 1, Next + Result, Next).

iterativeState(0) -> 0;
iterativeState(1) -> 1;
iterativeState(N) when N > 1 -> hd(createList(N, [0, 1]));
iterativeState(N) when N < 0 -> erlang:error({fibonacciIterativeStateNegativeArgument, N}).

createList(0, L) -> L;
createList(N, L) ->
    [H1, H2 | _T] = L,
    createList(N - 1, [H1 + H2 | L]).

%generator([]) -> erlang:error(generatorInconsistency);
%generator([H | T]) -> erlang:error(generatorInconsistency);
%generator([H1, H2 | T]) -> [H1 + H2 | fun() -> generator([H2 | T])].

%fibonacciSequence() = 0 :: 1 :: generator(fibonacciSequence).

-ifdef(TEST).

%%%%%%%%%%%%%%%%%%%%
%
%    The unit tests.

testData () ->  [
                   {0, 0},
                   {1, 1},
                   {2, 1},
                   {3, 2},
                   {4, 3},
                   {5, 5},
                   {6, 8},
                   {7, 13},
                   {8, 21},
                   {9, 34},
                   {10, 55},
                   {11, 89},
                   {12, 144}
                 ].

naiveRecursive_test_() -> [?_assertEqual(R, naiveRecursive(I)) || {I, R} <- testData()].
tailRecursive_test_() -> [?_assertEqual(R, tailRecursive(I)) || {I, R} <- testData()].
iterativeState_test_() -> [?_assertEqual(R, iterativeState(I)) || {I, R} <- testData()].

negativeValues() -> [-100, -20, -5, -4, -3, -2, -1].

negativeParametersNaiveRecursive_test_() -> [?_assertError({fibonacciNaiveRecursiveNegativeArgument, N}, naiveRecursive(N)) || N <- negativeValues()].
negativeParametersTailRecursive_test_() -> [?_assertError({fibonacciTailRecursiveNegativeArgument, N}, tailRecursive(N)) || N <- negativeValues()].
negativeParametersIterativeState_test_() -> [?_assertError({fibonacciIterativeStateNegativeArgument, N}, iterativeState(N)) || N <- negativeValues()].

-endif.
