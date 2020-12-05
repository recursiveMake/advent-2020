(ns advent-2020.problems.five)

(defn decode
  "decode binary boarding pass substring"
  [string base]
  (loop [s string
         low 0
         high base]
    (let [range (+ 1 (- high low))
          shift (/ range 2)]
      (case (first s)
        (\F \L) (recur (subs s 1) low (- high shift))
        (\B \R) (recur (subs s 1) (+ low shift) high)
        low))))

(defn seat-id
  "Parse seat id from boarding pass"
  [pass]
  (let [row (decode (subs pass 0 7) 127)
        col (decode (subs pass 7) 7)]
    (+ col (* row 8))))

(defn solve
  "Determine max seat id"
  [data]
  (->> data
       (map seat-id)
       (reduce max)))

(defn solve-b
  "Find missing seat id"
  [data]
  (let [seat-ids (->> data
                  (map seat-id)
                  (sort))
        start (first seat-ids)
        end (last seat-ids)]
    (->> seat-ids
         (interleave (range start end))
         (partition 2)
         (filter #(apply not= %))
         (first)
         (first))))
