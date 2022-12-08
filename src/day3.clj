(ns day3
  (:require
   [util :as u]
   [clojure.set :as set]))

(def item-priorities (->> (concat (range (int \a) (inc (int \z)))
                                  (range (int \A) (inc (int \Z))))
                          (map-indexed (fn [idx l] [l (inc idx)]))
                          (into {})))

(defn intersection
  [rucksack]
  (let [half (/ (count rucksack) 2)]
    (->> rucksack
         ((juxt (partial take half) (partial drop half)))
         (map set)
         (apply set/intersection))))

(defn sum
  [items]
  (reduce (fn [s item]
            (+ s (get item-priorities (int item))))
          0
          items))

(defn first-part
  [in]
  (->> in
       (map intersection)
       (map sum)
       (reduce +)))

(defn badge
  [group]
  (apply set/intersection group))

(defn second-part
  [in]
  (->> in
       (map set)
       (partition 3)
       (map badge)
       (map first)
       (sum)))

(defn run
  []
  (u/run "day3" nil first-part second-part))

(comment (time (run)))
