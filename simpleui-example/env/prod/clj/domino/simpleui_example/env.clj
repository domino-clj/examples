(ns domino.simpleui-example.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[simpleui-example starting]=-"))
   :start      (fn []
                 (log/info "\n-=[simpleui-example started successfully]=-"))
   :stop       (fn []
                 (log/info "\n-=[simpleui-example has shut down successfully]=-"))
   :middleware (fn [handler _] handler)
   :opts       {:profile :prod}})
