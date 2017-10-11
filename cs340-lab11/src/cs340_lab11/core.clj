(ns cs340-lab11.core)

;; Compute the cost of a single invoice line item.
;;
;; Params:
;;    line-item - two-element vector: item (keyword value),
;;                followed by integer quantity
;;    prices - map of items (keyword values) to the price for a
;;             single quantity of that item
;;
;; Returns:
;;   The cost of the line item, meaning the price of the
;;   specified item multiplied by the quantity.
;;
;; Examples:
;;   (tally-item {:apple 1.5, :pear 2.3} [:pear 2]) => 4.6
;;
(defn tally-item
  [prices line-item]
  (* (prices (first line-item)) (last line-item)))

;; Compute the total cost of all of the line items
;; in the specified invoice.  Use the built-in map function
;; to apply an anonymous function to each line item in
;; the invoice.  The anonymous function should apply
;; the tally-item function to a single line item (along
;; with the prices map.)  Then use the built-in reduce
;; function to combine all of the line item costs to
;; a single total cost.
;;
;; Params:
;;    prices - map of items (keyword values) to the price for a
;;             single quantity of that item
;;    invoice - sequences of line items
;;
;; Returns:
;;    Total cost of all line items.
;;
;; Examples:
;;    (invoice-total {:apple 1.5, :pear 2.3} [[:apple 2] [:pear 1]])
;;       => 5.3
;;
(defn invoice-total [prices invoice]
  (reduce + (map (fn [line-item] (tally-item prices line-item))
     invoice)))

;; Take a two-element vector and return a two-element
;; vector with the elements of the original vector swapped.
;;
;; Params:
;;    v - a two-element vector
;;
;; Returns:
;;    a vector with the elements of the original vector swapped
;;
;; Examples:
;;   (swapsies ["hello" "world"]) => ["world" "hello"]
;;   (swapsies [17 42]) => [42 17]
;;   (swapsies [:xyzzy :plugh]) => [:plugh :xyzzy]
;;
(defn swapsies [v]
  "OHAI")

;; Multiply each element in a three-element vector by a specified
;; factor, returning a vector as a result.
;;
;; Params:
;;    vec - a three-element vector of numbers
;;    fac - a factor
;;
;; Returns:
;;    A three-element vector resulting from multiplying each member
;;    of the original vector by the factor.
;;
;; Examples:
;;    (mulv3 [2 3 4] 2.5) => [5 7.5 10]
;;
(defn mulv3 [vec fac]
  "OHAI!")

;; Multiply each element in an arbitrary-length vector by a
;; specified factor, returning a vector as a result.
;;
;; Params:
;;    vec - a vector of numbers
;;    fac - a factor
;;
;; Returns:
;;    A vector resulting from multiplying each member
;;    of the original vector by the factor.
;;
;; Examples:
;;    (mulv [2 3 4] 2.5) => [5 7.5 10]
;;    (mulv [1 2 3 4 5 6 7 8 9 10] 2) => [2 4 6 8 10 12 14 16 18 20]
;;
;; Hint: use the built-in mapv function to apply an anonymous
;; function which can multiply a single value by the specified factor.
;;
(defn mulv [vec fac]
  "KTHXBYE!")
