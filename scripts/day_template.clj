(ns day_template
  (:require [clojure.string :as string]
            [util :refer [->Result]]
            [clojure.java.io :as io]))

(defn input 
  []
  (-> (io/resource "day_template")
      (slurp)
      (string/split-lines)))

(defn test-input
  []
  (-> (io/resource "day_templatetest")
      (slurp)
      (string/split-lines)))

(defn parse-input
  [in]
  ;; parse logic
  )

(defn first-part
  [in]
  ;; impl
  )

(defn second-part
  [in]
  ;; impl
  )

(defn run []
  (let [in (input)]
    (->Result (first-part in) (second-part in))))

(comment (time (run)))

