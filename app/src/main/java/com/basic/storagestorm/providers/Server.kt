package com.basic.storagestorm.providers

class Server {

    companion object {
        fun getObjectJSON(id: String): String {
            return """
                {
                        "name" : "Spiderman",
                        "age" : 22,
                        "occupation" : "hero",
                        "enemies" : ["venom", "other spiders"]
                    }
            """.trimIndent()
        }
    }
}