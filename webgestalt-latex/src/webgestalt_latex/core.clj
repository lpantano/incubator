(ns webgestalt-latex.core
  (:require [clojure.string :as str]
            [clostache.parser :as stache])
  (:gen-class))

(def pathway-header [:type :name :db-id])
(def pathway-stats-header [:count :observed :expected :fold-enrichment :pvalue
                           :fdr])

(defn partition-sections [file-content]
  "partition the webgestalt tsv into sections, dropping the header"
  (remove clojure.string/blank? (rest (str/split file-content #"\n\n\n"))))

(defn pathway-line [section]
  (first (str/split-lines section)))

(defn parse-pathway-stats [section]
  (let [items (-> section str/split-lines second (str/split #";"))]
    (map second (map #(str/split % #"=") items))))

(defn pathway-type [body]
  (first (str/split body #"\t")))

(defn pathway-name [body]
  (second (str/split body #"\t")))

(defn pathway-info [section]
  (zipmap pathway-header (str/split (pathway-line section) #"\t")))

(defn pathway-stats [section]
  (zipmap pathway-stats-header (parse-pathway-stats section)))

(defn get-pathway-genes [section]
  (let [gene-lines (rest (rest (str/split-lines section)))]
    {:genes (remove str/blank? (map #(first (str/split % #"\t")) gene-lines))}))

(defn make-pathway-info [section]
  (let [header (pathway-info section)
        stats (pathway-stats section)
        genes (get-pathway-genes section)]
    (merge header stats genes)))

(defn format-entry [section]
  (let [pathway-info (make-pathway-info section)
        genes (str/join ", " (:genes pathway-info))]
    (str (:name pathway-info) "&" (:pvalue pathway-info) "&"
         (:fdr pathway-info) "&" genes "\\")))

(defn format-table [file-content]
  (let [sections (partition-sections file-content)
        entries (str/join "\n" (map format-entry sections))]
    (stache/render-resource "template.stache" {:table-entries entries})))

(defn -main [& args]
  (print (format-table (slurp (first args)))))
