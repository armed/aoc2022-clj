(ns day10
  (:require
   [util :as u]))

(comment
  (def input (u/load-input "day10"))
  (def test-input (u/load-input "day10test")))

(defn parse-line
  [s]
  (if-let [l (re-find #"-*\d+" s)]
    (parse-long l)
    0))

(defn parse-input
  [in]
  (->> in
       (map parse-line)
       (reduce (fn [[hist prev] oper]
                 (if (zero? oper)
                   [(conj hist prev) 0]
                   [(into hist [prev 0]) oper]))
               [[] 1])
       (first)))

(defn sum-parts
  []
  (fn [rf]
    (let [prev (volatile! 0)]
      (fn
        ([] (rf))
        ([result] (rf result))
        ([result input]
         (let [new-sum (+ @prev (apply + input))]
           (vreset! prev new-sum)
           (rf result new-sum)))))))

(defn first-part
  [parsed-in]
  (transduce
   (comp
    (partition-all 20)
    (sum-parts)
    (map-indexed (fn [i v]
                   [(* 20 (inc i)) v]))
    (take-nth 2)
    (map (partial apply *)))
   +
   parsed-in))

(defn second-part
  [parsed-in]
  (loop [current 0
         idx 0
         opers parsed-in]
    (when (seq opers)
      (let [op (first opers)
            new-idx (if (zero? (mod idx 40))
                      (do (println) 0)
                      idx)
            new-place (+ current op)]
        (if (>= 1 (abs (- new-idx new-place)))
          (print "█")
          (print "•"))
        (recur new-place (inc new-idx) (rest opers))))))

(comment
  (first-part (parse-input test-input))
  (first-part (parse-input input))

  (second-part (parse-input test-input))

; ██••██••██••██••██••██••██••██••██••██••
; ███•••███•••███•••███•••███•••███•••███•
; ████••••████••••████••••████••••████••••
; █████•••••█████•••••█████•••••█████•••••
; ██████••••••██████••••••██████••••••████
; ███████•••••••███████•••••••███████•••••

  (second-part (parse-input input))
; ███••█••█•███••••██•███••███••█•••••██••
; █••█•█•█••█••█••••█•█••█•█••█•█••••█••█•
; █••█•██•••█••█••••█•███••█••█•█••••█••█•
; ███••█•█••███•••••█•█••█•███••█••••████•
; █•█••█•█••█••••█••█•█••█•█••••█••••█••█•
; █••█•█••█•█•••••██••███••█••••████•█••█•

  nil)

(defn run
  []
  (u/run "day10" parse-input first-part second-part))

(comment
  (time (run))
  (time (u/run "day10test" parse-input first-part second-part))
  nil)

