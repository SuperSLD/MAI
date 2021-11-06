package pro.midev.juttermap.server

class JTApiService(private var api: JTApi) {
    suspend fun getMapList() = api.getMapList()
    suspend fun getMap(id: String) = api.getMap(id)
}