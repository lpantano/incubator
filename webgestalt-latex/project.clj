(defproject webgestalt-latex "0.1.0-SNAPSHOT"
  :description "Convert WebGestalt TSV output to a LaTeX table"
  :url "http://www.github.com/roryk/incubator/webgestalt-latex"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [org.clojure/tools.cli "0.3.1"]]

  :main ^:skip-aot webgestalt-latex.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
