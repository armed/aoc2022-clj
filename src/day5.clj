(ns day5
  (:require
   [util :as u]
   [clojure.string :as string]))

(comment
  (def input (u/load-input "day5"))
  (def test-input (u/load-input "day5test")))

(defn- line->crates
  [line]
  (->> (partition-all 4 line)
       (map (comp second string/trim string/join))))

(defn parse-crates
  [input]
  (let [crates-input (take-while (comp not empty?) input)
        crate-lines (->> crates-input
                         (butlast)
                         (reverse)
                         (map line->crates))]
    (->> crate-lines
         (apply map list)
         (mapv (comp vec (partial remove nil?))))))

(defn parse-longs
  [cmd-str]
  (let [[amount from to] (map parse-long cmd-str)]
    [amount (dec from) (dec to)]))

(defn parse-commands
  [input]
  (->> input
       (drop-while (comp not empty?))
       (rest)
       (map (comp parse-longs (partial re-seq #"\d+")))))

(defn parse
  [input]
  [(parse-crates input) (parse-commands input)])

(defn move-crates
  [first-part? [crates cmds]]
  (reduce (fn [crates [amount from to]]
            (let [from-stack (nth crates from)
                  crate-pack (take-last amount from-stack)
                  crate-pack' (if first-part?
                                (reverse crate-pack)
                                crate-pack)]
              (-> crates
                  (update to concat crate-pack')
                  (update from (partial drop-last amount)))))
          crates
          cmds))

(defn calc-top-crates
  [first-part? parsed-input]
  (->> (move-crates first-part? parsed-input)
       (map last)
       (apply str)))

(defn run
  []
  (u/run "day5" parse
         (partial calc-top-crates true)
         (partial calc-top-crates false)))

(comment (time (run)))
