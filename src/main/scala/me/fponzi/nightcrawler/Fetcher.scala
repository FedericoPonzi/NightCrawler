package me.fponzi.nightcrawler

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup

import scala.collection.JavaConverters.asScalaBuffer
import scala.collection.mutable.ListBuffer

class Fetcher {
  private val fetchedPages = new ListBuffer[FetchedResource]
  private val toVisit = new ListBuffer[String]
  private var baseHost : String = _
  def this(baseHost: String) = {
    this()
    this.baseHost = baseHost
    toVisit.append(baseHost)
  }
  def getFetchedPages : ListBuffer[FetchedResource] = fetchedPages


  def run(): Unit = {
    toVisit.foreach(println)
    while(toVisit.nonEmpty) {
      fetch(toVisit.remove(0))
    }
  }
  def fetch(toFetch : String): Unit = {
    val client = HttpClientBuilder.create.build
    val request = new HttpGet(toFetch)
    request.addHeader("User-Agent", "Apache HTTPClient")
    val response = client.execute(request)
    val content = EntityUtils.toString(response.getEntity)
    val pageSize = content.length //response.getLastHeader("Content-Length") TODO: for some reason, from the command line the page size is pageSize+1 O_o
    val doc = Jsoup.parse(content)
    val linksFound = asScalaBuffer(doc.select("a"))
      .map(i => i.attr("abs:href"))
      .filter( el => !el.startsWith("#") && el.length > 0 && el.startsWith(baseHost))
      .toList

    fetchedPages.append(new FetchedResource(host = toFetch, responseCode = response.getStatusLine.getStatusCode, pageSize = pageSize, links= linksFound))

    linksFound.foreach(f => {
      if(fetchedPages.filter(t => t.host.equals(f)).isEmpty)
        toVisit.append(f)

    })
  }
}
