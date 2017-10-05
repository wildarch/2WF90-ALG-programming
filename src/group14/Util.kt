package group14

fun <A> cartesianProduct(s: List<Set<A>>): Set<List<A>> {
    if (s.isEmpty()) {
        return HashSet()
    }
    else if (s.size == 1) {
        val set = s[0]
        val res = HashSet<List<A>>()
        for (item in set) {
            val list = ArrayList<A>()
            list.add(item)
            res.add(list)
        }
        return res
    }
    else {
        val partialCart = cartesianProduct(s.subList(1, s.size))
        val res = HashSet<List<A>>()
        for (set in partialCart) {
            for (item in s[0]) {
                val list = ArrayList<A>()
                list.add(item)
                list.addAll(set)
                res.add(list)
            }
        }
        return res
    }
}