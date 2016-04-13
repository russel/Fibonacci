module Main where

import Numeric.Natural
import Test.QuickCheck

import Fibonacci

f_p :: (Integer -> Integer) -> Integer -> Bool
f_p f n
    | n == 0 = f n == 0
    | n == 1 = f n == 1
    | otherwise = f n == f (n - 1) + f (n - 2)

fibonacci_property :: (Integer -> Integer) -> Natural -> Bool
fibonacci_property f n = f_p f (fromIntegral n)

main :: IO()
main = do
  -- Do not attempt to test the naïveRecursive implementation due to the exponential behaviour.
  -- quickCheck (fibonacci_property naïveRecursive)
  quickCheck (fibonacci_property tailRecursive)
  quickCheck (fibonacci_property lazyList_recursion)
  quickCheck (fibonacci_property lazyList_zipWith)
