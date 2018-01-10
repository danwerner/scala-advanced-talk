//
// Scala Advanced Features
//

import play.api.mvc.{Action, Request, Results}

import scala.concurrent.{Await, ExecutionContext, Future}

object ImplicitParametersSolution extends Results {

  // 1. Implicit parameters

  // Request
  def formatValue(v: String)(implicit request: Request[_]): String = {
    request.headers.get("Accepts") match {
      case Some("text/html") => "<b>" + v + "</b>"
      case _ => v
    }
  }

  def index = Action { implicit request =>
    Ok(formatValue("foo"))
  }

  // Future
  import scala.concurrent.ExecutionContext.Implicits.global

  // implicitly
  Future { 1 + 2 }.map(_ * 2)

  // explicitly
  Future { 1 + 2 }.map(_ * 2)(global)

  implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutor(null)

  implicit def makeExecutionContext: ExecutionContext = ExecutionContext.fromExecutor(null)
}


object ImplicitConversionsSolution {

  // 2. Implicit conversions

  // java.time.Duration to FiniteDuration
  import scala.concurrent.duration.{Duration => ScalaDuration, _}
  import scala.language.postfixOps

  val f = Future { 1 + 2 }

  // Uses Scala Duration
  Await.result(f, 5 seconds)

  val timeout = java.time.Duration.ofSeconds(5)

  // Uses java.time.Duration, automatically
  Await.result(f, timeout)

  implicit def durationToScalaDuration(d: java.time.Duration): ScalaDuration = d.getSeconds.seconds
}


object ImplicitClassesSolution {

  // 3. Implicit classes

  implicit class FooString(self: String) {
    def foo(n: Int) = self + " " + n
  }

  "bar".foo(433)

  // DurationInt, DurationLong
  import scala.concurrent.duration.{DurationInt, DurationLong}

  5.seconds

  1 minute

  // Use case: Extend Future with new combinator methods

  // Syntax: ArrowAssoc to Tuple2
  Map("foo" -> 1, "bar" -> 2)
}


object ManifestsTypeTagsSolution {

  // 4. Manifests & TypeTags

  // This doesn't work
  def whatsTheList[T](xs: List[T]) = {
    if (xs.isInstanceOf[List[String]])
      println("string list")
    else
      println("other list")
  }

  // Manifests are deprecated

  // Type Tags, typeOf[T]
  import scala.reflect.runtime.universe._

  def whatsTheList[T: TypeTag](xs: List[T]) = {
    if (typeOf[T] <:< typeOf[String])
      println("string list")
    else
      println("other list")
  }
}


object ScalazSolution {

  // 5. Scalaz Features

  // Import Fun!!!
  import scalaz.std.map._
  import scalaz.std.list._
  import scalaz.syntax.semigroup._
  import scalaz.syntax.monoid._

  // Monoids
  0 + 1
  Nil ++ List(1, 2)
  Map.empty ++ Map("x" -> 1)

  Map("foo" -> List(1, 2)) |+| Map("foo" -> List(3), "bar" -> List(4))

  // Another use case: Combine the results of Futures with |+|

  // Tagged Types (@@)
  import scalaz.{@@, Tag}

  object Tags {
    trait UserId
  }

  type UserId = String @@ Tags.UserId

  def UserId(s: String): UserId = Tag[String, Tags.UserId](s)

  def showUser(userId: UserId) = ???

  val uid = UserId("JFK")

  showUser(uid)

  // Won't work -- String is not compile-time compatible with UserId!
  //showUser("JKF")

  // Won't work -- can't mess with tagged Strings!
  //uid.toLowerCase

  Tag.unwrap(uid).toLowerCase
}
