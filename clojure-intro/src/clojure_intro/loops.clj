(ns clojure-intro.loops)


;Clojure doesn't optimize tail recursion

(defn fact [n] (if (< n 2) 1
                   (* n
                      (fact (- n 1)))))

(defn fact-tail [n, factorial] (if (< n 2)
                           factorial
                          (fact-tail (- n 1) (* factorial n))
                          )
  )

(defn fact [n] (fact-tail n 1))

; You'll get Stack overflow if you try to count large factorial such as 10000
;user=> (fact-tail 10000N 1N)
;StackOverflowError   clojure.lang.Numbers.lt (Numbers.java:219)

; Optimized version of fact-tail
(defn fact-tail [n, factorial] (if (< n 2)
                           factorial
                          (recur (- n 1) (* factorial n))
  ))



; LOOPs
(defn like-for [counter, max]
  (loop [ctr counter]
    (println ctr)
    (if (< ctr max)
      (recur (inc ctr))
      ctr
      )
    )
  )

; for
(take 50 (for [x (range 100) y (range 10) z (range 5)] (println [x y z])))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello!")
  )

;Variable arity function: trumped-up example
(defn argcount
  ([] 0)
  ([x] 1)
  ([x y] 2)
  ([x y & more] (+ (argcount x y) (count more))))