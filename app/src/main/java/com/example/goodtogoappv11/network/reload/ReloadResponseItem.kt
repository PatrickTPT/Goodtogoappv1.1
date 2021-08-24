package com.example.goodtogoappv11.network.reload

data class ReloadResponseItem(
    val action: List<Action>,
    val containerHash: String,
    val containerList: List<Int>,
    val deliverContent: List<DeliverContent>,
    val dueDate: String,
    val orderContent: List<OrderContent>,
    val status: String,
    val storeID: Int
)