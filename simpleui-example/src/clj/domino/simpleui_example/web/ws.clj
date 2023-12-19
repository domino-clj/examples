(ns domino.simpleui-example.web.ws
    (:require
      [simpleui.render :as render]
      [ring.adapter.undertow.websocket :as ws]))

(def channels (atom #{}))

(defn add-channel [channel]
  (swap! channels conj channel))

(defn remove-channel [channel]
  (swap! channels disj channel))

(defn broadcast [snippet]
  (let [snippet (render/html snippet)]
    (doseq [channel @channels]
      (ws/send snippet channel))))
