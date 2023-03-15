
interface KeyValueStore<K, V> {
    fun put(key: K, value: V)
    operator fun get(key: K): V?
    fun remove(key: K): V?
    fun count(value: V): Int
    fun copy(): KeyValueStore<K, V>
}

class DataStore<K, V>(private val map: MutableMap<K, V> = mutableMapOf()) : KeyValueStore<K, V> {
    override fun put(key: K, value: V) {
        map[key] = value
    }

    override fun get(key: K): V? = map[key]

    override fun remove(key: K) = map.remove(key)

    override fun count(value: V) = map.count { it.value == value }

    override fun copy(): KeyValueStore<K, V> = DataStore(map.toMutableMap())
}