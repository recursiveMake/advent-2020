(ns advent-2020.problems.eight
  (:require [clojure.string :as string]))

(defn parse-instruction
  "Cast an instruction line to vector [op reg]"
  [line]
  (let [[op reg-str] (string/split line #" " 2)
        reg (Integer. reg-str)]
    [op reg]))

(defn indexes
  "Indices of coll for which (fred item) is true"
  [coll pred]
  (loop [idx 0
         found []
         items coll]
    (if (empty? items)
      found
      (if (pred (first items))
        (recur (inc idx) (conj found idx) (rest items))
        (recur (inc idx) found (rest items))))))

(defn replace-at
  "Replace the value at idx in coll with (f item)"
  [coll idx f]
  (let [coll-pre (take idx coll)
        coll-post (nthrest coll (inc idx))
        item (nth coll idx)
        replacement (f item)
        coll-idx (seq [replacement])]
    (concat coll-pre coll-idx coll-post)))

(defn repair
  "Produce n progs with op->op* replacement (1 for each op occurrence)"
  [prog op op*]
  (let [indices (indexes prog (fn [x] (= op (first x))))
        replacer (fn [idx] (replace-at prog idx (fn [x] [op* (second x)])))]
    (map replacer indices)))

(defn execute
  "Run program and return accumulator and exit flag"
  [prog]
  (loop [idx  1
         acc  0
         seen #{}]
    (if (contains? seen idx)
      [acc false]
      (let [[op reg] (nth prog (- idx 1) ["end" 0])
            seen*    (conj seen idx)]
        (case op
          "acc" (recur (inc idx) (+ acc reg) seen*)
          "jmp" (recur (+ idx reg) acc seen*)
          "nop" (recur (inc idx) acc seen*)
          "end" [acc true])))))

(defn solve
  [data]
  (->> data
       (map parse-instruction)
       (execute)
       (first)))

(defn solve-2
  [data]
  (let [prog (map parse-instruction data)
        progs (concat
               (repair prog "nop" "jmp")
               (repair prog "jmp" "nop"))]
    (loop [prog* (first progs)
           progs* (rest progs)]
      (let [[acc success] (execute prog*)]
        (if success
          acc
          (recur (first progs*) (rest progs*)))))))
