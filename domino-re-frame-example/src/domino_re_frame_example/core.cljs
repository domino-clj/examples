(ns domino-re-frame-example.core
    (:require
      [reagent.core :as r]
      [re-frame.core :as rf]
      [goog.string :as string]
      [goog.string.format]
      [domino.core :as domino]))

(rf/reg-event-db
 :init
 (fn [_ [_ schema]]
   (domino/initialize! schema {})))

(rf/reg-event-db
 :event
 (fn [db [_ path value]]
   (domino/transact db [[path value]])))

(rf/reg-sub
 :path
 (fn [db [_ path]]
   (get-in (::domino/db db) path)))

(rf/reg-sub
 :db
 (fn [db _]
   (::domino/db db)))

(defn parse-float [s]
  (let [value (js/parseFloat s)]
    (when-not (js/isNaN value) value)))

(defn format-number [n]
  (when n (string/format "%.2f" n)))

(defn input [label path]
  [:div
   [:label label]
   [:input 
    {:type      :text
     :value @(rf/subscribe [:path path])
     :on-change #(rf/dispatch [:event path (-> % .-target .-value parse-float)])}]])

(defn home-page []
  [:div
   [:h2 "BMI Calculator"]
   [input "height (KG) " [:demographics :height]]
   [input "weight (M) " [:demographics :weight]]
   [:label "BMI " (format-number @(rf/subscribe [:path [:vitals :bmi]]))]
   [:hr]
   [:strong "DB state"]
   [:pre (str @(rf/subscribe [:db]))]])

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync
   [:init
    {:model   [[:demographics {}
                [:height {:id :height}]
                [:weight {:id :weight}]]
               [:vitals {}
                [:bmi {:id :bmi}]]]
     :events  [{:inputs  [:height :weight]
                :outputs [:bmi]
                :handler (fn [_ [height weight] [bmi]]
                           (if (and height weight)
                             [(/ weight (* height height))]
                             [bmi]))}]}])
  (mount-root))
