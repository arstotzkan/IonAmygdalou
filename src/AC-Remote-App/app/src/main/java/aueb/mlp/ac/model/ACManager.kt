package aueb.mlp.ac.model

import java.lang.IllegalArgumentException

interface ACManager {

    fun createNewAc(acName: String)

    fun getAcByName(acName: String): AirConditioner

    fun getAllAcNames(): List<String>

    fun deleteAcByName(acName: String)
}

class ACManagerImpl: ACManager {

    private val acMap = mutableMapOf<String, AirConditioner>()

    override fun createNewAc(acName: String) {
        if (acMap.containsKey(acName)) {
            throw IllegalArgumentException("AC with name $acName already exists")
        }

        acMap[acName] = LoggingAirConditioner(acName)
    }

    override fun getAcByName(acName: String): AirConditioner =
        acMap[acName] ?: throw NoSuchElementException("No AC with name $acName")

    override fun getAllAcNames(): List<String> = acMap.keys.toList()

    override fun deleteAcByName(acName: String) {
        if (!acMap.containsKey(acName)) {
            throw IllegalArgumentException("AC with name $acName does not exist")
        }

        acMap.remove(acName)
    }
}
