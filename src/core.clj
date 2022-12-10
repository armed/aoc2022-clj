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
   [day10]))

(time
  (mapv deref
        [(future (day1/run))
         (future (day2/run))
         (future (day3/run))
         (future (day4/run))
         (future (day5/run))
         (future (day6/run))
         (future (day7/run))
         (future (day8/run))
         (future (day9/run))
         (future (day10/run))]))
