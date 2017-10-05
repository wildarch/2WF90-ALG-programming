package group14

fun <A> cartesianProduct(s: List<Set<A>>): Set<List<A>> {
    if (s.isEmpty()) {
        return HashSet()
    }
    else if (s.size == 1) {
        val set = s.get(0)
        var res = HashSet<List<A>>()
        for (item in set) {
            var list = ArrayList<A>()
            list.add(item)
            res.add(list)
        }
        return res
    }
    else {
        var partialCart = cartesianProduct(s.subList(1, s.size))
        var res = HashSet<List<A>>()
        for (set in partialCart) {
            for (item in s.get(0)) {
                var list = ArrayList<A>()
                list.add(item)
                list.addAll(set)
                res.add(list)
            }
        }
        return res
    }
}