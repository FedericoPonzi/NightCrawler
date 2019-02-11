package me.fponzi.nightcrawler

class FetchedResource(val host : String, val responseCode: Int, val pageSize : Int, val links : List[String]){
  override def toString: String = {
    "FetchedPage(host=%s, responsecode=%d, pageSize=%d, links[%d]=[....]".format(host, responseCode, pageSize, links.size)
  }

  override def equals(obj: Any): Boolean =
    obj match {
      case obj: FetchedResource => obj.host.equals(this.host)
      case _ => obj.equals(this)
    }
}
