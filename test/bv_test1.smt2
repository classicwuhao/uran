
;this file is automatically generated: Tue Jul 04 15:46:41 IST 2017

;declarations generated

(declare-const bv1 (_ BitVec 4))
(declare-const bv3 (_ BitVec 4))
(declare-const bv2 (_ BitVec 4))

;formula(s) generated
(assert (= bv1 (bvand bv1 bv2)))
(assert (= bv1 (bvor bv2 bv3)))
(assert (= bv2 (bvxor bv1 bv3)))
(assert (= bv3 (bvadd bv1 #xf)))
(assert (= bv1 #xf))

 ;end of formula 