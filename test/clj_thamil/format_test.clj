(ns clj-thamil.format-test
  (:use clojure.test
        clj-thamil.format
        clj-thamil.core))

(def words ["பந்து" "பந்தி" "பத்து" "பந்துகள்" "பந்தயம்" "பந்தாடு" "பந்தல்"])

(deftest word-letter-test
  (testing "splitting strings of தமிழ் characters into constituent தமிழ் characters"
    (is (= [] (str->letters "")))
    (is (= [] (str->letters nil)))
    (is (= ["த"] (str->letters "த")))
    (is (= [" " "த"] (str->letters " த")))
    (is (= ["த" " "] (str->letters "த ")))
    (is (= ["த்"] (str->letters "த்")))
    (is (= ["த" "மி" "ழ்"] (str->letters "தமிழ்")))
    (is (= ["த" "மி" "ழ்" " "] (str->letters "தமிழ் ")))
    (is (= ["s" "o" "f" "t" "w" "a" "r" "e" "=" "மெ" "ன்" "பொ" "ரு" "ள்" "," "." "." "."] (str->letters "software=மென்பொருள்,..."))))
  (testing "letter ordering"
    ;; boundary cases
    (is (= true (letter-before? nil nil)))
    (is (= true (letter-before? nil "")))
    (is (= false (letter-before? "" nil)))
    (is (= true (letter-before? nil "அ")))
    (is (= true (letter-before? nil "a")))
    (is (= true (letter-before? "a" "அ")))
    ;; equal inputs
    (is (= false (letter-before? "அ" "அ")))
    ;; தமிழ்
    (is (= true (letter-before? "அ" "ஆ")))
    (is (= true (letter-before? "ஆ" "இ")))
    (is (= true (letter-before? "அ" "ஔ")))
    (is (= true (letter-before? "ஔ" "ஃ")))
    (is (= true (letter-before? "ஃ" "க்")))
    (is (= true (letter-before? "க்" "க")))
    (is (= true (letter-before? "க" "கா")))
    (is (= true (letter-before? "க்" "கௌ")))
    (is (= false (letter-before? "க்" "ஃ")))
    (is (= true (letter-before? "கௌ" "ங்")))
    (is (= false (letter-before? "ங்" "கௌ")))
    ;; ASCII
    (is (= true (letter-before? "a" "z")))
    (is (= true (letter-before? "A" "Z")))
    (is (= true (letter-before? "Z" "a")))
    (is (= true (letter-before? "0" "9")))
    (is (= true (letter-before? "9" "A")))
    ;; comparator / sorting
    (is (= ["அ" "ஆ" "இ" "ஒ" "ஓ" "ஔ" "ஃ" "க்" "க" "ன்" "ன" "னா" "னு" "னௌ"] (sort-by identity letter-comp ["இ" "க" "ஃ" "ன" "னு" "னௌ" "னா" "ஆ" "க்" "அ" "ஔ" "ஓ" "ன்" "ஒ"]))))
  (testing "word ordering"
    (is (= false (word-before? "அ" "அ")))
    (is (= true (word-before? "அ" "அது"))) 
    (is (= true (word-before? "படம்" "பாடம்")))
    (is (= false (word-before? "பாடம்" "படம்"))) 
    (is (= false (word-before? "படம்" "பட்டம்")))
    (is (= true (word-before? "பட்டம்" "படம்"))) 
    (is (= true (word-before? "பாடம்" "பாதம்"))) 
    (is (= false(word-before? "பாதம்" "பாடம்")))
    (is (= false (word-before? "கடமை" "கட்டம்")))    
    (is (= true (word-before? "கட்டம்" "கடமை"))) 
    (is (= true (word-before? "கட்டு" "கெட்டு")))
    (is (= false (word-before? "கெட்டு" "கட்டு"))) 
    (is (= false (word-before? "பைந்தமிழ்" "பந்தல்")))
    (is (= true (word-before? "பந்தல்" "பைந்தமிழ்"))) 
    (is (= true (word-before? "அப்பம்" "ஆப்பம்")))
    (is (= false (word-before? "ஆப்பம்" "அப்பம்"))) 
    (is (= false (word-before? "நுளம்பு" "கொசு")))
    (is (= true (word-before? "கொசு" "நுளம்பு"))) 
    (is (= true (word-before? "ஈ" "கொசு")))
    (is (= false (word-before? "கொசு" "ஈ")))))
