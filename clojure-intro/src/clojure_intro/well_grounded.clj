(ns clojure-intro.well-grounded)


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

(schwartz (list "a" "abc" "ab") (fn [x] (count x)))