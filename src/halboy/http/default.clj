(ns halboy.http.default
  (:require
   [clojure.walk :refer [stringify-keys keywordize-keys]]
   [cheshire.core :as json]
   [org.httpkit.client :as http]
   [halboy.argutils :refer [deep-merge]]
   [halboy.http.protocol :as protocol])
  (:import [com.fasterxml.jackson.core JsonParseException]))

(def default-http-options
  {:as      :text
   :headers {"Content-Type" "application/json"
             "Accept"       "application/hal+json"}})

(defn- update-if-present [m ks func]
  (if (get-in m ks)
    (update-in m ks func)
    m))

(defn http-method->fn [method]
  (get-in
    {:head   http/head
     :get    http/get
     :post   http/post
     :put    http/put
     :patch  http/patch
     :delete http/delete}
    [method]))

(defn- with-default-options [m]
  (deep-merge default-http-options m))

(defn- with-json-body [m]
  (update-if-present m [:body] json/generate-string))

(defn- parse-json-response [response]
  (try
    (update-if-present
      response [:body]
      #(-> (json/parse-string %)
         (keywordize-keys)))
    (catch JsonParseException ex
      (assoc response
        :error {:code :not-valid-json
                :cause ex}))))

(defn- with-transformed-params [m]
  (update-if-present m [:query-params] stringify-keys))

(defn- format-for-halboy [response]
  (merge
    (select-keys response [:error :body :headers :status])
    {:url (get-in response [:opts :url])
     :raw response}))

(deftype DefaultHttpClient []
  protocol/HttpClient
  (exchange [_ {:keys [url method] :as request}]
    (let [request #p (-> request
                    (with-default-options)
                    (with-transformed-params)
                    (with-json-body))
          http-fn (http-method->fn method)]
      (-> @(http-fn url request)
        (parse-json-response)
        (format-for-halboy)))))

(defn new-http-client []
  (DefaultHttpClient.))
