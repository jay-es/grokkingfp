case class TvShow(title: String, start: Int, end: Int)

def addOrResign(
    parsedShows: Option[List[TvShow]],
    newParsedShow: Option[TvShow]
): Option[List[TvShow]] = {
  for {
    shows <- parsedShows
    newShow <- newParsedShow
  } yield shows.appended(newShow)
}

// 1.1
val res_a = addOrResign(Some(List.empty), Some(TvShow("Chernobyl", 2019, 2019)))
val ans_a = Some(List(TvShow("Chernobyl", 2019, 2019)))

// 1.2
val res_b = addOrResign(
  Some(List(TvShow("Chernobyl", 2019, 2019))),
  Some(TvShow("The Wire", 2002, 2008))
)
val ans_b = Some(
  List(
    TvShow("Chernobyl", 2019, 2019),
    TvShow("The Wire", 2002, 2008)
  )
)

// 1.3
val res_c = addOrResign(Some(List(TvShow("Chernobyl", 2019, 2019))), None)
val ans_c = None

// 1.4
val res_d = addOrResign(None, Some(TvShow("Chernobyl", 2019, 2019)))
val ans_d = None

// 1.5
val res_e = addOrResign(None, None)
val ans_e = None

assert(res_a == ans_a)
assert(res_b == ans_b)
assert(res_c == ans_c)
assert(res_d == ans_d)
assert(res_e == ans_e)
