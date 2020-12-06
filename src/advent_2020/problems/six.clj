(ns advent-2020.problems.six
  (:require [clojure.string :as string]))

(defn- parse-groups
  "Split data into separate groups"
  [data]
  (let [into-groups (fn [text] (string/split text #"\n\n"))
        into-person (fn [group] (string/split group #"\n"))
        into-set    (fn [person] (map #(into #{} %) person))]
    (->> data
         (into-groups)
         (map into-person)
         (map into-set))))

(defn solve
  [data merger]
  (->> data
       (parse-groups)
       (map (partial apply merger))
       (map count)
       (reduce +)))
