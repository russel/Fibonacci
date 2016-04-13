module Main where

import Control.Exception
import Control.Monad
import Test.HUnit

import Fibonacci

positiveData = [
        (0, 0),
        (1, 1),
        (2, 1),
        (3, 2),
        (4, 3),
        (5, 5),
        (6, 8),
        (7, 13),
        (8, 21),
        (9, 34),
        (10, 55),
        (11, 89),
        (12, 144),
        (13, 233),
        (14, 377),
        (15, 610),
        (16, 987),
        (17, 1597),
        (18, 2584),
        (19, 4181),
        (20, 6765),
        (21, 10946),
        (22, 17711),
        (23, 28657),
        (24, 46368),
        (25, 75025),
        (26, 121393),
        (27, 196418),
        (28, 317811),
        (29, 514229),
        (30, 832040) -- ,
        --
        --  Naïve recursive solutions have exponential time performance and so doing too many gets a bit tedious.
        --
        -- (31, 1346269),
        -- (32, 2178309),
        -- (33, 3524578),
        -- (34, 5702887),
        -- (35, 9227465),
        -- (36, 14930352),
        -- (37, 24157817),
        -- (38, 39088169),
        -- (39, 63245986),
        -- (40, 102334155),
        -- (41, 165580141),
        -- (42, 267914296),
        -- (43, 433494437),
        -- (44, 701408733),
        -- (45, 1134903170),
        -- (46, 1836311903),
        -- (47, 2971215073),
        -- (48, 4807526976),
        --
        ]

negativeData = [ -1, -2, -5, -10, -20, -100]

testPositive function comment = test [ comment ++ " " ++ show i ~: "" ~: expected ~=? function i | (i, expected) <- positiveData ]

-- assertException from: http://stackoverflow.com/questions/6147435/is-there-an-assertexception-in-any-of-the-haskell-test-frameworks/6147930#6147930
assertException :: (Exception e, Eq e) => e -> IO a -> IO ()
assertException ex action =
    handleJust isWanted (const $ return ()) $ do
        action
        assertFailure $ "Expected exception: " ++ show ex
  where isWanted = guard . (== ex)

testNegative function comment = test [comment ++ " " ++ show i ~: "" ~: assertException (ErrorCall "Cannot use a negative argument to Fibonacci function.") (evaluate $ function i) | i <- negativeData]

main = do
 runTestTT (testPositive Fibonacci.naïveRecursive "Naïve Recursive")
 runTestTT (testNegative Fibonacci.naïveRecursive "Naïve Recursive")
 runTestTT (testPositive Fibonacci.tailRecursive "Tail Recursive")
 runTestTT (testNegative Fibonacci.tailRecursive "Tail Recursive")
 runTestTT (testPositive Fibonacci.lazyList "Lazy List")
 runTestTT (testNegative Fibonacci.lazyList "Lazy List")
