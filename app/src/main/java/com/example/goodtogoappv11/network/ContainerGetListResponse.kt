package com.example.goodtogoappv11.network

import ContainerDict
import ContainerType
import java.io.Serializable

data class ContainerGetListResponse(
    val containerDict: ContainerDict,
    val containerType: List<ContainerType>
): Serializable