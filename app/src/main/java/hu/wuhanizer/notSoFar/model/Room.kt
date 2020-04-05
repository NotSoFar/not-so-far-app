package hu.wuhanizer.notSoFar.model

import java.util.*

enum class Rating {
    POSITIVE,NEUTRAL,NEGATIVE
}

data class Room(var id: String="",var startUserId:String="",var secondUserId:String="", var date:Date=Date(),var startWord:String="",var user1Rating:Rating?=null,var user2Rating:Rating?=null) {
}