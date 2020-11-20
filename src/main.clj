 (ns main (:gen-class)
    (:import (java.util Date TimeZone))
    (:require
      [clojure.test :refer [is]]
      [clojure.string :as s]
      ))

(defn variable? [v]
  (and (>= (int v) (int \a)) (<= (int v) (int \z))))

(defn new-lexer [s]
  (let [packed (apply str (filter #(not= % \space ) s))
        io (java.io.PushbackReader. (java.io.StringReader. (str packed)))]
    (fn lexer
      ([] (let [n (.read io)] (if (= n -1) nil (char n))))
      ([c] (do (.unread io (int c)) lexer)))))

(declare expression)

(defn lambda [l]
  (let [lambda (l)
        v (l)
        dot (l)]
    (cond
      (not (variable? v))
      {:type :error :value (str "Bad variable in lambda: " v)}

      (not (= dot \.))
      {:type :error :value (str "Missing DOT. Found: " dot)}

      :else
      {:type :lambda :parm  (str v) :body (expression l)})))

(defn variable [l]
  (let [w (l)]
    (if (variable? w)
      {:type :variable :value (str w)}
      {:type :error :value (str "Bad variable: " w)})))

(defn application [l]
  (let [e1 (expression l)
        e2 (expression l)]
    {:type :appl :left e1 :right e2}))

(defn expression [l]
  (let [c (l)]
    (cond
      (= c \()
      (let [e (application l)
            rp (l)]
        (if (not= rp \))
          {:type :error :value (str "Missing right paren. got " rp)}
          e))

      (= c \/)
      (lambda (l c))

      :else
      (variable (l c)))))

(defn parser [l]
  (let [c (l)]
    (if (nil? c)
      []
      (let [e (expression (l c))]
        (cons e (parser l))))))

(defn -main
  [& args]
  (new-lexer args))
