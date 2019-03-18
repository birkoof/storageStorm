package com.basic.storagestorm

class Helper {
    companion object {
        fun getFileByID(ID: String) : String {
            return when (ID) {
                Constants.HOME -> """
                    {
                        "type" : "Home",
                        "id": "aFcKFRZBY37gl01qO",
                        "name": "Home",
                        "collections": ["UgJRB7YGNxwFxde28", "jc65gPRQeCQVDXTn8", "6udLnMy74ZYN6AUMG", "gRb2AFHHO6tsyD8o0"]
                    }
                """.trimIndent()
                "UgJRB7YGNxwFxde28" -> """
                    {
                        "type" : "Collection",
                        "id" : "UgJRB7YGNxwFxde28",
                        "name" : "Customers",
                        "documents" : [
                            "EDXqUVkOgLyGjszq4",
                            "iqfTdajhpksJdqqNJ",
                            "Z8PzDjAbkVgVcxIAW",
                            "IAMZnurMgQAogSMhG",
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "wlu7ksAj8aKH3wKCz",
                            "0FpFtQpxsinfahxBu",
                            "wiMjzGMhTXUXw523o",
                            "UvrpWkXNKvNUBN3RA",
                            "rDsZqpdOnhB4wvX8a"
                        ]
                    }
                """.trimIndent()
                "jc65gPRQeCQVDXTn8" -> """
                    {
                        "type" : "Collection",
                        "id" : "jc65gPRQeCQVDXTn8",
                        "name" : "Cities",
                        "documents" : [
                            "EDXqUVkOgLyGjszq4",
                            "iqfTdajhpksJdqqNJ",
                            "Z8PzDjAbkVgVcxIAW",
                            "IAMZnurMgQAogSMhG",
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "wlu7ksAj8aKH3wKCz",
                            "0FpFtQpxsinfahxBu",
                            "wiMjzGMhTXUXw523o",
                            "UvrpWkXNKvNUBN3RA",
                            "rDsZqpdOnhB4wvX8a"
                        ]
                    }
                """.trimIndent()
                "6udLnMy74ZYN6AUMG" -> """
                    {
                        "type" : "Collection",
                        "id" : "6udLnMy74ZYN6AUMG",
                        "name" : "Destinations",
                        "documents" : [
                            "EDXqUVkOgLyGjszq4",
                            "iqfTdajhpksJdqqNJ",
                            "Z8PzDjAbkVgVcxIAW",
                            "IAMZnurMgQAogSMhG",
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "wlu7ksAj8aKH3wKCz",
                            "0FpFtQpxsinfahxBu",
                            "wiMjzGMhTXUXw523o",
                            "UvrpWkXNKvNUBN3RA",
                            "rDsZqpdOnhB4wvX8a"
                        ]
                    }
                """.trimIndent()
                "gRb2AFHHO6tsyD8o0" -> """
                    {
                        "type" : "Collection",
                        "id" : "gRb2AFHHO6tsyD8o0",
                        "name" : "Something",
                        "documents" : [
                            "EDXqUVkOgLyGjszq4",
                            "iqfTdajhpksJdqqNJ",
                            "Z8PzDjAbkVgVcxIAW",
                            "IAMZnurMgQAogSMhG",
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "wlu7ksAj8aKH3wKCz",
                            "0FpFtQpxsinfahxBu",
                            "wiMjzGMhTXUXw523o",
                            "UvrpWkXNKvNUBN3RA",
                            "rDsZqpdOnhB4wvX8a"
                        ]
                    }
                """.trimIndent()
                "EDXqUVkOgLyGjszq4" -> """
                    {
                        "type" : "Document",
                        "id" : "EDXqUVkOgLyGjszq4",
                        "name" : "Batman",
                        "age" : 42,
                        "occupation" : "vigilante",
                        "enemies" : ["joker", "superman", "everyone"],
                        "collections" : ["BMvuKJ05IHBuQVQ7w", "lNn7LjfvAKfcgDH89"]
                    }
                """.trimIndent()
                "iqfTdajhpksJdqqNJ" -> """
                    {
                        "type" : "Document",
                        "id" : "iqfTdajhpksJdqqNJ",
                        "name" : "Superman",
                        "age" : 36,
                        "occupation" : "hero",
                        "enemies" : ["batman", "kriptonite"]
                    }
                """.trimIndent()
                "Z8PzDjAbkVgVcxIAW" -> """
                    {
                        "type" : "Document",
                        "id" : "Z8PzDjAbkVgVcxIAW",
                        "name" : "Spiderman",
                        "age" : 22,
                        "occupation" : "hero",
                        "enemies" : ["venom", "other spiders"],
                        "collections" : ["BMvuKJ05IHBuQVQ7w", "lNn7LjfvAKfcgDH89"]
                    }
                """.trimIndent()
                "IAMZnurMgQAogSMhG" -> """
                    {
                        "type" : "Document",
                        "id" : "IAMZnurMgQAogSMhG",
                        "name" : "Stickman",
                        "age" : 0,
                        "occupation" : "character",
                        "enemies" : ["pencils", "erasers", "humans"]
                    }
                """.trimIndent()
                "LXuqzJa3IgnZS4nTg" -> """
                    {
                        "type" : "Document"
                        "id" : "LXuqzJa3IgnZS4nTg",
                        "name" : "Angry man",
                        "age" : 32,
                        "occupation" : "Human",
                        "enemies" : ["humans"]
                    }
                """.trimIndent()
                "2tPzXSa6Un8Xamgww" -> """
                    {
                        "type" : "Document",
                        "id" : "2tPzXSa6Un8Xamgww",
                        "name" : "Fat man",
                        "age" : 32,
                        "occupation" : "Human",
                        "enemies" : ["humans"]
                    }
                """.trimIndent()
                "wlu7ksAj8aKH3wKCz" -> """
                    {
                        "type" : "Document",
                        "id" : "wlu7ksAj8aKH3wKCz",
                        "name" : "Old man",
                        "age" : 123,
                        "occupation" : "Human",
                        "enemies" : ["humans"],
                        "collections" : ["BMvuKJ05IHBuQVQ7w", "lNn7LjfvAKfcgDH89"]
                    }
                """.trimIndent()
                "0FpFtQpxsinfahxBu" -> """
                    {
                        "type" : "Document",
                        "id" : "0FpFtQpxsinfahxBu",
                        "name" : "Young man",
                        "age" : 18,
                        "occupation" : "Human",
                        "enemies" : ["humans"],
                        "collections" : ["BMvuKJ05IHBuQVQ7w", "lNn7LjfvAKfcgDH89"]
                    }
                """.trimIndent()
                "wiMjzGMhTXUXw523o" -> """
                    {
                        "type" : "Document",
                        "id" : "wiMjzGMhTXUXw523o",
                        "name" : "Tooth man",
                        "age" : 53,
                        "occupation" : "Human",
                        "enemies" : ["humans"],
                        "collections" : ["BMvuKJ05IHBuQVQ7w", "lNn7LjfvAKfcgDH89"]
                    }
                """.trimIndent()
                "UvrpWkXNKvNUBN3RA" -> """
                    {
                        "type" : "Document",
                        "id" : "UvrpWkXNKvNUBN3RA",
                        "name" : "Iron man",
                        "age" : 40,
                        "occupation" : "vigilante",
                        "enemies" : ["guns", "everyone"]
                    }
                """.trimIndent()
                "rDsZqpdOnhB4wvX8a" -> """
                    {
                        "type" : "Document",
                        "id" : "rDsZqpdOnhB4wvX8a",
                        "age" : 0,
                        "occupation" : "none",
                        "enemies" : ["no", "one"],
                        "collections" : ["BMvuKJ05IHBuQVQ7w"]
                    }
                """.trimIndent()
                "BMvuKJ05IHBuQVQ7w" -> """
                    {
                        "type" : "Collection",
                        "id" : "BMvuKJ05IHBuQVQ7w",
                        "name" : "Cool Collection",
                        "documents" : [
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "UvrpWkXNKvNUBN3RA"
                        ]
                    }
                """.trimIndent()
                "lNn7LjfvAKfcgDH89" -> """
                    {
                        "type" : "Collection",
                        "id" : "lNn7LjfvAKfcgDH89",
                        "name" : "Penguin Collection",
                        "documents" : [
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "UvrpWkXNKvNUBN3RA"
                        ]
                    }
                """.trimIndent()
                else -> """
                    {
                        "type" : "Collection",
                        "id" : "UgJRB7YGNxwFxde28",
                        "name" : "Customers",
                        "documents" : [
                            "EDXqUVkOgLyGjszq4",
                            "iqfTdajhpksJdqqNJ",
                            "Z8PzDjAbkVgVcxIAW",
                            "IAMZnurMgQAogSMhG",
                            "LXuqzJa3IgnZS4nTg",
                            "2tPzXSa6Un8Xamgww",
                            "wlu7ksAj8aKH3wKCz",
                            "0FpFtQpxsinfahxBu",
                            "wiMjzGMhTXUXw523o",
                            "UvrpWkXNKvNUBN3RA",
                            "rDsZqpdOnhB4wvX8a"
                        ]
                    }
                """.trimIndent()
            }
        }

        fun getRandomID(): String {
            return java.util.UUID.randomUUID().toString()
        }
    }
}