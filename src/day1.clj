(ns day1
  (:require
   [clojure.string :as string]
   [clojure.java.io :as io]))

(def elves-snacks
  (->> (slurp (io/resource "day1"))
       (string/split-lines)
       (map (fn [s] (if (empty? s) 0 (parse-long s))))
       (partition-by zero?)
       (map #(reduce + %))
       (sort)
       (reverse)))

(def first-part (first elves-snacks))

(def second-part (reduce + (take 3 elves-snacks)))
