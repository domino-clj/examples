(ns domino.simpleui-example.web.domino
    (:require
      [domino.core :as domino]
      [domino.simpleui-example.web.ws :as ws]
      [domino.simpleui-example.web.views.disp.home :as disp.home]))

;; adapted from domino README.md

(def schema
  {:model   [[:demographics
              [:height {:id :height}]
              [:weight {:id :weight}]]
             [:vitals
              [:bmi {:id :bmi}]]]
   :events  [{:inputs  [:height :weight]
              :outputs [:bmi]
              :handler (fn [{{:keys [height weight]} :inputs
                             {:keys [bmi]} :outputs}]
                         {:bmi (if (and height weight)
                                 (/ weight (* height height))
                                 bmi)})}]
   :effects [{:inputs [:bmi]
              :handler (fn [{{:keys [bmi]} :inputs}]
                         (ws/broadcast
                          (disp.home/bmi-label bmi)))}]})

(def ctx (atom (domino/initialize schema {:demographics {:height 1.6 :weight 60.0}})))

(defn transact! [id v]
  (swap! ctx domino/transact [[[id] v]])
  nil)

(defn select [id]
  (domino/select @ctx id))
