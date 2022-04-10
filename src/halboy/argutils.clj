(ns halboy.argutils)

(defn- map->key-value-pairs [m]
  (reduce concat (seq m)))

(defn- arg-map? [kvs]
  (and (= 1 (count kvs)) (map? (first kvs))))

(defn ensure-key-value-pairs [n]
  (if (arg-map? n)
    (map->key-value-pairs (first n))
    n))

(defn apply-pairs [f resource kvs]
  (if (empty? kvs)
    resource
    (if kvs
      (if (next kvs)
        (let [rel (first kvs)
              val (second kvs)]
          (recur f (f resource rel val) (nnext kvs)))
        (throw
          (IllegalArgumentException.
            "expected an even number of arguments, found odd number")))
      resource)))

(defn apply-pairs-or-map [f resource kvs]
  (apply-pairs f resource (ensure-key-value-pairs kvs)))

(defn deep-merge [v & vs]
  (letfn [(rec-merge [v1 v2]
            (if (and (map? v1) (map? v2))
              (merge-with deep-merge v1 v2)
              v2))]
    (when (some identity vs)
      (reduce rec-merge v vs))))
