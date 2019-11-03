(ns domino-re-frame-example.prod
  (:require
    [domino-re-frame-example.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
