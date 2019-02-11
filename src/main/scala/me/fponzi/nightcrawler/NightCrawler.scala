package me.fponzi.nightcrawler
import scala.collection.mutable.ListBuffer

class NightCrawler (host: String, numberOfThreads: Int = 1, followLinks : Boolean = true) {
  private val reportIf = new ListBuffer[FetchedResource => Boolean]()

  def reportIf_(conditionChecker: FetchedResource => Boolean): NightCrawler= {
    reportIf.append(conditionChecker)// conditionChecker
    this
  }
  def run() : Unit = {
    println("Running the night crawler bro!")
    val fetcher = new Fetcher(host)
    fetcher.run()
    var res = fetcher.getFetchedPages.filter(p => reportIf.exists(fn => fn(p))).result()
    println("Scan complete.")
    println("Fetched pages:")
    res.foreach(println)
  }
  /*
  Data to save:
    response_time
    resonse_code
    header_data
    export_page
    fetch of media files
   */
}
