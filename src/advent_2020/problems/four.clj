(ns advent-2020.problems.four
  (:require [clojure.spec.alpha :as spec]
            [clojure.string :as string]))

(defn valid-height?
  "Validate height range"
  [height-str]
  (let [len (count height-str)
        sep (- len 2)
        units (subs height-str sep)
        height (Integer. (subs height-str 0 sep))]
    (case units
      "in" (<= 59 height 76)
      "cm" (<= 150 height 193)
      false)))

;; strict passport specs
(spec/def :strict/byr #(<= 1920 (Integer. %) 2002))
(spec/def :strict/iyr #(<= 2010 (Integer. %) 2020))
(spec/def :strict/eyr #(<= 2020 (Integer. %) 2030))
(spec/def :strict/hgt valid-height?)
(spec/def :strict/hcl (spec/and #(= \# (.charAt % 0))
                          #(= 7 (count %))
                          #(Long/parseLong (subs % 1) 16)))
(spec/def :strict/ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(spec/def :strict/pid (spec/and #(= 9 (count %))
                          #(Integer. %)))

(spec/def :strict/passport (spec/keys :req [:strict/byr
                                            :strict/iyr
                                            :strict/eyr
                                            :strict/hgt
                                            :strict/hcl
                                            :strict/ecl
                                            :strict/pid]
                                :opt [:strict/cid]))

;; relaxed passport specs
(spec/def :relaxed/passport (spec/keys :req [:relaxed/byr
                                            :relaxed/iyr
                                            :relaxed/eyr
                                            :relaxed/hgt
                                            :relaxed/hcl
                                            :relaxed/ecl
                                            :relaxed/pid]
                                :opt [:relaxed/cid]))

(defn parse-passports
  "Separate into sequence of unparsed passports"
  [data]
  (string/split data #"\n\n"))

(defn parse-fields
  "Move single passport into hashmap (with keys namespaced)"
  [passport ns]
  (let [entries (mapcat 
                 #(string/split % #" ")
                 (string/split passport #"\n"))]
    (->> entries
         (map #(string/split % #":"))
         (mapcat #(seq [(keyword ns (first %)) (second %)]))
         (apply assoc {}))))

(defn solve
  [data ns]
  (->> data
       (parse-passports)
       (map #(parse-fields % ns))
       (filter #(spec/valid? (keyword ns "passport") %))
       (count)))
