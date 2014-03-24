package mongo

import org.scalatest.{ShouldMatchers, FlatSpec}
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.commons.conversions.scala._

/**
 * Created by brobinson3 on 3/21/14.
 */

case class OldCollection(job: String, name: String, testId: Int, version: Int)

case class NewCollection(job: String, name: String, testId: Int, version: Int, state: String)

class MongoSpec extends FlatSpec with ShouldMatchers {

  def setUpMongo() {
    val obj1 = OldCollection.apply("plumber", "Mike", 22, 1)
    val obj2 = OldCollection.apply("bullfighter", "Ben", 23, 1)
    //val li = util.ArrayList(obj1, obj2)
    val mongoConn = MongoConnection()("testDB")("testDB")
   val toDb = grater[OldCollection].asDBObject(obj1)
    val toDb2 = grater[OldCollection].asDBObject(obj2)
    mongoConn.save(toDb)
    mongoConn.save(toDb2)

  }

  def removeAll(){
    val mongoConn = MongoConnection()("testDB")("testDB")
    //mongoConn.findAndRemove(MongoDBObject("job" -> "plumber"))
    mongoConn.remove(MongoDBObject("job" -> "plumber"))
    mongoConn.remove(MongoDBObject("job" -> "bullfighter"))
  }

  "A class " should
    "connect to mongo" in {
    setUpMongo()
    val mongoConn = MongoConnection()("testDB")("testDB")

    val q1 = MongoDBObject("job" -> "plumber")

    val obj = mongoConn findOne q1

    val ccObj = grater[OldCollection].asObject(obj.get)
    assert(ccObj.name equals "Mike")

    removeAll()
  }
    it should "do something" in {

    }


}
