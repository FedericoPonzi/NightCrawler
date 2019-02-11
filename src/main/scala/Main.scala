import me.fponzi.nightcrawler.NightCrawler

object Main extends App {
  println("Hello, world!")
  //val nightCrawler = new NightCrawler("https://blog.informaticalab.com")
  val nightCrawler = new NightCrawler("https://fponzi.me")
  //nightCrawler.run()
  println(nightCrawler
  /**
    * Test for 404s:
    */
    .reportIf_(fetchedPage => fetchedPage.responseCode == 404)
  /**
    * Test for page size:
    */
    .reportIf_(fetchedPage => fetchedPage.pageSize > 1500)
    .run())

}
