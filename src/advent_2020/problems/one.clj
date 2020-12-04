(ns advent-2020.problems.one
  (:require [clojure.math.combinatorics :refer [combinations]]))

(defn solve
  "Find n elements in data that sum to total and return the product"
  [data n total]
  (loop [groups (combinations data n)]
    (let [group (first groups)
          others (rest groups)
          sum (reduce + group)]
      (if (= total sum)
        (reduce * group)
        (recur others)))))
