(ns fibonacci.variants)

"
Various implementations of the Fibonacci Sequence function:

f 0 = 0
f 1 = 1
f n = f (n - 1) + f (n - 2), n > 1

"

(defn- validate-argument [n]
  ; integer? delivers true for java.lang.Long and clojure.lang.BigInt, but not java.math.BigDecimal.
 (if (not (integer? n))
    (throw (IllegalArgumentException. (format "Argument must be integer, got: %s" (type n)))))
  (if (< n 0)
    (throw (IllegalArgumentException. (format "Argument must be a non-negative integer, got: %d" n)))))

(defn naïve [n]
  "nth Fibonacci Sequence value using a naïve recursive approach."
  (validate-argument n)
  (if (< n 2)
    n
    (+' (naïve (dec n)) (naïve (- n 2)))))

(defn looping [n]
  "nth Fibonnaci Sequence value using a classic tail recursive algorithm."
  (validate-argument n)
  (if (< n 2)
    n
    (loop [
           i n
           previous 0
           result 1]
      (if (< i 2)
        result
        (recur (dec i) result (+' previous result))))))

(defn pattern-match
  "nth Fibonacci Sequence value using a tail recrsive algorithm in a pattern matching function."
  ([n previous result]
   (validate-argument n)
    (if (< n 2)
      result
      (recur (dec n) result (+' previous result))))
  ([n]
   (validate-argument n)
    (if (< n 2)
      n
      (pattern-match n 0 1))))

(defn reducing [n]
  "nth Fibonacci Sequence value using the built-in reduce function"
  (validate-argument n)
  (if (< n 2)
    n
    (second
      (reduce
        (fn [x y] [(second x), (+' (first x) (second x))])
        [0, 1]
        (range (dec n))))))

(defn zipping [n]
  "nth Fibonacci Sequence value using a zipping algorithm on lazy lists"
  (validate-argument n)
  (def fib (cons 0 (cons 1 (lazy-seq (map + fib (rest  fib))))))
  (first (drop n fib)))

(defn lazycatting [n]
  "nth Fibonacci Sequence value using a zipping algorithm via lazy cat"
  (validate-argument n)
  (def fib (lazy-cat [0 1] (map + (rest fib) fib)))
  (first (drop n fib)))