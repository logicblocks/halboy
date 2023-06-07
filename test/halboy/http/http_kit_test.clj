(ns halboy.http.http-kit-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [org.httpkit.fake :refer [with-fake-http]]
   [halboy.http.http-kit :as http-kit-client]
   [halboy.http.protocol :as http]))

(def base-url "https://service.example.com")

(deftest halboy-http
  (testing "http_kit"
    (with-fake-http
      [{:url base-url :method :get} {:status 201 :body "{}"}]
      (let [client (http-kit-client/new-http-client)
            request {:url    base-url
                     :method :get}]
        (is (=
              (http/exchange client request)
              {:body    {}
               :headers {:content-type "text/html"
                         :server       "org.httpkit.fake"}

               :raw     {:body    {}
                         :headers {:content-type "text/html"
                                   :server       "org.httpkit.fake"}
                         :opts    {:as      :text
                                   :headers {"Accept"       "application/hal+json"
                                             "Content-Type" "application/json"}
                                   :method  :get
                                   :url     "https://service.example.com"}
                         :status  201}
               :status  201
               :url     "https://service.example.com"}))))))
