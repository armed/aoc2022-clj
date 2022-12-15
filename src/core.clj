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
   [day13])
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
               (future (day13/run))])))

(comment
  (-main))
; [{:part1 71924, :part2 210406}
;  {:part1 9241, :part2 14610}
;  {:part1 8298, :part2 2708}
;  {:part1 584, :part2 933}
;  {:part1 "WSFTMRHPP", :part2 "GSLCMFBRP"}
;  {:part1 1702, :part2 3559}
;  {:part1 1517599, :part2 2481982}
;  {:part1 1849, :part2 201600}
;  {:part1 6354, :part2 2651}
;  {:part1 15120, :part2 nil}
;  {:part1 50616, :part2 11309046332}
;  {:part1 425, :part2 418}
;  {:part1 6272, :part2 22288}]
