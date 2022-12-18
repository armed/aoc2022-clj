(ns day15
  (:require
   [util :as u]))

(def day "day15")
(def day-test "day15test")

(comment
  (def input (u/load-input day))
  (def test-input (u/load-input day-test)))

(defn parse-input
  [in]
  (into []
        (comp
         (map #(re-seq #"-?\d+" %))
         (map #(map parse-long %))
         (map (fn [[x1 y1 x2 y2 :as coords]]
                (conj coords (+ (abs (- x1 x2))
                                (abs (- y1 y2)))))))
        in))

(defn first-part
  [sensors]
  (let [row 2000000
        candidates (filter (fn [[len _ y]]
                             (if (> y row)
                               (<= (- y len) row)
                               (>= (+ y len) row)))
                           sensors)]
    (loop [xm Long/MAX_VALUE
           xx (- Long/MAX_VALUE)
           cx candidates]
      (if (seq cx)
        (let [[len x1 y1] (first cx)
              bm (- (+ (abs x1) (abs (- y1 row))) len)
              bx (+ x1 (abs (- x1 bm)))]
          (recur (if (< bm xm) bm xm)
                 (if (> bx xx) bx xx)
                 (rest cx)))
        (+ (abs xx) (abs xm))))))


; 8
; 7       3
; 6     # # #
; 5   # # # # #
; 4 2 # # 0 # # 4
; 3   # # # # #
; 2     # # #
; 1       1
; 0 1 2 3 4 5 6 7 8 9
;
; 8
; 7
; 6   2 # # # 3
; 5   # # # # #
; 4   # # 0 # #
; 3   # # # # #
; 2   1 # # # 4
; 1
; 0 1 2 3 4 5 6 7 8 9

(defn rotate
  [[x y]]
  [(+ x y) (- x y)])

(defn rotate-back
  [[x y]]
  [(long (/ (+ x y) 2.0))
   (long (/ (- x y) 2.0))])

(defn sides
  [[len x y]]
  [[(- x len) (+ x len)] [(- y len) (+ y len)]])

(defn transform
  [sensors]
  (->> sensors
       (map (fn [[l x y]] (into [l] (rotate [x y]))))
       (map sides)))

(defn min-gap-lines
  [lines]
  (set (for [l1 lines
             l2 lines
             :when (= 2 (abs (- l1 l2)))]
         (inc (min l1 l2)))))

(defn second-part
  [parsed-in]
  (let [sides (transform parsed-in)
        x-lines (->> sides (map first) (flatten) (set))
        y-lines (->> sides (map second) (flatten) (set))
        x-candidates (min-gap-lines x-lines)
        y-candidates (min-gap-lines y-lines)
        [x y] (rotate-back [(first x-candidates) (first y-candidates)])]
    (+ y (* 4000000 x))))

(defn run
  []
  (u/run day parse-input first-part second-part))

(comment
  (time (run))
  (time (u/run day-test parse-input first-part second-part))
  nil)

