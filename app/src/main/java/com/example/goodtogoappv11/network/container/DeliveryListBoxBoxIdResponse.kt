package com.example.goodtogoappv11.network.container

data class DeliveryListBoxBoxIdResponse(
    val ID: Int,
    val action: List<Action>,
    val boxName: String,
    val comment: String,
    val containerHash: String,
    val containerList: List<Int>,
    val deliverContent: List<DeliverContent>,
    val dueDate: String,
    val orderContent: List<OrderContent>,
    val status: String,
    val storeID: Any,
    val user: User
)