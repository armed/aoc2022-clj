(ns day5
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def input (-> (io/resource "day5")
               (slurp)
               (string/split-lines)))

(defn- line->crates
  [line]
  (->> (partition-all 4 line)
       (map string/join)
       (map string/trim)
       (map second)))

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
                    crate-pack (take amount (reverse from-stack))
                    crate-pack' (if first-part?
                                  crate-pack
                                  (reverse crate-pack))]
                (-> crates
                    (update to into crate-pack')
                    (update from (comp vec (partial drop-last amount))))))
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

;; first part
(calc-top-crates input true)
;; second part
(calc-top-crates input false)
