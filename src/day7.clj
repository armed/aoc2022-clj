(ns day7
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]
   [util :refer [->Result]]))

(defn input
  []
  (-> (io/resource "day7")
      (slurp)
      (string/split-lines)))

(defn test-input
  []
  (-> (io/resource "day7test")
      (slurp)
      (string/split-lines)))

(defn add-file
  [{:keys [path] :as ctx} file]
  (let [size (parse-long (re-find #"\d+" file))]
    (update ctx :dir-sizes (fn [dsx]
                             (loop [p (iterate #(and (seq %) (pop %)) path)
                                    dsx dsx]
                               (if-let [path (first p)]
                                 (recur (rest p)
                                        (update dsx path (fnil + 0) size))
                                 dsx))))))

(defn parse
  [in]
  (->> (drop 2 in)
       (reduce (fn [ctx line]
                 (cond
                   (= line "$ cd ..")
                   (update ctx :path pop)

                   (string/starts-with? line "$ cd")
                   (update ctx :path conj (subs line 5))

                   (or (string/starts-with? line "$ ls")
                       (string/starts-with? line "dir"))
                   ctx

                   :else (add-file ctx line)))
               {:path ["/"]
                :dir-sizes {}})
       :dir-sizes))

(defn first-part
  [in]
  (->> (parse in)
       (vals)
       (filter (fn [size] (<= size 100000)))
       (reduce +)))

(defn second-part
  [in]
  (let [desired-space 30000000
        total-space 70000000
        sizes (parse in)
        taken-space (get sizes ["/"])
        required-min (- desired-space (- total-space taken-space))]
    (apply min (sort (filter #(<= required-min %) (vals sizes))))))

(comment
  (parse (test-input)))

(defn run []
  (let [in (input)]
    (->Result (first-part in) (second-part in))))

(comment (time (run)))

