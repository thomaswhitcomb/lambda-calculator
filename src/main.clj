 (ns main (:gen-class)
    (:require
      [clojure.test :refer [is]]
      [clojure.string :as s]
      [lexer :as l]
      [parser :as p]
      [evaluator :as e]
      ))

(defn -main
  [code]
  (println (p/parse (l/new-lexer code))))

(is (= (p/parse (l/new-lexer "/y.(/x.x (y x))"))
       (list {:type :lambda,
              :parm {:type :variable, :value "y"},
              :body {
                     :type :appl,
                     :lhs {
                            :type :lambda,
                            :parm {:type :variable, :value "x"},
                            :body {:type :variable, :value "x"}},
                     :rhs {
                             :type :appl,
                             :lhs {:type :variable, :value "y"},
                             :rhs {:type :variable, :value "x"}}}})))
(is (= (p/parse (l/new-lexer "(/y.((/x.x (/z.z b)) a) (y x))"))
       (list {:type :appl,
              :lhs {
                     :type :lambda,
                     :parm {:type :variable, :value "y"}
                     :body {
                            :type :appl,
                            :lhs {
                                   :type :appl,
                                   :lhs {
                                          :type :lambda,
                                          :parm {:type :variable, :value "x"}
                                          :body {
                                                 :type :variable,
                                                 :value "x"}},
                                   :rhs {
                                           :type :appl,
                                           :lhs {
                                                  :type :lambda,
                                                  :parm {:type :variable, :value "z"}
                                                  :body {
                                                         :type :variable,
                                                         :value "z"}},
                                           :rhs {
                                                   :type :variable,
                                                   :value "b"}}},
                            :rhs {
                                    :type :variable,
                                    :value "a"}}},
              :rhs {
                      :type :appl,
                      :lhs {
                             :type :variable,
                             :value "y"},
                      :rhs {
                              :type :variable,
                              :value "x"}}})))
