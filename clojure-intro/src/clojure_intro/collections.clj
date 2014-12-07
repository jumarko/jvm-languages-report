(ns clojure-intro.collections)

(let [my-vector [1 2 3 4]
      my-map {:fred "ethel"}
      my-list (list 4 3 2 1)]
  (list
    (conj my-vector 5)
    (assoc my-map :ricky "lucy")
    (conj my-list 5)
    ;the originals are intact
    my-vector
    my-map
    my-list))

; aka "zip"
(map vector '(1 2 3) '(2 3 4))
(map vector '(1 2 3) '(2 3 4) '(3 4 5))

; makes list by applying f to each element of x
(defn list-maker-fun [x f]
  (map (fn [z] (list z (f z)))
       x))

; sort collection x by values of f applied to elements of collection
; the whole point is to made sorting efficient by avoiding unnecessary invocations of key-fn
(defn schwartz [coll key-fn]
  ; extract first items from sorted list of pairs
  (map
    (fn [x] (nth x 0))
    ; sort temporary list of pairs by second item (result of function applied to element of coll)
    (sort-by
      (fn [y] (nth y 1) )
      ; create temporary list of pairs ( (x1 f(x1)), (x2, f(x2)), ..., (xn, f(xn)) )
      (map
        (fn [z] (list z (key-fn z)))
        coll
        ))
    )
  )

(schwartz (list "a" "abc" "ab") (fn [x] (do (println "counting x") (count x))))
;counting x                                                                               │
;counting x                                                                               │
;counting x                                                                               │
;("a" "ab" "abc

; Compare to alternative
(sort-by (list "a" "abc" "ab") (fn [x] (do (println "counting x") (count x))))
;counting x                                                                               │
;counting x                                                                               │
;counting x                                                                               │
;counting x                                                                               │
;counting x                                                                               │
;counting x                                                                               │
;counting x                                                                               │
;counting x
;("a" "ab" "abc
