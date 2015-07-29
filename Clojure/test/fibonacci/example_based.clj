(ns fibonacci.example-based
  (:require
    [clojure.test :refer :all]
    [fibonacci.variants :refer :all]))

(def positive_data [
                    [0 0]
                    [1 1]
                    [2 1]
                    [3, 2]
                    [4, 3]
                    [5, 5]
                    [6, 8]
                    [7, 13]
                    [8, 21]
                    [9, 34]
                    [10, 55]
                    [11, 89]
                    [12, 144]
                    [13, 233]
                    [14, 377]
                    [15, 610]
                    ])

(def negative_data [-1 -2 -5 -10 -20 -100])

(def real_data [-200.5 -20.5 -2.5 2.5 20.5 200.5])

(defn- run-positive-tests [f]
  (doseq [x positive_data]
    (is (= (last x) (f (first x))))))

(defn- run-negative-tests [f]
  (doseq [x negative_data]
    (is (thrown? IllegalArgumentException (f x)))))

(defn- run-real-tests [f]
  (doseq [x real_data]
   (is (thrown? IllegalArgumentException (f 1.4)))))

(defn- run-the-tests [f]
  (run-positive-tests f)
  (run-negative-tests f)
  (run-real-tests f))

(deftest naïve-test (run-the-tests naïve))

(deftest looping-test (run-the-tests looping))

(deftest pattern-match-test (run-the-tests pattern-match))

(deftest reducing-test (run-the-tests reducing))
