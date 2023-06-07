(ns halboy.http.protocol)

(defprotocol HttpClient
  (exchange [self {:keys [method url query-params body options]}]))
