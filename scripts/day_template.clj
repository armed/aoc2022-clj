(ns day_template
  (:require
   [util :as u]))

(def day "day_template")
(def day-test "day_templatetest")

(comment
  (def input (u/load-input day))
  (def test-input (u/load-input day-test))
  )

(defn parse-input
  [in]
  ;; parse logic
  )

(defn first-part
  [parsed-in]
  ;; impl
  )

(defn second-part
  [parsed-in]
  ;; impl
  )

(defn run
  []
  (u/run day parse-input first-part second-part))

(comment 
  (time (run))
  (time (u/run day-test parse-input first-part second-part))
  nil)

