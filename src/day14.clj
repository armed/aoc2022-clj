(ns day14
  (:require
   [clojure.string :as string]
   [util :as u]))

(def day "day14")
(def day-test "day14test")

(comment
  (def input (u/load-input day))
  (def test-input (u/load-input day-test)))

(defn delta
  [x1 y1 x2 y2]
  [(- x2 x1) (- y2 y1)])

(defn make-range
  [coord delta]
  (let [iter-fn (cond
                  (zero? delta) identity
                  (pos? delta) inc
                  :else dec)]
    (iterate iter-fn coord)))

(defn make-line
  [[[x1 y1] [x2 y2]]]
  (let [[dx dy] (delta x1 y1 x2 y2)]
    (take (inc (max (abs dx) (abs dy)))
          (map vector (make-range x1 dx) (make-range y1 dy)))))

(defn parse-input
  [in]
  (transduce
   (comp
    (map #(re-seq #"\d+" %))
    (map #(map parse-long %))
    (map #(partition 2 %))
    (map #(partition 2 1 %))
    (map #(reduce (fn [acc line] (into acc (make-line line))) [] %)))
   (completing (fn [acc cx] (into acc cx)))
   #{}
   in))

(defn simulate
  [y-max first-part? state]
  (loop [x 500 y 0]
    (cond
      (> y y-max) (if first-part?
                    nil
                    (conj! state [x (dec y)]))
      (not (state [x y])) (recur x (inc y))
      (not (state [(dec x) y])) (recur (dec x) (inc y))
      (not (state [(inc x) y])) (recur (inc x) (inc y))
      :else (conj! state [x (dec y)]))))

(defn first-part
  [state]
  (let [y-coords (sort (mapv second state))
        y-max (last y-coords)]
    (reduce (fn [cnt new-state]
              (if new-state
                (inc cnt)
                (reduced cnt)))
            -1
            (iterate (partial simulate y-max true)
                     (transient state)))))

(defn second-part
  [state]
  (let [y-coords (sort (mapv second state))
        y-max (+ 1 (last y-coords))]
    (reduce (fn [cnt new-state]
              (if (not (new-state [500 0]))
                (inc cnt)
                (reduced cnt)))
            0
            (iterate (partial simulate y-max false)
                     (transient state)))))

(defn run
  []
  (u/run day parse-input first-part second-part))

(comment
  (time (run))
  (time (u/run day-test parse-input first-part second-part))
  nil)

