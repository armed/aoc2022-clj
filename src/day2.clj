(ns day2
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def rules {:X 1 :A 1
            :Y 2 :B 2
            :Z 3 :C 3})

(comment
  (- 1 2) ; rock paper W -1
  (- 1 3) ; rock scissors L -2

  (- 2 1) ; paper rock L 1
  (- 2 3) ; paper scissors W -1

  (- 3 1) ; scissors rock W 2
  (- 3 2) ; scissors paper L 1
  )

(def states {1 0
             -2 0

             0 3

             -1 6
             2 6})

(defn play
  [acc [him me]]
  (let [[him' me'] [(get rules him) (get rules me)]
        state (get states (- him' me'))]
    (+ acc (+ state me'))))

(def day2-data (->> (slurp (io/resource "day2"))
                    (string/split-lines)
                    (map (fn [[him _ me]]
                           [(keyword (str him))
                            (keyword (str me))]))))

(def first-part (reduce play 0 day2-data))

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

(def second-part (reduce play-2 0 day2-data))
