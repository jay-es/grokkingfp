import cats.effect.IO
import cats.effect.unsafe.implicits.global

// 1
import ch08_SchedulingMeetings.calendarEntriesApiCall
def calendarEntries(name: String): IO[List[MeetingTime]] =
  IO.delay(calendarEntriesApiCall(name))

val res1 = calendarEntries("Alice")
// val res1b = res1.unsafeRunSync()

// 2
import ch08_SchedulingMeetings.createMeetingApiCall
def createMeeting(names: List[String], meetingTime: MeetingTime): IO[Unit] =
  IO.delay(createMeetingApiCall(names, meetingTime))

val res2 = createMeeting(List("Alice", "Bob"), MeetingTime(12, 14))
val res2b = res2.unsafeRunAndForget()

// 3
def scheduledMeetings(
    person1: String,
    person2: String
): IO[List[MeetingTime]] = {
  for {
    meetingTime1 <- calendarEntries(person1)
    meetingTime2 <- calendarEntries(person2)
  } yield meetingTime1.appendedAll(meetingTime2)
}

val res3 = scheduledMeetings("Alice", "Bob")
// val res3b = res3.unsafeRunSync()

// 8.18

// 1
def possibleMeetings(
    existingMeetings: List[MeetingTime],
    startHour: Int,
    endHour: Int,
    lengthHours: Int
): List[MeetingTime] =
  List
    .range(startHour, endHour - lengthHours + 1)
    .map(t => MeetingTime(t, t + lengthHours))
    .filter(mt =>
      existingMeetings.forall(em =>
        em.startHour >= mt.endHour || em.endHour <= mt.startHour
      )
    )

val res18_1a =
  possibleMeetings(List(MeetingTime(8, 10), MeetingTime(14, 16)), 9, 15, 3)
assert(res18_1a == List(MeetingTime(10, 13), MeetingTime(11, 14)))
val res18_1b =
  possibleMeetings(
    List(MeetingTime(8, 10), MeetingTime(11, 12), MeetingTime(9, 10)),
    8,
    16,
    3
  )
assert(res18_1b == List(MeetingTime(12, 15), MeetingTime(13, 16)))

// 2
def schedule(
    person1: String,
    person2: String,
    lengthHours: Int
): IO[Option[MeetingTime]] = {
  // scheduledMeetings(person1, person2).map(
  //   possibleMeetings(_, 8, 16, lengthHours).headOption
  // )
  for {
    existingMeetings <- scheduledMeetings(person1, person2)
    meetings = possibleMeetings(existingMeetings, 8, 16, lengthHours)
  } yield meetings.headOption
}

val res18_2 = schedule("Alice", "Bob", 3)
// assert(res18_2.unsafeRunSync() == Some(MeetingTime(12, 15)))

// 8.25
val res25a = calendarEntries("Alice").orElse(calendarEntries("Alice"))
val res25b = calendarEntries("Alice")
  .orElse(calendarEntries("Alice"))
  .orElse(calendarEntries("Alice"))
