// 直積型（product type）
case class ProgrammingLanguage(name: String, year: Int)

val javalang = ProgrammingLanguage("Java", 1995)
val scalalang = ProgrammingLanguage("Scala", 2004)

val languages = List(javalang, scalalang)

// map や filter
val names = languages.map(lang => lang.name)
val filtered = languages.filter(lang => lang.year > 2000)

// アンダースコア構文（省略記法）
val names2 = languages.map(_.name)
val filtered2 = languages.filter(_.year > 2000)
