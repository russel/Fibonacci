"
Various implementations of the Fibonacci Sequence function:

f_0 = 0
f_1 = 1
f_n = f_{n - 1} + f_{n - 2}  ∀ n > 1
"

(defun naive-recursive (n)
  "Naive recursive computation of the nth element of the Fibonacci sequence"
  (check-type n (integer 0 *))
  (if (< n 2) n
    (+ (fib (1- n)) (fib (- n 2)))))

(defun tail-recursive (n)
  "Tail-recursive computation of the nth element of the Fibonacci sequence"
  (check-type n (integer 0 *))
  (labels ((fib-aux (n f1 f2)
                    (if (zerop n) f1
                      (fib-aux (1- n) f2 (+ f1 f2)))))
    (fib-aux n 0 1)))

(defun loop-based (n)
  "loop-based iterative computation of the nth element of the Fibonacci sequence"
  (check-type n (integer 0 *))
  (loop for f1 = 0 then f2
        and f2 = 1 then (+ f1 f2)
        repeat n finally (return f1)))

(defun do-based (n)
  "do-based iterative computation of the nth element of the Fibonacci sequence"
  (check-type n (integer 0 *))
  (do ((i n (1- i))
       (f1 0 f2)
       (f2 1 (+ f1 f2)))
      ((= i 0) f1)))
