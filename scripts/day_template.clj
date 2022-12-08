(ns day_template
  (:require
   [util :as u]))

(comment
  (def input (u/load-input "day_template"))
  (def test-input (u/load-input "day_templatetest"))
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
  (u/run "day_template" parse-input first-part second-part))

(comment 
  (time (run))
  (time (u/run "day_templatetest" parse-input first-part second-part))
  nil)

