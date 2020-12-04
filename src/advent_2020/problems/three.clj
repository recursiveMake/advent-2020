(ns advent-2020.problems.three)

(defn- move-right
  "Move right along a level with wrapping"
  [line start n]
  (let [length (count line)
        end (+ n start)]
    (mod end length)))

(defn- is-tree
  "Check text to see if tree returning int instead of bool"
  [line pos]
  (let [character (.charAt line pos)]
    (if (= \# character)
      1
      0)))

(defn- count-trees
  "Count trees going down the levels with pattern"
  [data right down]
  (let [levels (take-nth down data)]
    (loop [total  0
           pos    0
           level  (second levels)
           others (nthrest levels 2)]
      (if (empty? level)
        total
        (let [new-pos   (move-right level pos right)
              increment (is-tree level new-pos)]
          (recur (+ total increment)
                 new-pos
                 (first others)
                 (rest others)))))))

(defn solve
  [data pairs]
  (->> pairs
       (map #(count-trees data (first %) (second %)))
       (reduce *)))
