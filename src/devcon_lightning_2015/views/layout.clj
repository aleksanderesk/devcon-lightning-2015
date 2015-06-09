(ns devcon-lightning-2015.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to devcon-lightning-2015"]
     (include-css "/css/screen.css")]
    [:body body]))
