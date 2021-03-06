(ns clj-thamil.மொழியியல்
  (:require [clj-thamil.format :as fmt])
  #+clj (:use clj-thamil.core)
  #+cljs (:use-macros [clj-thamil.core :only [வரையறு விவரி மீதி வரையறு-செயல்கூறு பெறு எதாவது பூலியன் என்னும்போது
                                               வைத்துக்கொள் கடைசி பொறுத்து எண்ணு முதல் இரண்டாம் தொடை
                                               கடைசியின்றி அன்று மற்றும் அல்லது தொடு செயல்படுத்து செயல்கூறு]]))


(வரையறு மெய்-தொடக்கம்-எழுத்துகள் fmt/c-cv-letters)

(வரையறு உயிரெழுத்துகள் fmt/vowels)

(வரையறு மெய்யெழுத்துகள் fmt/consonants)

(வரையறு உயிர்மெய்யெழுத்துகள் (தட்டையாக்கு (விவரி மீதி மெய்-தொடக்கம்-எழுத்துகள்)))

(வரையறு தொடை->எழுத்துகள் fmt/str->letters)

(வரையறு தொடை->ஒலியன்கள் fmt/str->phonemes)

(வரையறு-செயல்கூறு ஒலியன்கள்->எழுத்து [ஒலியன்கள்] (பெறு fmt/inverse-phoneme-map ஒலியன்கள்))

;;;;;;;;
;; எழுத்து
;; letters
;;;;;;;;

(வரையறு-செயல்கூறு எழுத்தா? [ச] (fmt/in-trie? ச))

