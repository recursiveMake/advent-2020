(ns advent-2020.core
  (:require [clojure.set :as set]
            [clojure.tools.cli :refer [parse-opts]]
            [advent-2020.io :as io]
            [advent-2020.problems.one :as one]
            [advent-2020.problems.two :as two]
            [advent-2020.problems.three :as three]
            [advent-2020.problems.four :as four]
            [advent-2020.problems.five :as five]
            [advent-2020.problems.six :as six])
  (:gen-class))

(defn- problem-1
  [n]
  (let [text (slurp "resources/1.txt")
        data (io/parse-int text)
        limit 2020]
    (one/solve data n limit)))

(defn- problem-2
  [valid?]
  (let [text (slurp "resources/2.txt")
        data (io/parse-str text)]
    (two/solve data valid?)))

(defn- problem-3
  [pairs]
  (let [text (slurp "resources/3.txt")
        data (io/parse-str text)]
    (three/solve data pairs)))

(defn- problem-4
  [ns]
  (let [data (slurp "resources/4.txt")]
    (four/solve data ns)))

(defn- problem-5
  [f]
  (let [text (slurp "resources/5.txt")
        data (io/parse-str text)]
    (f data)))

(defn- problem-6
  [merger]
  (let [data (slurp "resources/6.txt")]
    (six/solve data merger)))

(defn -main
  "I solve the problems of the world to save Christmas"
  [& args]
  (println "Problem one (a): " (problem-1 2))
  (println "Problem one (b): " (problem-1 3))
  (println "Problem two (a): " (problem-2 two/valid-a?))
  (println "Problem two (b): " (problem-2 two/valid-b?))
  (println "Problem three (a): " (problem-3 [[3 1]]))
  (println "Problem three (b): " (problem-3 [[1 1] [3 1] [5 1] [7 1] [1 2]]))
  (println "Problem four (a): " (problem-4 "relaxed"))
  (println "Problem four (b): " (problem-4 "strict"))
  (println "Problem five (a): " (problem-5 five/solve))
  (println "Problem five (b): " (problem-5 five/solve-b))
  (println "Problem six (a): " (problem-6 set/union))
  (println "Problem six (b): " (problem-6 set/intersection)))
