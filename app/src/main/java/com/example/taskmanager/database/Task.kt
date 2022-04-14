package com.example.taskmanager.database


data class Task(val task : String, val subject : String){
    constructor() : this("", "")
}
