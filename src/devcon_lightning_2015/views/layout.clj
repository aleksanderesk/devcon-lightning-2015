(ns devcon-lightning-2015.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [& body]
  (html5
    [:head
     [:title  "Welcome to devcon-lightning-2015"]
     (include-css "/css/bootstrap.min.css")
     (include-css "/css/github.css")
     (include-js "/js/highlight.pack.js")]
    [:body
     [:script "hljs.initHighlightingOnLoad();"]
     body]))
