(ns core
  (:require
   [day1]
   [day2]
   [day3]
   [day4]
   [day5]
   [day6]
   [day7]
   [day8]
   [day9]
   [day10]
   [day11]
   [day12]
   [day13]
   [day14]
   [day15])
  (:gen-class))

(defn -main []
  (time (mapv deref
              [(future (day1/run))
               (future (day2/run))
               (future (day3/run))
               (future (day4/run))
               (future (day5/run))
               (future (day6/run))
               (future (day7/run))
               (future (day8/run))
               (future (day9/run))
               (future (day10/run))
               (future (day11/run))
               (future (day12/run))
               (future (day13/run))
               (future (day14/run))
               (future (day15/run))
               ])))

(comment
  (-main))
