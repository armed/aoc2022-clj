(ns day1
  (:require
   [clojure.string :as string]
   [util :as u]
   [clojure.java.io :as io]))

(defn input []
  (->> (slurp (io/resource "day1"))
       (string/split-lines)
       (map (fn [s] (if (empty? s) 0 (parse-long s))))
       (partition-by zero?)
       (map #(reduce + %))
       (sort)
       (reverse)))

(def first-part first)

(defn second-part 
  [in]
  (reduce + (take 3 in)))

(defn run
  []
  (u/run-input (input) first-part second-part))

(comment (time (run)))
