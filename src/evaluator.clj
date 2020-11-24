 (ns evaluator (:gen-class)
    (:require
      [clojure.test :refer [is]]
      [clojure.string :as s]
      ))

(defn substitute
  ([e1 e2]
   (println "substitute2 " e1 e2)
   (if (= (e1 :type) :lambda)
     (substitute  e2 (e1 :parm) (e1 :body))
     e1))

  ([a p b]
   (println "substitute3 " a p b)
   (cond
     (and (= (b :type) :variable) (= (p :type) :variable) (= (b :value) (p :value)))
     a
     (and (= (b :type) :variable) (= (p :type) :variable))
     b
  )))

(defn to-str [{t :type :as expression}]
  (cond
    (= t :variable)
    (expression :value)

    (= t :lambda)
    (str  "/" ((expression :parm) :value) "." (to-str (expression :body)))

    (= t :appl)
    (str "(" (to-str (expression :lhs)) " " (to-str (expression :rhs)) ")")
    ))

(defn run [{t :type :as expression}]
  (println "run " expression)
  (cond
    (= t :variable)
    expression

    (= t :lambda)
    (assoc expression :body (run (expression :body)))

    (= t :appl)
    (let [rhs-exp (run  (expression :rhs))
          lhs-exp (run  (expression :lhs))]
      (if (= ((expression :lhs) :type) :lambda)
        (substitute rhs-exp ((expression :lhs) :parm)  ((expression :lhs) :body))
        (assoc expression :lhs lhs-exp :rhs rhs-exp)))
    ))



