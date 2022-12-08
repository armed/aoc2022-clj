(ns day8
  (:require
   [util :as u]))

(comment
  (def input (u/load-input "day8"))
  (def test-input (u/load-input "day8test")))

(defn parse-input
  [in]
  (let [trees (reduce (fn [trees line]
                        (conj trees (mapv parse-long (re-seq #"\d" line))))
                      []
                      in)]
    [trees (vec (apply map vector trees))]))

(defn tree-data
  [[x y] trees trans]
  (let [row (get trees y)
        col (get trans x)
        tree (get row x)
        left (subvec row 0 x)
        right (subvec row (inc x) (count row))
        top (subvec col 0 y)
        bottom (subvec col (inc y) (count col))]
    [tree left right top bottom]))

(defn visible?
  [coords trees trans]
  (let [[tree & sides] (tree-data coords trees trans)]
    (reduce (fn [_ part]
              (when (every? #(< % tree) part)
                (reduced true)))
            false
            sides)))

(defn tree-range
  [trees offset]
  (for [x (range offset (dec (count trees)))
        y (range offset (dec (count trees)))]
    [x y]))

(defn first-part
  [[trees trans]]
  (reduce (fn [cnt coords]
            (if (visible? coords trees trans)
              (inc cnt)
              cnt))
          (- (* (count trees) 4) 4)
          (tree-range trees 1)))

(defn side-scenic
  [side tree]
  (reduce (fn [cnt t]
            (if (< t tree)
              (inc cnt)
              (reduced (inc cnt))))
          0
          side))

(defn scenic
  [coords trees trans]
  (let [[tree left right top bottom] (tree-data coords trees trans)]
    (* (side-scenic (reverse left) tree)
       (side-scenic (reverse top) tree)
       (side-scenic right tree)
       (side-scenic bottom tree))))

(defn second-part
  [[trees trans]]
  (reduce (fn [score coords]
            (let [s (scenic coords trees trans)]
              (if (> s score) s score)))
          0
          (tree-range trees 0)))

(defn run []
  (u/run "day8" parse-input first-part second-part))

(comment
  (time (run))
  (time (u/run "day8test" parse-input first-part second-part))
  nil)



