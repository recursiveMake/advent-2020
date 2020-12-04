(ns advent-2020.problems.two
  (:require [clojure.string :as string]))

(defn- parse-line
  "Separate a line into parsed components"
  [line]
  (let [[policy password] (string/split line #": ")
        [range_ char-string] (string/split policy #" ")
        character (.charAt char-string 0)
        limits (string/split range_ #"-")
        [pre post] (map #(Integer. %) limits)]
    (seq [password character pre post])))

(defn valid-a?
  "Is a password valid according to occurences in string"
  [password character start end]
  (->> password
       (filter #(= character %))
       (count)
       (#(<= start % end))))

(defn valid-b?
  "Is a password valid according to positions in string"
  [password character pos-1 pos-2]
  (->> [pos-1 pos-2]
       (map #(.charAt password (- % 1)))
       (filter #(= character %))
       (count)
       (= 1)))

(defn solve
  "Parse passwords according to a rule and return total valid"
  [data valid?]
  (->> data
       (map parse-line)
       (filter #(apply valid? %))
       (count)))
