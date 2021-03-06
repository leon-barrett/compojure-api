(ns compojure.api.routes
  (:require [compojure.core :refer :all]))

(defmulti collect-routes identity)

(def +compojure-api-routes+ '+compojure-api-routes+)

(defmacro api-root [& body]
  (let [[details body] (collect-routes body)]
    `(do
       (def ~+compojure-api-routes+ '~details)
       (routes ~@body))))

(defmacro get-routes []
  `(try
     (eval +compojure-api-routes+)
     (catch RuntimeException _#
       (throw
         (IllegalStateException.
           (str
             "Coudn't find a +compojure-api-routes+ var defined in this ns. "
             "You should wrap your api in a compojure.api.routes/with-routes"
             " -macro to get your lovely routes collected."))))))
