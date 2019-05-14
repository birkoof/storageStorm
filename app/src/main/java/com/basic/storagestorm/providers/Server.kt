package com.basic.storagestorm.providers

class Server {

    companion object {
        fun getObjectJSON(id: String): String {
            return """
                [
                    {
                        "_id": "5cda0d2e15c8e4ec18d20686",
                        "index": 0,
                        "guid": "e18a8264-d9cf-4aa1-a5e0-60f76ed7f70a",
                        "isActive": false,
                        "balance": "${'$'}3,102.99",
                        "picture": "http://placehold.it/32x32",
                        "age": 31,
                        "eyeColor": "brown",
                        "name": "Anastasia Gilbert",
                        "gender": "female",
                        "company": "CEPRENE",
                        "email": "anastasiagilbert@ceprene.com",
                        "phone": "+1 (992) 460-3468",
                        "address": "527 Poplar Avenue, Trucksville, Kansas, 5137",
                        "about": "Ipsum do tempor minim irure elit quis sint est aliqua fugiat dolor velit occaecat nostrud. Consequat magna exercitation eu proident deserunt minim fugiat. Velit eu aliqua aliqua cupidatat ut. Ea velit sit et elit.\r\n",
                        "registered": "2015-02-08T01:58:45 -01:00",
                        "latitude": -39.786627,
                        "longitude": 178.254271,
                        "tags": [
                          "laboris",
                          "ipsum",
                          "minim",
                          "Lorem",
                          "dolore",
                          "aliqua",
                          "ea"
                        ],
                        "friends": [
                          {
                            "id": 0,
                            "name": "Sharron Oneil"
                          },
                          {
                            "id": 1,
                            "name": "Janet Hamilton"
                          },
                          {
                            "id": 2,
                            "name": "Vazquez Mcfadden"
                          }
                        ],
                        "greeting": "Hello, Anastasia Gilbert! You have 8 unread messages.",
                        "favoriteFruit": "banana"
                    }
                ]
            """.trimIndent()
        }
    }
}