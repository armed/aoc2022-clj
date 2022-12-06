(ns day6
  (:require [util :refer [->Result]]
            [clojure.java.io :as io]))

(defn input
  []
  (-> (io/resource "day6")
      (slurp)))

(defn least-dupe-idx
  [letters]
  (reduce (fn [idx [i j]]
            (let [l1 (nth letters i)
                  l2 (nth letters j)]
              (if (= l1 l2)
                (reduced (inc i))
                idx)))
          nil
          (for [i (range (dec (count letters)))
                j (range 1 (count letters))
                :when (< i j)]
            [i j])))

(defn find-distinct-distance
  [in len]
  (let [letters (vec in)]
    (loop [offset 0]
      (let [part (subvec letters offset (+ offset len))]
        (if-let [dupe-idx (least-dupe-idx part)]
          (let [new-offset (+ offset dupe-idx)]
            (recur new-offset))
          (+ len offset))))))

(comment
  (find-distinct-distance (input) 4)

  (find-distinct-distance "bvwbjplbgvbhsrlpgdmjqwftvncz" 4)
  (find-distinct-distance "nppdvjthqldpwncqszvftbrmjlhg" 4)
  (find-distinct-distance "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" 4)
  (find-distinct-distance "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" 4)

  (find-distinct-distance "mjqjpqmgbljsphdztnvjfqwrcgsmlb" 14)
  (find-distinct-distance "bvwbjplbgvbhsrlpgdmjqwftvncz" 14)
  (find-distinct-distance "nppdvjthqldpwncqszvftbrmjlhg" 14)
  (find-distinct-distance "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" 14)
  (find-distinct-distance "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" 14))

(defn first-part
  [in]
  (find-distinct-distance in 4))

(defn second-part
  [in]
  (find-distinct-distance in 14))

(defn run []
  (let [in (input)]
    (->Result (first-part in) (second-part in))))

(comment (time (run)))

