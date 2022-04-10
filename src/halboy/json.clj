(ns halboy.json
  (:require
   [clojure.walk :refer [keywordize-keys]]
   [halboy.data :refer [transform-values]]
   [halboy.resource :as hal]
   [cheshire.core :as json])
  (:import (com.fasterxml.jackson.core JsonParseException)))

(declare map->resource resource->map)

(defn- extract-links [m]
  (:_links m {}))

(defn- extract-properties [m]
  (or (dissoc m :_links :_embedded) {}))

(defn- map->embedded-resource [m]
  (if (map? m)
    (map->resource m)
    (map map->embedded-resource m)))

(defn- extract-embedded [body]
  (transform-values (:_embedded body {}) map->embedded-resource))

(defn- links->map [resource]
  (let [links (:links resource)]
    (when (seq links)
      {:_links links})))

(defn- embedded-resource->map [resource]
  (if (map? resource)
    (resource->map resource)
    (map embedded-resource->map resource)))

(defn- embedded->map [resource]
  (let [resources (transform-values (:embedded resource) embedded-resource->map)]
    (when (seq resources)
      {:_embedded resources})))

(defn map->resource
  "Parses a map representing a HAL+JSON response into a
  resource"
  [m]
  (-> (hal/new-resource)
    (hal/add-links (extract-links m))
    (hal/add-resources (extract-embedded m))
    (hal/add-properties (extract-properties m))))

(defn json->resource
  "Parses a HAL+JSON string into a resource"
  [s]
  (try
    (-> (json/parse-string s)
      keywordize-keys
      map->resource)
    (catch JsonParseException e
      (throw (ex-info "Failed to parse json"
               {:exception e
                :string    s})))))

(defn resource->map
  "Transforms a resource into a map representing a HAL+JSON
  response"
  [resource]
  (merge
    (links->map resource)
    (embedded->map resource)
    (:properties resource)))

(defn resource->json
  "Transforms a resource into a HAL+JSON string"
  [resource]
  (json/generate-string (resource->map resource)))
