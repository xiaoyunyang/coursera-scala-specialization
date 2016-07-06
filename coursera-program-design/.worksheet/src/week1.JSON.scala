package week1

object json {
  abstract class JSON
  case class JSeq   (elems: List[JSON])           extends JSON
  case class JObj  (bindings: Map[String, JSON])  extends JSON
  case class JNum  (num: Double)                  extends JSON
  case class JStr  (str: String)                  extends JSON
  case class JBool (b: Boolean)                   extends JSON
  case object JNull                               extends JSON;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(902); 
  
	val jobj = JObj(Map(
	  "firstName" -> JStr("John"),
	  "lastName" -> JStr("Smith"),
	  "address" -> JObj(Map(
	    "streetAddress" -> JStr("21 2nd Street"),
      "state" -> JStr("NY"),
      "postalCode" -> JNum(10021)
    )),
    "phoneNumbers" -> JSeq(List(
      JObj(Map(
        "type" -> JStr("home"),
        "number" -> JStr("212 555-1234")
      )),
      JObj(Map(
        "type" -> JStr("fax"),
        "number" -> JStr("646 555-4567")
      ))
    ))
  ));System.out.println("""jobj  : week1.json.JObj = """ + $show(jobj ));$skip(436); 

  def show(json: JSON): String = json match {
    case JSeq(elems) =>
      "[" + { elems map show mkString ", " } + "]"
    case JObj(bindings) =>
      val assocs = bindings map {
        case (key, value) => "\"" + key + "\": " + show(value)
      }
      "{" + (assocs mkString ", ") + "}"
    case JNum(num) => num.toString
    case JStr(str) => '\"' + str + '\"'
    case JBool(b)  => b.toString
    case JNull     => "null"
  };System.out.println("""show: (json: week1.json.JSON)String""");$skip(14); val res$0 = 

  show(jobj);System.out.println("""res0: String = """ + $show(res$0));$skip(42); 
  val data: List[JSON] = List(jobj, jobj);System.out.println("""data  : List[week1.json.JSON] = """ + $show(data ));$skip(321); val res$1 = 
 
  
  //This gives the first and last names of people with a phone number starting with "212"
  for {
    JObj(bindings) <- data
	  JSeq(phones) = bindings("phoneNumbers")
    JObj(phone) <- phones
    JStr(digits) = phone("number")
    if digits startsWith "212"
  } yield (bindings("firstName"), bindings("lastName"));System.out.println("""res1: List[(week1.json.JSON, week1.json.JSON)] = """ + $show(res$1))}
}
