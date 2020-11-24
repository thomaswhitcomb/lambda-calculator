 (ns lexer (:gen-class)
    (:require
      [clojure.test :refer [is]]
      [clojure.string :as s]
      ))

(defn new-lexer [s]
  (let [packed (apply str (filter #(not= % \space ) s))
        io (java.io.PushbackReader. (java.io.StringReader. (str packed)))]
    (fn lexer
      ([] (let [n (.read io)] (if (= n -1) nil (char n))))
      ([c] (do (.unread io (int c)) lexer)))))
