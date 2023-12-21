(ns domino.simpleui-example.web.views.home
    (:require
      [domino.simpleui-example.web.domino :as domino]
      [domino.simpleui-example.web.htmx :refer [page-htmx]]
      [domino.simpleui-example.web.views.disp.home :as disp.home]
      [simpleui.core :as simpleui :refer [defcomponent]]))

(defcomponent ^:endpoint bmi-form [req ^:double height ^:double weight]
  (cond
   height (domino/transact! :height height)
   weight (domino/transact! :weight weight)
   :else (disp.home/form
          (domino/select :height)
          (domino/select :weight)
          (domino/select :bmi))))

(defn ui-routes [base-path]
  (simpleui/make-routes
   base-path
   (fn [req]
     (page-htmx
      (bmi-form req)))))

