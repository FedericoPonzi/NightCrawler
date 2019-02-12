import me.fponzi.nightcrawler.NightCrawlerBuilder

object Main extends App {
  println("Hello, world!")
  //val host = "https://blog.informaticalab.com"
  val host = "https://fponzi.me"

  val nightCrawlerBuilder = new NightCrawlerBuilder(host)
  val nightCrawler = nightCrawlerBuilder.addReportIf(fetchedPage => fetchedPage.responseCode == 404)
                                        .addReportIf(fetchedPage => fetchedPage.pageSize > 1500)
                                        .build

  println(nightCrawler.run())

}
