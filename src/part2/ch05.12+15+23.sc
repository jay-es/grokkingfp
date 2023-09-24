case class Point(x: Int, y: Int)

// List(???).flatMap(x => List(???).map(y => Point(x, y)))

val ans = List(1).flatMap(x => List(-2, 7).map(y => Point(x, y)))

assert(ans == List(Point(1, -2), Point(1, 7)))

// 5.15
val xs = List(1)
val ys = List(-2, 7)

// for{
//   ???
// } yield ???

val ans1 = for {
  x <- xs
  y <- ys
} yield Point(x, y)

assert(ans1 == ans)

// 2
case class Point3d(x: Int, y: Int, z: Int)
val zs = List(3, 4)

val ans2 = for {
  x <- xs
  y <- ys
  z <- zs
} yield Point3d(x, y, z)

assert(
  ans2 == List(
    Point3d(1, -2, 3),
    Point3d(1, -2, 4),
    Point3d(1, 7, 3),
    Point3d(1, 7, 4)
  )
)

// 3
val ans3 = xs.flatMap(x => ys.flatMap(y => zs.map(z => Point3d(x, y, z))))
assert(ans3 == ans2)

// 5.23
val points = List(Point(5, 2), Point(1, 1))
val riskyRadiuses = List(-10, 0, 2)
def isInside(point: Point, radius: Int): Boolean = {
  radius * radius >= point.x * point.x + point.y * point.y
}

for {
  r <- riskyRadiuses
  point <- points.filter(p => isInside(p, r))
} yield s"$point is within a radius of $r"

// 1. filter
val ans23_1 = for {
  r <- riskyRadiuses.filter(_ > 0)
  point <- points.filter(p => isInside(p, r))
} yield s"$point is within a radius of $r"

assert(ans23_1 == List("Point(1,1) is within a radius of 2"))

// 2. ガード式
val ans23_2 = for {
  r <- riskyRadiuses
  if r > 0
  point <- points.filter(p => isInside(p, r))
} yield s"$point is within a radius of $r"

assert(ans23_2 == List("Point(1,1) is within a radius of 2"))

// 3. flatMap
def validateRadius(radius: Int): List[Int] =
  if (radius > 0) List(radius) else List.empty
def insideFilter(point: Point, radius: Int): List[Point] =
  if (isInside(point, radius)) List(point) else List.empty

val ans23_3 = for {
  r <- riskyRadiuses
  validRadius <- validateRadius(r)
  point <- points
  inPoint <- insideFilter(point, r)
} yield s"$point is within a radius of $r"

assert(ans23_3 == List("Point(1,1) is within a radius of 2"))
