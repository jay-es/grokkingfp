case class Event(name: String, start: Int, end: Int)

def validateName(name: String): Option[String] =
  if (name.size > 0) Some(name) else None

def validateEnd(end: Int): Option[Int] =
  if (end < 3000) Some(end) else None

def validateStart(start: Int, end: Int): Option[Int] =
  if (start <= end) Some(start) else None

def parse(name: String, start: Int, end: Int): Option[Event] = for {
  validName <- validateName(name)
  validEnd <- validateEnd(end)
  validStart <- validateStart(start, end)
} yield Event(validName, validStart, validEnd)

// 1
def validateLength(start: Int, end: Int, minLength: Int): Option[Int] = {
  val length = end - start
  if (length >= minLength) Some(length) else None
}

assert(validateLength(2000, 2020, 10) == Some(20))
assert(validateLength(2000, 2020, 21) == None)

// 2
def parseLongEvent(
    name: String,
    start: Int,
    end: Int,
    minLength: Int
): Option[Event] = for {
  validName <- validateName(name)
  validEnd <- validateEnd(end)
  validStart <- validateStart(start, end)
  validLength <- validateLength(start, end, minLength)
} yield Event(validName, validStart, validEnd)

assert(
  parseLongEvent("Apollo Program", 1961, 1972, 10) == Some(
    Event("Apollo Program", 1961, 1972)
  )
)
assert(parseLongEvent("World War II", 1939, 1945, 10) == None)
assert(parseLongEvent("", 1939, 1945, 10) == None)
assert(parseLongEvent("Apollo Program", 1972, 1961, 10) == None)
