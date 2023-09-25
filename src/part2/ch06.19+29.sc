// 6.19

def extractName(rawShow: String): Option[String] = {
  val bracketOpen = rawShow.indexOf('(')

  if (bracketOpen > 0)
    Some(rawShow.substring(0, bracketOpen).trim())
  else
    None

  // for {
  //   name <-
  //     if (bracketOpen > 0) Some(rawShow.substring(0, bracketOpen).trim)
  //     else None
  // } yield name
}

val ansName1 = extractName("Breaking Bad (2008-2013)")
val ansName2 = extractName("Mad Men (-2015)")
val ansName3 = extractName("(2002- N/A ) The Wire")

assert(ansName1 == Some("Breaking Bad"))
assert(ansName2 == Some("Mad Men"))
assert(ansName3 == None)

def extractYearEnd(rawShow: String): Option[Int] = {
  val bracketClose = rawShow.indexOf(')')
  val dash = rawShow.indexOf('-')

  for {
    yearStr <-
      if (bracketClose != -1 && bracketClose > dash + 1)
        Some(rawShow.substring(dash + 1, bracketClose))
      else None
    year <- yearStr.toIntOption
  } yield year
}

val ansYearEnd1 = extractYearEnd("Breaking Bad (2008-2013)")
val ansYearEnd2 = extractYearEnd("Mad Men (-2015)")
val ansYearEnd3 = extractYearEnd("(2002- N/A ) The Wire")

assert(ansYearEnd1 == Some(2013))
assert(ansYearEnd2 == Some(2015))
assert(ansYearEnd3 == None)

// 6.29
def extractYearStart(rawShow: String): Option[Int] = {
  val bracketOpen = rawShow.indexOf('(')
  val dash = rawShow.indexOf('-')
  for {
    yearStr <-
      if (bracketOpen != -1 && dash > bracketOpen + 1)
        Some(rawShow.substring(bracketOpen + 1, dash))
      else None
    year <- yearStr.toIntOption
  } yield year
}
def extractSingleYear(rawShow: String): Option[Int] = {
  val dash = rawShow.indexOf('-')
  val bracketOpen = rawShow.indexOf('(')
  val bracketClose = rawShow.indexOf(')')
  for {
    yearStr <-
      if (dash == -1 && bracketOpen != -1 && bracketClose > bracketOpen + 1)
        Some(rawShow.substring(bracketOpen + 1, bracketClose))
      else None
    year <- yearStr.toIntOption
  } yield year
}

val list = List("A (1992-)", "B (2002)", "C (-2012)", "(2022)", "E (-)")

// 1
def fn1(rawShow: String): Option[Int] =
  extractSingleYear(rawShow).orElse(extractYearEnd(rawShow))

val ans0629_1 = list.map(fn1)
assert(ans0629_1 == List(None, Some(2002), Some(2012), Some(2022), None))

// 2
def fn2(rawShow: String): Option[Int] =
  extractYearStart(rawShow)
    .orElse(extractYearEnd(rawShow))
    .orElse(extractSingleYear(rawShow))

val ans0629_2 = list.map(fn2);
assert(
  ans0629_2 == List(Some(1992), Some(2002), Some(2012), Some(2022), None)
)

// 3
def fn3(rawShow: String): Option[Int] =
  extractName(rawShow).flatMap(_ => extractSingleYear(rawShow))

val ans0629_3 = list.map(fn3)
assert(ans0629_3 == List(None, Some(2002), None, None, None))

// 4
def fn4(rawShow: String): Option[Int] =
  for {
    name <- extractName(rawShow)
    year <- extractYearStart(rawShow)
      .orElse(extractYearEnd(rawShow))
      .orElse(extractSingleYear(rawShow))
  } yield year

val ans0629_4 = list.map(fn4)
assert(ans0629_4 == List(Some(1992), Some(2002), Some(2012), None, None))
