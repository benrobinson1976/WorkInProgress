package mongo

import org.scalatest.{ShouldMatchers, FlatSpec}
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.novus.salat.annotations.{Key, Persist}
import com.mongodb.casbah.commons.conversions.scala._

/**
 * Created by brobinson3 on 3/21/14.
 */

case class OldCollection(@Key("_id") id: Int, name: String, job: String) {

  @Persist val _version = 1
}

case class NewCollection(@Key("_id") id: Int, name: String, job: String, state: String) {

  @Persist val _version = 2
}


class MongoSpec extends FlatSpec with ShouldMatchers {

  def setUpMongo(): MongoCollection = {
    val obj1 = OldCollection.apply(7, "Mike", "plumber")
    val obj2 = OldCollection.apply(11, "Ben", "bullfighter")
    val mongoColl = MongoConnection()("testDB")("testDB")
    val toDb = grater[OldCollection].asDBObject(obj1)
    val toDb2 = grater[OldCollection].asDBObject(obj2)
    mongoColl.save(toDb)
    mongoColl.save(toDb2)
    mongoColl
  }

  def removeAll() {
    val mongoColl = MongoConnection()("testDB")("testDB")
    mongoColl.remove(MongoDBObject("job" -> "plumber"))
    mongoColl.remove(MongoDBObject("job" -> "bullfighter"))
  }

  def findById(id: Int, coll: MongoCollection): Option[NewCollection] = {
    coll.findOne(MongoDBObject("_id" -> id)) map (buildObject(_))
  }

  def buildObject(dbo: MongoDBObject): NewCollection = {
    dbo.getAs[Int]("_version") match {
      case Some(2) => grater[NewCollection].asObject(dbo)
      case Some(1) => buildObject_New(dbo)
      case _ => throw new IllegalStateException("illegal version")
    }
  }

  def buildObject_New(dbo: MongoDBObject): NewCollection = {
    val old = grater[OldCollection].asObject(dbo)
    val updated = NewCollection(old.id, old.name, old.job, "New York")
    updated
  }


  "A class " should
    "connect to mongo" in {

    val mongoColl = setUpMongo()

    val q1 = MongoDBObject("job" -> "plumber")

    val obj = mongoColl findOne q1

    removeAll()
  }
  it should "do something when we get a new field" in {

    val coll = setUpMongo()
    val res = findById(7, coll)
    println("res name: " + res.get.name)
    val newDboj = grater[NewCollection].asDBObject(res.get)
    coll.save(newDboj)

    val res2 = findById(7, coll)
    println("res version: " + res2.get._version)

    //removeAll()
  }


}
