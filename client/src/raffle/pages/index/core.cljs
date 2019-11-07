(ns raffle.pages.index.core
  (:require
    ["@material-ui/core/CardActions" :default CardActions]
    ["@material-ui/core/CardContent" :default CardContent]
    ["@material-ui/core/Typography" :default Typography]
    ["@material-ui/core/CardMedia" :default CardMedia]
    ["@material-ui/core/Button" :default Button]
    ["@material-ui/core/Link" :default Link]
    ["@material-ui/core/Grid" :default Grid]
    ["@material-ui/core/Card" :default Card]
    [raffle.components :refer [button-link]]
    [raffle.pages.index.subs :as subs]
    [raffle.routing.core :as routing]
    [re-frame.core :as rf]
    [reagent.core :as r]))

(declare link-button raffle-item)

(defn card-grid [{:keys [^js classes]}]
  (let [items (rf/subscribe [::subs/items])]
    (fn []
      [:> Grid
       {:class     (.-cardGrid classes)
        :container true
        :spacing   4}
       (for [{:keys [id] :as item} @items]
         [raffle-item
          {:key     id
           :item    item
           :classes classes}])])))

(defn- link-button [{:keys [to params query replace? target] :as props} children]
  (let [navigate! (if replace? routing/replace! routing/navigate!)
        self-target? (or (nil? target) (= target "_self"))
        location (routing/resolve to params query)]
    (letfn [(-on-click [event]
              (let [left-button? (= (.-button event) 0)
                    modified? (or (.-metaKey event) (.-altKey event) (.-ctrlKey event) (.-shiftKey event))]
                (when (and left-button? self-target? (not modified?))
                  (.preventDefault event)
                  (navigate! to params query))))]
      (let [props* (r/merge-props props
                                  {:on-click  -on-click
                                   :component Link
                                   :href      location})]
        [:> Button props* children]))))

(defn- raffle-item [{:keys [item ^js classes]}]
  [:> Grid
   {:item true
    :xs   12
    :sm   6
    :md   4}
   [:> Card
    {:class (.-card classes)}
    [:> CardMedia
     {:class (.-cardMedia classes)
      :image (:image-url item)
      :title "Image title"}]
    [:> CardContent
     {:class (.-cardContent classes)}
     [:> Typography
      {:gutter-bottom true
       :variant       :h5
       :component     :h2}
      (str "Item " (:id item))]
     [:> Typography
      (:description item)]]
    [:> CardActions
     [link-button
      {:size   :small
       :color  :primary
       :to     :item
       :params {:id (:id item)}}
      "View"]]]])
