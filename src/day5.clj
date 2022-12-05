(ns day5
  (:require
   [util :refer [->Result]]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(defn input []
  (-> (io/resource "day5")
      (slurp)
      (string/split-lines)))

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

(defn move-crates
  [input first-part?]
  (let [crates (parse-crates input)
        cmds (parse-commands input)]
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
            cmds)))

(defn calc-top-crates
  [input first-part?]
  (->> (move-crates input first-part?)
       (map last)
       (apply str)))

(comment
  (def test-input (-> (io/resource "day5test")
                      (slurp)
                      (string/split-lines)))

  (calc-top-crates test-input true)
  (calc-top-crates test-input false)

  nil)

(defn run []
  (let [in (input)]
    (->Result (calc-top-crates in true) (calc-top-crates in false))))

(comment (time (run)))
