(ns day4
  (:require
   [util :refer [->Result]]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(defn input []
  (let [in (->> (io/resource "day4")
                (slurp))]
    (->> (string/split in #"\D")
         (map parse-long)
         (partition-all 4))))

(defn full-overlap?
  [[a1 b1 a2 b2]]
  (or (and (>= a2 a1) (<= b2 b1))
      (and (>= a1 a2) (<= b1 b2))))

(comment
  (def test-data [[2 4,6 8]
                  [2 3,4 5]
                  [5 7,7 9]
                  [2 8,3 7]
                  [6 6,4 6]
                  [2 6,4 8]])

  (filter full-overlap? test-data)
  nil)

(defn count-overlaps
  [in overlap-fn]
  (reduce (fn [c pair]
            (if (overlap-fn pair)
              (inc c)
              c))
          0
          in))

(defn partial-overlap?
  [[a1 b1 a2 b2]]
  (or (<= a1 a2 b1)
      (<= a1 b2 b1)
      (<= a2 a1 b2)
      (<= a2 b1 b2)))

(defn run []
  (let [in (input)]
    (->Result (count-overlaps in full-overlap?)
              (count-overlaps in partial-overlap?))))

(comment (time (run)))
