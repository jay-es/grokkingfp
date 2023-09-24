// 1
// for {
//   x <- List(1, 2, 3)
//   y <- Set(1)
// } yield x * y
// List(???)

val ans1 = for {
  x <- List(1, 2, 3)
  y <- Set(1)
} yield x * y

assert(ans1 == List(1, 2, 3))

// 2
// for {
//   x <- ???
//   y <- List(1)
// } yield x * y
// Set(1, 2, 3)

val ans2 = for {
  x <- Set(1, 2, 3)
  y <- List(1)
} yield x * y

assert(ans2 == Set(1, 2, 3))

// 3
// for {
//   x <- ???(1, 2, 3)
//   y <- List(1)
//   z <- Set(???)
// } yield x * y * z
// List(0, 0, 0)

val ans3 = for {
  x <- List(1, 2, 3)
  y <- List(1)
  z <- Set(0)
} yield x * y * z

assert(ans3 == List(0, 0, 0))
