(ns day9
  (:require
   [clojure.string :as string]
   [util :as u]))

(comment
  (def input (u/load-input "day9"))
  (def test-input (u/load-input "day9test")))

(def directions
  {"R" [1 0]
   "L" [-1 0]
   "U" [0 1]
   "D" [0 -1]})

(defn parse-input
  [in]
  (reduce (fn [steps cmd]
            (let [[d c] (string/split cmd #" ")
                  cx (parse-long c)
                  dir (get directions d)]
              (into steps (take cx (repeat dir)))))
          []
          in))

(defn move-knot
  [[x1 y1] [x2 y2]]
  (let [diff-x (- x1 x2)
        diff-y (- y1 y2)]
    (if (or (> (abs diff-x) 1)
            (> (abs diff-y) 1))
      [(+ x2 (min (max diff-x -1) 1))
       (+ y2 (min (max diff-y -1) 1))]
      [x2 y2])))

(defn perform-motion
  [rope [h1 h2]]
  (let [[x y] (first rope)
        new-head [(+ x h1) (+ y h2)]]
    (loop [head new-head
           old-knots (rest rope)
           new-knots [new-head]]
      (if (seq old-knots)
        (let [m (move-knot head (first old-knots))]
          (recur m (rest old-knots) (conj new-knots m)))
        new-knots))))

(defn tail-move-count
  [parsed-in initial-body]
  (let [visits (transient #{})]
    (reduce (fn [rope coords]
              (let [new-rope (perform-motion rope coords)]
                #_{:clj-kondo/ignore [:unused-value]}
                (conj! visits (last new-rope))
                new-rope))
            initial-body
            parsed-in)
    (count (persistent! visits))))

(defn first-part
  [parsed-in]
  (tail-move-count parsed-in [[0 0] [0 0]]))

(defn second-part
  [parsed-in]
  (tail-move-count parsed-in (take 10 (repeat [0 0]))))

(defn run
  []
  (u/run "day9" parse-input first-part second-part))

(comment
  (time (run))
  (time (u/run "day9test" parse-input first-part second-part))
  nil)

