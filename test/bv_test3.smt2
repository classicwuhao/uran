
;this file is automatically generated: Tue Jul 04 15:43:16 IST 2017

;declarations generated

(declare-const bv1 (_ BitVec 8))
(declare-const bv3 (_ BitVec 8))
(declare-const bv2 (_ BitVec 8))

;formula(s) generated
(assert (= bv2 (bvnand bv1 bv3)))
(assert (= bv2 (bvnor bv2 bv1)))
(assert (= bv3 (bvsub bv1 #xff)))
(assert (= bv1 #xff))

 ;end of formula 