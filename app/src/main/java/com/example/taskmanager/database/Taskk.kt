package com.example.taskmanager.database


data class Taskk(var id : String, val task : String, val subject : String){
    constructor() : this("", "", "")
}
