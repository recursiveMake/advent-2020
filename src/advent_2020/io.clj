(ns advent-2020.io
  (:require [clojure.string :as string]))

(defn parse-str
  "Split input into sequence of strings"
  [text]
  (string/split-lines text))

(defn parse-int
  "Create sequence of numbers from input data"
  [text]
  (let [words (parse-str text)]
    (map #(Integer. %) words)))
