
;this file is automatically generated: Wed Jan 25 16:37:46 GMT 2017

;declarations generated

(declare-fun s1 () Int)
(declare-fun s2 () Int)
(declare-fun s3 () Int)
(declare-fun s4 () Int)
(declare-fun s5 () Int)
(declare-fun c1 () Bool)
(declare-fun c2 () Int)
(declare-fun c3 () Real)
(declare-fun fun1 ( Int Bool) Real)
(declare-fun fun2 ( Bool Bool Bool) Int)
(declare-fun fun3 ( Bool Bool) Int)
(declare-fun x1 () Bool)
(declare-fun x2 () Bool)
(declare-fun x3 () Bool)
(declare-fun x4 () Bool)
(declare-fun x5 () Bool)

;formula(s) generated
(assert (= 5 ( fun1 c2  c1 ) ))
(assert (or (or (and (and x1 (not x2 )) (not x3 )) (and (and (not x1 ) x2) (not x3 ))) (and (and (not x1 ) (not x2 )) x3)))
(assert (forall (( x1 Bool )( x2 Bool )( x3 Bool )) (= 10 ( fun2 x2  x3  x1 ) )))
(assert (= (+ (+ (+ (+ (+ s1 s2) s2) s3) s4) s5) 30))
(assert (or (or (or (or x1 x2) x3) x4) x5))
(assert (and (>= s1 0) (<= s1 1)))
(assert (and (> s2 0) (< s2 2)))
(assert (> ( fun2 x1  x2  x3 )  ( fun3 x3  x4 ) ))

 ;end of formula 
(assert (and (and x3 x4) x5))
