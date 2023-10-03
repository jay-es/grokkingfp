import cats.effect.IO
import ch08_SchedulingMeetings.calendarEntriesApiCall
import ch08_SchedulingMeetings.createMeetingApiCall

def calendarEntries(name: String): IO[List[MeetingTime]] =
  IO.delay(calendarEntriesApiCall(name))

def createMeeting(names: List[String], meetingTime: MeetingTime): IO[Unit] =
  IO.delay(createMeetingApiCall(names, meetingTime))

def scheduledMeetings(
    person1: String,
    person2: String
): IO[List[MeetingTime]] = {
  for {
    meetingTime1 <- calendarEntries(person1)
    meetingTime2 <- calendarEntries(person2)
  } yield meetingTime1.appendedAll(meetingTime2)
}

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

// 8.33
def schedule(
    person1: String,
    person2: String,
    lengthHours: Int
): IO[Option[MeetingTime]] = {
  for {
    existingMeetings <- scheduledMeetings(person1, person2)
      .orElse(scheduledMeetings(person1, person2))
      .orElse(IO.pure(List.empty))
    meetings = possibleMeetings(existingMeetings, 8, 16, lengthHours)
    headOption = meetings.headOption
    _ <- headOption match
      case None => IO.unit
      case Some(meetingTime) =>
        createMeeting(List(person1, person2), meetingTime)
          .orElse(createMeeting(List(person1, person2), meetingTime))
          .orElse(IO.unit)
  } yield headOption
}
