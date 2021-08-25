package com.example.goodtogoappv11.network



data class StoresListResponse(
    val contract_code_explanation: ContractCodeExplanation,
    val globalAmount: Int,
    val shop_data: List<ShopData>,
    val title: String
)

data class ContractCodeExplanation(
    val `0`: String,
    val `1`: String,
    val `2`: String
)

data class ShopData(
    val address: String,
    val category: Int,
    val contract: Contract,
    val id: Int,
    val img_info: ImgInfo,
    val location: Location,
    val name: String,
    val opening_hours: List<OpeningHour>,
    val testing: Boolean,
    val type: List<String>
)

data class Contract(
    val borrowable: Boolean,
    val returnable: Boolean,
    val status_code: Int
)