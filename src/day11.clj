(ns day11
  (:require
   [clojure.math :as math]
   [util :as u]))

(comment
  (def input (u/load-input "day11"))
  (def test-input (u/load-input "day11test")))

(defn parse-operation
  [[operation value]]
  (let [inspect-fn (if (= "*" operation) * +)
        oper-fn (if (= "old" value)
                  (fn old-old [v]
                    (inspect-fn v v))
                  (fn old-new [v]
                    (inspect-fn v (parse-double value))))]
    (fn [v first-part?]
      (if first-part?
        (math/floor (/ (oper-fn v) 3))
        (oper-fn v)))))

(defn mod* [div v] (mod v div))

(defn parse-monkey
  [[_ starting-items operation test truthy falsy]]
  (let [div-by (->> (subs test 21) (parse-double))]
    ; (transient)
    {:inspects 0
     :items (->> (subs starting-items 18)
                 (re-seq #"(\d+)")
                 (mapv (comp parse-double first)))
     :oper-fn (->> (subs operation 23)
                   (re-matches #"(\S)\s(\d+|old)")
                   (rest)
                   (parse-operation))
     :oper (->> (subs operation 23)
                (re-matches #"(\S)\s(\d+|old)")
                (rest))
     :test (partial mod* div-by)
     :div-by div-by
     :throw-to {true (->> (subs truthy 29) (parse-long))
                false (->> (subs falsy 30) (parse-long))}}))

(defn parse-input
  [in]
  (into [] (comp (partition-all 7) (map parse-monkey)) in))

(defn round
  [first-part? lcd monkeys rounds]
  (persistent!
   (reduce (fn [monkeys idx]
             (let [{:keys [items oper-fn test throw-to inspects] :as monkey}
                   (nth monkeys idx)]
               (loop [remain-items items
                      monkeys monkeys]
                 (if (seq remain-items)
                   (let [item (first remain-items)
                         i (oper-fn item first-part?)
                         test-result (test i)
                         to (get throw-to (zero? test-result))
                         send-val (if (not first-part?)
                                    (rem i lcd)
                                    i)
                         to-monkey (nth monkeys to)]
                     (recur (subvec remain-items 1)
                            (assoc! 
                              monkeys to (update to-monkey :items conj send-val))))
                   (assoc!
                    monkeys
                    idx (assoc monkey
                               :items []
                               :inspects (+ inspects (count items))))))))
           (transient monkeys)
           (take (* (count monkeys) rounds)
                 (cycle (range (count monkeys)))))))

(defn monkey-business-level
  [first-part? monkeys]
  (let [lcd (apply * (map :div-by monkeys))
        result (round first-part? lcd monkeys (if first-part? 20 10000))]
    (->> result
         (sort-by :inspects >)
         (map :inspects)
         (take 2)
         (apply *))))

(defn run
  []
  (u/run "day11"
         parse-input
         (partial monkey-business-level true)
         (partial monkey-business-level false)))

(defn run-test
  []
  (u/run "day11test"
         parse-input
         (partial monkey-business-level true)
         (partial monkey-business-level false)))

(take (* 4 10) (cycle [1 2 3 4]))

(comment
  (time (monkey-business-level false (parse-input input)))
  (time (monkey-business-level true (parse-input input)))
  ; {:part1 50616, :part2 11309046332}
  (time (run))
  ; {:part1 10605, :part2 2713310158}
  (time (run-test))
  nil)

