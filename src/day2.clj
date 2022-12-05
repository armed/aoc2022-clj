(ns day2
  (:require
   [clojure.java.io :as io]
   [util :refer [->Result]]
   [clojure.string :as string]))

(def rules-1 {:X 1 :A 1
              :Y 2 :B 2
              :Z 3 :C 3})

(def states {1 0
             -2 0

             0 3

             -1 6
             2 6})

(defn play
  [acc [him me]]
  (let [[him' me'] [(get rules-1 him) (get rules-1 me)]
        state (get states (- him' me'))]
    (+ acc (+ state me'))))

(defn input []
  (->> (slurp (io/resource "day2"))
       (string/split-lines)
       (map (fn [[him _ me]]
              [(keyword (str him))
               (keyword (str me))]))))

(def rules-2 {:A [3 1 2]
              :B [1 2 3]
              :C [2 3 1]
              :X [0 0]
              :Y [3 1]
              :Z [6 2]})

(defn play-2
  [acc [him decision]]
  (let [[sum shape] (get rules-2 decision)
        shapes (get rules-2 him)]
    (+ acc sum (nth shapes shape))))

(defn run []
  (let [in (input)]
    (->Result (reduce play 0 in) (reduce play-2 0 in))))

(comment (time (run)))
