
;this file is automatically generated: Fri Jan 27 12:04:21 GMT 2017

;declarations generated

(declare-const bv1 (_ BitVec 8))
(declare-const bv3 (_ BitVec 8))
(declare-const bv2 (_ BitVec 8))

;formula(s) generated
(assert (= bv1 (bvand bv1 bv2)))
(assert (= bv1 (bvor bv2 bv3)))
(assert (= bv2 (bvxor bv1 bv3)))
(assert (= bv2 (bvnand bv1 bv3)))
(assert (= bv2 (bvnor bv2 bv1)))
(assert (= bv3 (bvadd bv1 #xff)))
(assert (= bv1 #xff))

 ;end of formula 