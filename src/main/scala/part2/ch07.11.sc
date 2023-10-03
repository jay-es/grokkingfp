object model {
  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }

  // 7.11
  opaque type Genre = String
  object Genre {
    def apply(value: String): Genre = value
    extension (a: Genre) def name: String = a
  }

  opaque type YearsActiveStart = Int
  object YearsActiveStart {
    def apply(value: Int): YearsActiveStart = value
    extension (a: YearsActiveStart) def value: Int = a
  }

  opaque type YearsActiveEnd = Int
  object YearsActiveEnd {
    def apply(value: Int): YearsActiveEnd = value
    extension (a: YearsActiveEnd) def value: Int = a
  }
}

import model._

case class Artist(
    name: String,
    genre: Genre,
    origin: Location,
    yearsActiveStart: YearsActiveStart,
    isActive: Boolean,
    yearsActiveEnd: YearsActiveEnd
)
