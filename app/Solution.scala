import play.api.mvc.{Action, Request, Results}
import play.mvc.Http.Status

import scala.concurrent.Future

object Solution extends Results {

  // Implicit parameters

  // Request
  def formatValue(v: String)(implicit request: Request[_]): String = {
    val formatO = request.headers.get("Accepts")
    ???
  }

  def index = Action { implicit request =>
    Ok(formatValue("foo"))
  }

  // Future
  import scala.concurrent.ExecutionContext.Implicits.global

  Future { 1 + 2 }.map(_ * 2)

  // Implicit conversions

  // FiniteDuration to java.time.Duration
  // Syntax: ArrowAssoc to Tuple2

  // Implicit classes

  // DurationInt, DurationLong
  // Own example


  // Manifests & TypeTags

  // Manifests (shortly, deprecated!)
  // Type Tags, typeOf[T] -- why to use


  // Scalaz Features

  // Tagged Types (@@)
  // Something else?

}
