(ns day12
  (:require
   [util :as u]))

(comment
  (def input (u/load-input "day12"))
  (def test-input (u/load-input "day12test")))

;; start in E
(def START_ID 69)
(def END_ID 83)

(def LOW (int \a))
(def HITH (int \z))

(defn parse-input
  [in]
  (let [start-row (volatile! 0)
        start-col (volatile! 0)
        end-row (volatile! 0)
        end-col (volatile! 0)]
    [(vec (map-indexed
           (fn [y row]
             (vec (map-indexed
                   (fn [x c]
                     (let [ic (int c)]
                       (cond
                         (= START_ID ic) (do (vreset! start-col x)
                                             (vreset! start-row y)
                                             HITH)
                         (= END_ID ic) (do (vreset! end-col x)
                                           (vreset! end-row y)
                                           LOW)
                         :else ic)))
                   row)))
           in))
     [@start-row @start-col]
     [@end-row @end-col]]))

(defn neighbours
  [[row col] graph seen]
  (let [curr-val (get-in graph [row col])]
    (filter (fn [[r c :as coords]]
              (when-not (seen coords)
                (>= (get-in graph [r c] -100500)
                    (dec curr-val))))
            [[(dec row) col]
             [(inc row) col]
             [row (dec col)]
             [row (inc col)]])))

(defn find-path
  [part [graph start finish]]
  (loop [candidates (vector [0 start])
         seen #{start}]
    (let [[steps current] (first candidates)]
      (if (or (and (= part 2) (= (int \a) (get-in graph current)))
              (= finish current))
        steps
        (let [nbs (vec (neighbours current graph seen))
              new-candidates (mapv #(vector (inc steps) %) nbs)]
          (recur (into (vec (rest candidates)) new-candidates)
                 (into seen nbs)))))))

(defn run
  []
  (u/run "day12" parse-input (partial find-path 1) (partial find-path 2)))
;
(comment
  (time (run))
  (time (u/run "day12test"
               parse-input
               (partial find-path 1)
               (partial find-path 2)))
  nil)

