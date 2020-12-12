(ns advent-2020.problems.seven
  (:require [clojure.set :as set]
            [clojure.string :as string]))

(defn- format-type
  "Format bag color as keyword"
  [type]
  (-> type
      (string/replace #" " "-")
      keyword))

(defn- parse-content
  "Parse singular content snippet like '1 red bag' into a map"
  [content-str]
  (if (= content-str "no other bags.")
    (hash-map)
    (let [[count* content*] (string/split content-str #" " 2)
          count (Integer. count*)
          [content _] (string/split content* #" bag" 2)]
      (hash-map (format-type content) count))))

(defn- parse-rule
  "Parse single rule string into a map"
  [rule-str]
  (let [[type* contents-str] (string/split rule-str #" contain ")
        [type _] (string/split type* #" bag" 2)
        contents* (string/split contents-str #", ")
        contents (map parse-content contents*)]
    (hash-map (format-type type) (apply merge contents))))

(defn- parse-rules
  "Collapse all input rules to single map, with values being a map of bag color to number"
  [rule-strs]
  (->> rule-strs
       (map parse-rule)
       (apply merge)))

(defn- to-map-set
  "Convert parsed rules to map of color to set of contained colors"
  [rules]
  (->> rules
       (vals)
       (map #(map first %))
       (map #(into #{} %))
       (zipmap (keys rules))))

;; https://clojuredocs.org/clojure.set/map-invert
;; eww
(defn- invert-map-of-sets [m]
  (reduce (fn [a [k v]] (assoc a k (conj (get a k #{}) v))) {} (for [[k s] m v s] [v k])))

;; https://clojuredocs.org/clojure.core/reduce-kv#example-543cd003e4b02688d208b1b4
(defn- update-map [m f]
  (reduce-kv (fn [m k v] 
               (assoc m k (f v))) (empty m) m))

(defn- update-bags
  "Multiply contained bags by n bags in map"
  [bag-map n]
  (update-map bag-map #(* n %)))

(defn can-contain
  [rules type]
  (let [rules-set (to-map-set rules)
        i-rules   (invert-map-of-sets rules-set)]
    (loop [containers #{}
           lookups    #{type}]
      (let [get-contain-set (fn [type] (get i-rules type #{}))
            contain-sets    (map get-contain-set lookups)
            all-containers  (reduce set/union contain-sets)
            new-containers  (set/difference all-containers containers)]
        (if (empty? new-containers)
          (count containers)
          (recur (set/union containers new-containers) new-containers))))))

(defn contains
  [rules type]
  (loop [m    {type 1}
         total 0]
    (let [m'  (map #(get rules %) (keys m))
          m'' (map update-bags m' (vals m))
          m*  (apply merge-with + m'')
          t'  (reduce + total (vals m*))]
      (if (= total t')
        t'
        (recur m* t')))))

(defn solve
  [data func]
  (-> data
      (parse-rules)
      (func :shiny-gold)))
