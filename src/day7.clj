(ns day7
  (:require
   [clojure.string :as string]
   [util :as u]))

(comment
  (def input (u/load-input "day7"))
  (def test-input (u/load-input "day7test")))

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
  [sizes]
  (->> (vals sizes)
       (filter (fn [size] (<= size 100000)))
       (reduce +)))

(defn second-part
  [sizes]
  (let [desired-space 30000000
        total-space 70000000
        taken-space (get sizes ["/"])
        required-min (- desired-space (- total-space taken-space))]
    (apply min (filter #(<= required-min %) (vals sizes)))))

(defn run []
  (u/run "day7" parse first-part second-part))

(comment (time (run)))

