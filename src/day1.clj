(ns day1
  (:require
   [clojure.string :as string]
   [util :refer [->Result]]
   [clojure.java.io :as io]))

(defn input []
  (->> (slurp (io/resource "day1"))
       (string/split-lines)
       (map (fn [s] (if (empty? s) 0 (parse-long s))))
       (partition-by zero?)
       (map #(reduce + %))
       (sort)
       (reverse)))

(defn run []
  (let [in (input)]
    (->Result (first in) (reduce + (take 3 in)))))

(comment (time (run)))
