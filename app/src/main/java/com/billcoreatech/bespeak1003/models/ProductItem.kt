package com.billcoreatech.bespeak1003.models

/**
 * 상품 기본 정보
 * 코드, 명칭, 가격, 설명, 할인율, 기본이미지
 */
data class ProductItem(
    var productId : String = "",
    var productName : String = "",
    var productPrice : Int = 0 ,
    var productDescription : String = "",
    var discountRate : Double = 0.0,
    var productImage : String = "",
)
