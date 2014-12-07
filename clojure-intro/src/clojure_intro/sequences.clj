(ns clojure-intro.sequences)


(seq (vector 1 2 3))
; you can make seq from the list but the list itself is already a sequence
(seq '(1 2 3))

(first (vector 1 2 3))

(rest (vector 1 2 3))

(seq? (vector 1 2 3))
(seq? (seq (vector 1 2 3)))

(cons 1 (vector 1 2 3))

(conj (vector 1 2 3) 1)

(every? #(< %1 5) (vector 1 2 3))

; Lazy sequences
(defn next-big-n [n]
  (let  [new-val (+ 1 n)]
    (lazy-seq
      (cons new-val (next-big-n new-val))
      )
    )
  )

(defn natural-k [k] (concat [k] (next-big-n k)))


; Variable arity functions

(defn const-fun1
  ([] 1)
  ([x] 1)
  ([x & more] 1)
  )

(defn const-fun2 [& args] 2)

; More generic constantly function in clojure-core
(defn constantly
  "Returns a function that takes any number of arguments and returns x."
  {:added "1.0"
   :static true}
  [x] (fn [& args] x))
