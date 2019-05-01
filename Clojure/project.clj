(defproject Fibonacci "0.0.0"
  :description "Functions to calculate nth item in the Fibonacci Sequence with various realizations."
  :license {:name "GPL v.3"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :dependencies [
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/test.check "0.10.0-alpha4"]
                 [com.gfredericks/test.chuck "0.2.9"]
                 ]
  :repl-options {:init (load-file "src/fibonacci/variants.clj") }
  )
