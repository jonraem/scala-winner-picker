import scala.io.Source
import scala.util.Random
import scala.io.StdIn.readInt
import scala.io.StdIn.readLine
import scala.collection.mutable.ArrayBuffer

object WinnerPicker {
  var winnerCount = 0
  var textParsed = false
  var lines = new ArrayBuffer[String]
  var winners = new ArrayBuffer[String]

  def main(args: Array[String]): Unit = {
    println("Hello! Let's pick some winners!")
    println("-----------------------------------------")
    DialogController
  }

  def getWinners = winners

  def DialogController {
    var retry = true
    while (retry) {
      try {
        print("How many winners would you like to pick? ")
        var readInteger = readInt()
        winnerCount = readInteger
        retry = false
      } catch {
        case e: Exception => errorHandler(e)
      }
    }
    println("-----------------------------------------")
    PickWinner
    println("The winners are:")
    println(getWinners.mkString(", "))
    println("-----------------------------------------")
    EndController
  }

  def EndController {
    var prompt = readLine("Would you like to pick more winners? ")
    println("-----------------------------------------")
    if (prompt.toLowerCase() == "y" || prompt.toLowerCase() == "yes"){
      DialogController
    } else if (prompt.toLowerCase == "n" || prompt.toLowerCase() == "no"){
      println("OK. Thanks for using Winner Picker!")
      Thread.sleep(1500)
      System.exit(0)
    } else {
      println("Please answer 'yes' or 'no'.")
      println("-----------------------------------------")
    }
  }

  def PickWinner {
    var rnd = new Random
    if (!textParsed){ ParseText; textParsed = true }
    for (i <- 0 until winnerCount){
      var index = rnd.nextInt(lines.length)
      var winner = lines(index)
      winners += winner
      lines.remove(index)
    }
  }

  def ParseText {
    var retry = true
    while (retry) {
    try {
      var prompt2 = readLine("Please make sure the list of items is in the same folder as the source files and it is renamed 'parseable.txt'. When you're ready, say 'yes'. ")
      println("-----------------------------------------")
      if (prompt2.toLowerCase() == "y" || prompt2.toLowerCase() == "yes"){
        for (line <- Source.fromFile("parseable.txt").getLines()){
          lines += line
        }
        retry = false
      } else {
        println("-----------------------------------------")
        println("Please answer 'yes' when you are ready.")
        println("-----------------------------------------")
    }
    } catch {
      case e: Exception => errorHandler(e)
    }
    }
  }

  def errorHandler(e: Exception){
    println("-----------------------------------------")
    println("Exception caught: " + e)
    println("-----------------------------------------")
  }
}
