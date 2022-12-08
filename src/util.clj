(ns util 
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(defn load-input
  [file-name]
  (-> (io/resource file-name)
      (slurp)
      (string/split-lines)))

(defn run [day parse-fn first-part-fn second-part-fn]
  (let [in (load-input day)
        parsed (if parse-fn (parse-fn in) in)]
    {:part1 (first-part-fn parsed)
     :part2 (second-part-fn parsed)}))

(defn run-input
  [input first-part second-part]
  {:part1 (first-part input)
   :part2 (second-part input)})

