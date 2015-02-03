(ns webgestalt-latex.core-test
  (:require [clojure.test :refer :all]
            [webgestalt-latex.core :refer :all]
            [clojure.string :as str]))

(def test-pathway-commons (slurp "resources/webgestalt/pathway_commons.tsv"))
(def test-section (first (partition-sections test-pathway-commons)))

(deftest test-pathway-type
  (testing "get the pathway type"
    (is (= "Pathway Commons pathway" (pathway-type test-section)))))

(deftest test-pathway-name
  (testing "get the pathway name"
    (is (= "IFN-gamma pathway" (pathway-name test-section)))))

(deftest test-partition-sections
  (testing "testing partition the file into sections"
    (is (= 66 (count (partition-sections test-pathway-commons))))))

(deftest test-pathway-line
  (testing "get the pathway line from a section"
    (is (= \P (first (pathway-line test-section))))))

(deftest test-pathway-info
  (testing "We can make a pathway-info record from the pathway information"
    (is (= (:name (pathway-info test-section)) "IFN-gamma pathway"))))

(deftest test-make-pathway-info
  (testing "We can make a pathway-info hash-map from the pathway information"
    (is (= (:fdr (make-pathway-info test-section)) "3.15e-05"))))

(deftest test-get-pathway-genes
  (testing "Getting the list of genes in a pathway works"
    (is (= "DNM1" (first (:genes (get-pathway-genes test-section)))))))

(deftest test-format-pathway-info
  (testing "Test to see if the format of the pathway output works"
    (is (= "IFN-gamma pathway&4.71e-06&3.15e-05&DNM1, CSNK1G2\\" (format-entry test-section)))))

(deftest test-format-table
  (testing "Test to see if the stache formatting works"
    (println (format-table test-pathway-commons))))
