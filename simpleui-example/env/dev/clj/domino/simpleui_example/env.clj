(ns domino.simpleui-example.env
  (:require
    [clojure.tools.logging :as log]
    [domino.simpleui-example.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[simpleui-example starting using the development or test profile]=-"))
   :start      (fn []
                 (log/info "\n-=[simpleui-example started successfully using the development or test profile]=-"))
   :stop       (fn []
                 (log/info "\n-=[simpleui-example has shut down successfully]=-"))
   :middleware wrap-dev
   :opts       {:profile       :dev
                :persist-data? true}})
