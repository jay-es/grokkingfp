// 1
val ans1 = List("scala", "rust", "ada").map(w => w.length)
assert(ans1 == List(5, 4, 3))

// 2
val ans2 = List("rust", "ada").map(w => w.length - w.replaceAll("s", "").length)
assert(ans2 == List(1, 0))

// 3
val ans3 = List(5, 1, 2, 4, 0).map(n => -n)
assert(ans3 == List(-5, -1, -2, -4, 0))

// 4
val ans4 = List(5, 1, 2, 4, 0).map(n => n * 2)
assert(ans4 == List(10, 2, 4, 8, 0))
