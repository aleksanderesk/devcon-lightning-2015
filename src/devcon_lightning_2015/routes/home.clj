(ns devcon-lightning-2015.routes.home
  (:require [compojure.core :refer :all]
            [devcon-lightning-2015.views.layout :as layout]))

(def example-request
  {:ssl-client-cert nil :cookies {}
   :remote-addr "0:0:0:0:0:0:0:1"
   :params {}
   :flash nil 
   :route-params {}
   :headers {"accept-encoding" "gzip, deflate, sdch" "cache-control" "max-age=0" "connection" "keep-alive" "user-agent" "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36" "accept-language" "en-US,en;q=0.8" "accept" "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" "host" "localhost:3000"} 
   :server-port 3000
   :content-length nil 
   :form-params {}
   :session/key nil
   :query-params {}
   :content-type nil 
   :character-encoding nil
   :uri "/"
   :server-name "localhost"
   :query-string nil
   :body ""
   :multipart-params {}
   :scheme 
   :http
   :request-method :get
   :session {}})

(def route-example
  (clojure.string/join "\n"
                       ["(defroutes app-routes"
                        "  (route/resources \"/\")"
                        "  (route/not-found \"Not Found\"))"
                        ""
                        "(def app"
                        "  (-> (routes home-routes app-routes)"
                        "      (handler/site)"
                        "      (wrap-base-url)))"
                        ""
                        "(defn get-slide [slide-num]"
                        "  (layout/common (get slides (keyword slide-num))))"
                        ""
                        "(defroutes home-routes"
                        "  (GET \"/\" [] (get-slide \"1\"))"
                        "  (GET \"/:slide\" [slide] (get-slide slide)))" 
                        ]))

(defn make-nav [left right]
   [:div.navbar.navbar.default.col-md-6.col-md-offset-3
    [:a.btn.btn-primary {:href (str "/" left) :style "margin:8px"} "Prev Slide"]
    [:a.pull-right.btn.btn-primary {:href (str "/" right) :style "margin:8px"} "Next Slide"]])

(defn make-body [& body]
  (into [:div.col-md-6.col-md-offset-3] body))

(def slides
  {:1 [:div
       (make-nav nil 2)
       (make-body
         [:h1.text-center "F: HTTPRequest ⇒ HTTPResponse"]
         [:h3.text-center "An Overview of Web Development in Clojure"]
         [:h3.text-center "Aleksander Eskilson"]
         [:h3.text-center "github.com/aleksanderesk/devcon-lightning-2015"])]
   :2 [:div
       (make-nav 1 3)
       (make-body
         [:h1.text-center "Ring: What's in an HTTPRequest?"]
         [:p "A Clojure dictionary representation of the requests various parameters"]
         [:pre
          [:code.clojure (str example-request)]]
         [:h1.text-center "What's in an HTTPResponse?"]
         [:p "A Clojure dictionary representation of the request"]
         [:pre
          [:code.clojure (str {:status 200
                               :headers {}
                               :body "Hello World!"})]]
         [:p "This map gets translated to an actual HTTPResponse. It can be more complicated and support other bodies and status messages:"]
         [:ul
          [:li "JSON"]
          [:li "Files"]])]
   :3 [:div
       (make-nav 2 3)
       (make-body
         [:h1.text-center "How do We Handle Requests?"]
         [:p "Ring provides means of handling HTTP protocol, we'd like a nice abstraction to handle routing traffic either to provide pages or to expose components of an API."]
         [:h1.text-center "Enter Compojure"]
         [:pre
          [:code.clojure route-example]]
         [:p "Familiar with Ruby Sinatra? Compojure provides similar methods of destructuring all types of HTTPRequests"]
         [:ul
          [:li "GET"]
          [:li "PUT"]
          [:li "POST"]
          [:li "DELETE"]]
         [:form {:action "/" :method "post"}
          [:div.form-group
           [:label "Name:"]
           [:input.form-control {:type "text" :name "name"}]]
          [:button.btn.btn-default {:type "submit"} "Submit"]])]
   :4 [:div
       (make-nav 3 5)
       (make-body
         [:h1.text-center "Why Would We do This?"]
         [:p "Why use Clojure for Web Dev?"]
         [:ul
          [:li "Clojure's a natural backend language"]
          [:li "Libraries built over, and interop with Java makes doing a large number of tasks accessible in the native language"]
          [:li "Also still good in larger production, with facilities to manage:"
           [:ul
            [:li "Authorization/Authentication/Cryptography - Buddy/Friend"]
            [:li "Databases - Natural jdbc interop, Koram/Yesql"]
            [:li "Templating - Hiccup (native data structures), Mustache, Selmer"]
            [:li "Clojurescript - Om, Reagent"]]]
          [:li "Clojure is a great host language - great, rich data structures, higher-order functions (an elegant weapon, for a more civilized age), macros"]])]
   :5 [:div
       (make-nav 4 6)
       (make-body
         [:h1.text-center "Why Would We do This?"]
         [:p "Some Philosophical Considerations"]
         [:ul
          [:li "Good for smaller, roll-your own style projects, when a larger framework would encumber you, but still good for larger production, with its libraries and interop"]
          [:li "Nice for deployment, lein lets you package the whole app as a jar"]
          [:li "Lein also manages dependencies for you quite nicely"]
          [:li "Localizes mutation, makes tracing flow of logic a little easier"]
          [:li "Already a nice representation of a common need, We want to communicate over HTTP"]])]
   :6 [:div
       (make-nav 5 1)
       (make-body
         [:h1.text-center "End"]
         [:h4.text-center "Questions?"])]}) 

(defn get-slide [slide-num]
  (layout/common (get slides (keyword slide-num))))

(defn post-demo [params]
  (let [a-name (:name params)]
    (layout/common [:div
                    (make-nav 3 4)
                    (make-body
                      [:h1.text-center (str "Hello, " a-name "!")])])))

(defroutes home-routes
  (GET "/" [] (get-slide "1"))
  (GET "/:slide" [slide] (get-slide slide))
  (POST "/" {params :params} (post-demo params)))
