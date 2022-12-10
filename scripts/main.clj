(ns main
  (:require [babashka.fs :as fs]
            [clojure.string :as string]))

(defn- day-contents 
  [day-num]
  (-> (slurp "scripts/day_template.clj")
      (string/replace #"day_template" (str "day" day-num))))

(defn- try-create-file!
  ([fpath]
   (try-create-file! fpath nil))
  ([fpath contents]
   (when-not (fs/exists? (fs/file fpath))
     (if contents
       (spit fpath contents)
       (fs/create-file fpath)))))

(defn create-day
  [day-num]
  (let [dayfile (str "src/day" day-num ".clj")
        infile (str "resources/day" day-num)
        testinfile (str infile "test")]
    (try-create-file! dayfile (day-contents day-num))
    (try-create-file! infile)
    (try-create-file! testinfile)))

(comment
  (create-day 6))

