(ns advent-2020.problems.nine
  (:require [clojure.math.combinatorics :refer [combinations]]))

(defn valid?
  "Is n contained in the sum of 2 distinct values of prev?"
  [prev n]
  (->> prev
       (distinct)
       (#(combinations % 2))
       (map (partial reduce +))
       (into #{})
       (#(contains? % n))))

(defn parse
  "Parse sequence into sequence of preamble next pairs"
  [chunk-size data]
  (->> data
       (partition (+ chunk-size 1) 1)
       (map (fn [chunk_] [(take chunk-size chunk_) (last chunk_)]))))

(defn take-to-sum
  "Take until seq reaches sum or nil, does not handle (< sum (reduce + coll))"
  [coll sum]
  (loop [total 0
         data  coll
         n     0]
    (let [total* (+ (first data) total)
          n*     (inc n)]
      (if (> total* sum)
        nil
        (if (= total* sum)
          (take n* coll)
          (recur total* (rest data) n*))))))

(defn solve-2
  [coll sum]
  (loop [data coll]
    (let [nums (take-to-sum data sum)]
      (if (nil? nums)
        (recur (rest data))
        (let [s-nums (sort nums)]
          (+ (first s-nums) (last s-nums)))))))

(defn solve
  [data size]
  (->> data
       (parse size)
       (map #(apply (fn [c n] [(valid? c n) n]) %))
       (remove first)  ; find invalid nums
       (first)         ; take 1
       (second)))      ; return num
