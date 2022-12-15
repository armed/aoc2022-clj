(ns day13
  (:require
   [clojure.edn :as edn]
   [util :as u]))

(comment
  (def input (u/load-input "day13"))
  (def test-input (u/load-input "day13test"))

  nil)

(defn parse-input
  [in]
  (into [] (comp (filter seq) (map edn/read-string)) in))

(defn wv [l]
  (if (coll? l) l [l]))

(defn numify [l]
  (if (coll? l) (or (first l) -1) l))

(defn mt?
  [v]
  (if (coll? v) (empty? v) (nil? v)))

(defn solve [left right]
  (loop [l (numify left)
         r (numify right)
         ll (rest (wv left))
         rr (rest (wv right))]
    (cond
      (and (= l r) (mt? ll)) true
      (and (not (mt? left)) (mt? right)) false
      (and (int? l) (int? r)) (if (= l r)
                                (recur (first ll) (first rr)
                                       (rest ll) (rest rr))
                                (< l r))
      (not (solve (wv l) (wv r))) false
      (solve (wv r) (wv l)) (recur (first ll) (first rr)
                                   (rest ll) (rest rr))
      :else true)))

(defn first-part
  [parsed-in]
  (transduce
   (comp
    (partition-all 2)
    (map-indexed (fn [i [l r]]
                   (if (solve l r) (inc i) 0))))
   +
   parsed-in))

(defn second-part
  [parsed-in]
  (let [divs #{[[2]] [[6]]}]
    (->> divs
         (concat parsed-in)
         (sort-by identity solve)
         (map-indexed (fn [i p] (if (divs p) (inc i) 1)))
         (reduce *))))

(defn run
  []
  (u/run "day13" parse-input first-part second-part))
;
(comment
  (time (run))
  (time (u/run "day13test" parse-input first-part second-part))
  nil)

