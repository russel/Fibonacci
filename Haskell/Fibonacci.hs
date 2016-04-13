{-|
Module      : Fibonacci
Description : Various implementation of the Fibonacci function.
Copyright   : © Russel Winder  2015
License     : GPLv3
Maintainer  : russel@winder.org.uk
Stability   :  stable
Portability :  portable

This module contains various implementations of the Fibonacci function (an accessor of the nth item in the
Fibonacci Sequence) to show various different Haskell techniques and approaches.

Fibonacci is undefined for negative integers, raise an error for such uses. Whilst this seems a little
extreme, any program asking to evaluate Fibonacci for a negative value is broken.
-}
module Fibonacci where

exceptionErrorMessage = "Cannot use a negative argument to Fibonacci function."

-- | Fibonacci implemented using a naïve recuirsive approach. This function has exponential time behaviour
-- | so is useless for anything other than small positive arguments.
naïveRecursive n
    | n < 0 = error exceptionErrorMessage
    | n == 0 = 0
    | n == 1 = 1
    | n > 1 = naïveRecursive (n - 1) + naïveRecursive (n - 2)

-- | Fibonacci implemented using a classic tail recursive function approach.
tailRecursive n
    | n < 0 = error exceptionErrorMessage
    | n == 0 = 0
    | n == 1 = 1
    | n > 1 = doTailRecursion n 1 0
        where
            doTailRecursion 0 next result = result
            doTailRecursion i next result = doTailRecursion (i - 1) (next + result) next

-- | Fibonacci implemented using a lazy list realized using zipWith.
lazyList n
    | n < 0 = error exceptionErrorMessage
    | otherwise = fibonacciSequence !! fromInteger n
    where
      fibonacciSequence = 0 : 1 : zipWith (+) fibonacciSequence (tail fibonacciSequence)
