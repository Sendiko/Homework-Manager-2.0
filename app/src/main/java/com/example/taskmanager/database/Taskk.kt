package com.example.taskmanager.database


// TODO : MODEL FOR THE DATABASE
data class Taskk(var id : String, val task : String, val subject : String){
    constructor() : this("", "", "")
}
