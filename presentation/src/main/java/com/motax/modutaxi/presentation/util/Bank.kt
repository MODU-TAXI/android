package com.motax.modutaxi.presentation.util

import com.motax.modutaxi.presentation.R

enum class Bank(val displayName: String, val logoResId: Int) {
    NH("NH농협", R.drawable.ic_bank_nh),
    KAKAO("카카오뱅크", R.drawable.ic_bank_kakao),
    KB("KB국민", R.drawable.ic_bank_kb),
    TOSS("토스뱅크", R.drawable.ic_bank_toss),
    SHINHAN("신한", R.drawable.ic_bank_shinhan),
    WOORI("우리", R.drawable.ic_bank_woori),
    IBK("IBK기업", R.drawable.ic_bank_ibk),
    HANA("하나", R.drawable.ic_bank_hana),
    MG("새마을", R.drawable.ic_bank_mg),
    BUSAN("부산", R.drawable.ic_bank_busan),
    DAEGU("대구", R.drawable.ic_bank_daegu),
    K("케이뱅크", R.drawable.ic_bank_k),
    SHINHYUP("신협", R.drawable.ic_bank_shinhyup),
    POST("우체국", R.drawable.ic_bank_post),
    SC("SC제일", R.drawable.ic_bank_sc),
    BNK("경남", R.drawable.ic_bank_bnk),
    GWANGJU("광주", R.drawable.ic_bank_gwangju),
    SUHYUP("수협", R.drawable.ic_bank_suhyup),
    JEONBUK("전북", R.drawable.ic_bank_jeonbuk),
    SB("저축은행", R.drawable.ic_bank_sb),
    JEJU("제주", R.drawable.ic_bank_jeju);

    companion object {
        // displayname을 넣으면 enum값 반환
        fun fromDisplayName(displayName: String): Bank {
            return entries.first { it.displayName.contains(displayName) }
        }
        //displayname에 해당하는 ic 반환
        fun getLogoResource(displayName: String): Int {
            return fromDisplayName(displayName).logoResId
        }
        // 주어진 enum값과 일치하는 back enum값 반환
        fun fromName(name: String): Bank {
            return entries.first{ it.name == name }
        }
        // enum에 따른 displayname 반환
        fun getDisplayName(name: String): String {
            return fromName(name).displayName
        }
    }
}