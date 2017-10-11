(ns cs340-lab11.core-test
  (:require [clojure.test :refer :all]
            [cs340-lab11.core :refer :all]))

;; Compare floating point values for "fuzzy" equality.
(defn fuzzy= [x y]
  (< (Math/abs (- x y)) 0.0001))

;; Test prices map.
(def fruit-prices
  {:apple 0.75,
   :orange 0.80,
   :pomegranate 2.50,
   :banana 0.50,
   :plum 1.20,
   :peach 1.00,
   :persimmon 1.75,
   :lime 0.60})

;; Test invoice.
(def yummy-fruit
  [[:persimmon 2]
   [:orange 3]
   [:peach 1]
   [:plum 10]
   [:pomegranate 5]])

(deftest tally-item-test
  (testing "tally-item"
     (is (fuzzy= 4.6 (tally-item {:apple 1.5, :pear 2.3} [:pear 2])))
     (is (fuzzy= 3.5 (tally-item fruit-prices [:persimmon 2])))
     (is (fuzzy= 2.4 (tally-item fruit-prices [:orange 3])))
     (is (fuzzy= 1.0 (tally-item fruit-prices [:peach 1])))
     (is (fuzzy= 0.0 (tally-item fruit-prices [:banana 0])))
    ))

(deftest invoice-total-test
  (testing "invoice-total"
     (is (fuzzy= 5.3 (invoice-total {:apple 1.5, :pear 2.3} [[:apple 2] [:pear 1]])))
     (is (fuzzy= 31.4 (invoice-total fruit-prices yummy-fruit)))))

(deftest swapsies-test
  (testing "swapsies"
     (is (= ["world" "hello"] (swapsies ["hello" "world"])))
     (is (= [42 17] (swapsies [17 42])))
     (is (= [:plugh :xyzzy] (swapsies [:xyzzy :plugh])))
     ))

(deftest mulv3-test
  (testing "mulv3"
     (is (every? identity (map fuzzy= [5 7.5 10] (mulv3 [2 3 4] 2.5))))
     (is (every? identity (map fuzzy= [0 0 0] (mulv3 [11 22 33] 0))))     
     (is (every? identity (map fuzzy= [11 22 33] (mulv3 [11 22 33] 1.0))))     
     ))

(deftest mulv-test
  (testing "mulv"
     (is (every? identity (map fuzzy= [5 7.5 10] (mulv [2 3 4] 2.5))))
     (is (every? identity (map fuzzy= [0 0 0] (mulv [11 22 33] 0))))     
     (is (every? identity (map fuzzy= [11 22 33] (mulv [11 22 33] 1.0))))     
     (is (every? identity (map fuzzy= [2 4 6 8 10 12 14 16 18 20] (mulv [1 2 3 4 5 6 7 8 9 10] 2.0))))
     
     ))