(வரையறு-செயல்கூறு மெய்யெழுத்தா? [எ] (பூலியன் (எதாவது #{எ} மெய்யெழுத்துகள்)))

(வரையறு-செயல்கூறு உயிரெழுத்தா? [எ] (பூலியன் (எதாவது #{எ} உயிரெழுத்துகள்)))

(வரையறு-செயல்கூறு உயிர்மெயெழுத்தா? [எ] (பூலியன் (எதாவது #{எ} உயிர்மெய்யெழுத்துகள்)))

;;;;;;;;
;; அசை
;; syllables
;;;;;;;;

(வரையறு குறில்-உயிரெழுத்துகள் #{"அ" "இ" "உ" "எ" "ஒ"})

(வரையறு நெடில்-உயிரெழுத்துகள் #{"ஆ" "ஈ" "ஊ" "ஏ" "ஓ"})

(வரையறு-செயல்கூறு நெடிலா?
  "எழுத்து நெடில் எழுத்தா என்பதைத் திருப்பிக் கொடுக்கும்
  returns whether the letter is நெடில் (has long vowel sound)"
  [எழுத்து]
  (பூலியன்
   (என்னும்போது (எழுத்தா? எழுத்து)
     ;; ஒலியன் = phoneme
     (வைத்துக்கொள் [ஒலியன்கள் (தொடை->ஒலியன்கள் எழுத்து)
                 கடைசி-ஒலியன் (கடைசி ஒலியன்கள்)]
       (பெறு நெடில்-உயிரெழுத்துகள் கடைசி-ஒலியன்)))))

(வரையறு-செயல்கூறு குறிலா?
  "எழுத்து குறில் எழுத்தா என்பதைத் திருப்பிக் கொடுக்கும்
  returns whether the letter is குறில் (has short vowel sound)"
  [எழுத்து]
  (பூலியன்
   (என்னும்போது (எழுத்தா? எழுத்து)
     (->> (தொடை->ஒலியன்கள் எழுத்து)
          கடைசி
          (பெறு குறில்-உயிரெழுத்துகள்)))))

;;;;;;;;
;; ஒலியன்
;; phonemes
;;;;;;;;

(வரையறு முன்னொட்டா? fmt/prefix?)

(வரையறு பின்னொட்டா? fmt/suffix?)

;;;;;;;;
;; விகுதி
;; suffixes
;;;;;;;;

;; பன்மை
;; plurals

(வரையறு-செயல்கூறு பன்மை
  "ஒரு சொல்லை அதன் பன்மை வடிவத்தில் ஆக்குதல்
  takes a word and pluralizes it"
  [சொல்]
  (வைத்துக்கொள் [எழுத்துகள் (தொடை->எழுத்துகள் சொல்)]
    (பொறுத்து

     ;; (fmt/seq-prefix? (புரட்டு சொல்) (புரட்டு "கள்"))
     (பின்னொட்டா? சொல் "கள்")
     சொல்
     
     (= "ம்" (கடைசி எழுத்துகள்))
     (செயல்படுத்து தொடை (தொடு (கடைசியின்றி எழுத்துகள்) ["ங்கள்"]))
     
     (மற்றும் (= 1 (எண்ணு எழுத்துகள்))
            (நெடிலா? சொல்))
     (தொடை சொல் "க்கள்")

     (மற்றும் (= 2 (எண்ணு எழுத்துகள்))
            (ஒவ்வொன்றுமா? அடையாளம் (விவரி குறிலா? எழுத்துகள்)))
     (தொடை சொல் "க்கள்")

     (மற்றும் (= 2 (எண்ணு எழுத்துகள்))
            (குறிலா? (முதல் எழுத்துகள்))
            (= "ல்" (இரண்டாம் எழுத்துகள்)))
     (தொடை (முதல் எழுத்துகள்) "ற்கள்")

     (மற்றும் (= 2 (எண்ணு எழுத்துகள்))
            (குறிலா? (முதல் எழுத்துகள்))
            (= "ள்" (இரண்டாம் எழுத்துகள்)))
     (தொடை (முதல் எழுத்துகள்) "ட்கள்")
     
     :அன்றி
     (தொடை சொல் "கள்"))))

;; சந்தி (விதிகள்)
;; (rules for) joining words/suffixes

(வரையறு-செயல்கூறு சந்தி
  [சொல்1 சொல்2]
  (வைத்துக்கொள் [எழுத்துகள்1 (தொடை->எழுத்துகள் சொல்1)
              எழுத்துகள்2 (தொடை->எழுத்துகள் சொல்2)
              ஒலியன்கள்1 (தொடை->ஒலியன்கள் சொல்1)
              ஒலியன்கள்2 (தொடை->ஒலியன்கள் சொல்2)
              சொ1-கஒ (கடைசி ஒலியன்கள்1)
              சொ2-முஒ (முதல் ஒலியன்கள்2)]
    (பொறுத்து

     (மற்றும் (உயிரெழுத்தா? சொ2-முஒ)
            (பெறு #{"இ" "ஈ" "ஏ" "ஐ"} சொ1-கஒ))
     (செயல்படுத்து தொடை சொல்1 (ஒலியன்கள்->எழுத்து ["ய்" சொ2-முஒ]) (மீதி சொல்2))

     (மற்றும் (உயிரெழுத்தா? சொ2-முஒ)
            (பெறு #{"அ" "ஆ" "ஊ" "ஒ" "ஓ" "ஔ"} சொ1-கஒ))
     (செயல்படுத்து தொடை சொல்1 (ஒலியன்கள்->எழுத்து ["வ்" சொ2-முஒ]) (மீதி சொல்2))

     (மற்றும் (உயிரெழுத்தா? சொ2-முஒ)
            (= "உ" சொ1-கஒ)
            (= 2 (எண்ணு எழுத்துகள்1))
            (ஒவ்வொன்றுமா? குறிலா? எழுத்துகள்1))
     (செயல்படுத்து தொடை சொல்1 (ஒலியன்கள்->எழுத்து ["வ்" சொ2-முஒ]) (மீதி சொல்2))

     (மற்றும் (உயிரெழுத்தா? சொ2-முஒ)
            (= "உ" சொ1-கஒ)
            (அன்று (மற்றும் (= 2 (எண்ணு எழுத்துகள்1))
                         (ஒவ்வொன்றுமா? குறிலா? எழுத்துகள்1))))
     (செயல்படுத்து தொடை (தொடு (கடைசியின்றி எழுத்துகள்1) (ஒலியன்கள்->எழுத்து [(கடைசி (கடைசியின்றி ஒலியன்கள்1)) சொ2-முஒ]) (மீதி சொல்2)))

     
     (மற்றும் (உயிரெழுத்தா? சொ2-முஒ)
            (= 2 (எண்ணு எழுத்துகள்1))
            (குறிலா? (முதல் எழுத்துகள்1))
            (மெய்யெழுத்தா? (இரண்டாம் எழுத்துகள்1)))
     (செயல்படுத்து தொடை (தொடு சொல்1 [(ஒலியன்கள்->எழுத்து [சொ1-கஒ சொ2-முஒ])] (மீதி சொல்2)))

     (மற்றும் (உயிரெழுத்தா? சொ2-முஒ)
            (மெய்யெழுத்தா? சொ1-கஒ))
     (செயல்படுத்து தொடை (தொடு (கடைசியின்றி எழுத்துகள்1) [(ஒலியன்கள்->எழுத்து [சொ1-கஒ சொ2-முஒ])] (மீதி சொல்2)))

     :அன்றி
     (தொடை சொல்1 சொல்2)
     
     )))

;; வேற்றுமை
;; noun cases

(வரையறு-செயல்கூறு வேற்றுமை-முன்-மாற்றம்
  "ஒரு பெயர்ச்சொல்லுக்கு வேற்றுமை விகுதி சேர்க்கும் முன் செய்யவேண்டிய மாற்றம்
  change that is required before adding a case suffix to a noun"
  [சொல்]
  (வைத்துக்கொள் [எழுத்துகள் (தொடை->எழுத்துகள் சொல்)
              ஒலியன்கள் (தொடை->ஒலியன்கள் சொல்)
              கஎ (கடைசி எழுத்துகள்)
              கஒ (கடைசி ஒலியன்கள்)]
    (பொறுத்து

     (= "ம்" (கடைசி எழுத்துகள்))
     (செயல்படுத்து தொடை (தொடு (கடைசியின்றி எழுத்துகள்) ["த்த்"]))

     (மற்றும் (பெறு #{"டு" "று"} கஎ)
            (அல்லது (மற்றும் (= 2 (எண்ணு எழுத்துகள்))
                          (ஒவ்வொன்றுமா? குறிலா? எழுத்துகள்))
                   (மெய்யெழுத்தா? (கடைசி (கடைசியின்றி எழுத்துகள்)))))
     சொல்

     (= "டு" கஎ)
     (செயல்படுத்து தொடை (தொடு (கடைசியின்றி எழுத்துகள்) ["ட்ட்"]))

     (= "று" கஎ)
     (செயல்படுத்து தொடை (தொடு (கடைசியின்றி எழுத்துகள்) ["ற்ற்"]))

     :அன்றி
     சொல்)))

(வரையறு-செயல்கூறு வேற்றுமை
  "ஒரு பெயர்ச்சொல்லுக்கு ஒரு வேற்றுமை விகுதியைச் சேர்த்தல்
  adds a case suffix to a noun"
  [சொல் வே]
  (வைத்துக்கொள் [எழுத்துகள் (தொடை->எழுத்துகள் சொல்)
              ஒலியன்கள் (தொடை->ஒலியன்கள் சொல்)]
    (எனில் (மற்றும் (= "உக்கு" வே)
                 (அல்லது (பெறு #{"இ" "ஈ" "ஐ"} (கடைசி ஒலியன்கள்))
                        (எதாவது (செயல்கூறு [தொடை] (பின்னொட்டா? சொல் தொடை))
                                ["ஆய்"])))
      (வேற்றுமை சொல் "க்கு")
      (-> சொல்
          வேற்றுமை-முன்-மாற்றம்
          (சந்தி வே)))))
