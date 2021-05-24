package com.raspisanie.mai.models.server

data class CanteenResponse (
    val id: String,
    val name: String,
    val workTime: String,
    val address: String
)

/*
{
  "id": "1",
  "name": "Привет",
  "workTime": "Пн-Сб 09:00-21:00",
  "address": "Университетская 2",
  "createdAt": "2021-05-07 01:49:12.0"
},
 */