(ns clojure-intro.functions)

(defn adder [constToAdd] #(+ constToAdd %1))

(def plus2 (adder 2))
(def plus3 (adder 3))
