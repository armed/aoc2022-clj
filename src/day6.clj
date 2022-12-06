(ns day6
  (:require [util :refer [->Result]]
            [clojure.java.io :as io]))

(defn input
  []
  (-> (io/resource "day6")
      (slurp)))

(defn find-distinct-distance
  [in amount]
  (reduce (fn [acc c]
            (let [stack (conj acc c)
                  cnt (count stack)
                  tail (subvec stack (min cnt (abs (- cnt amount))))]
              (if (= amount (count (distinct tail)))
                (reduced (count stack))
                stack)))
          []
          in))

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

