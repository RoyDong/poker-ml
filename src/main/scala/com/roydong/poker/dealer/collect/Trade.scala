package com.roydong.poker.dealer.collect

import java.text.SimpleDateFormat
import java.util.Date

import com.typesafe.config.ConfigFactory

import scala.io.Source
import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * Created by roydong on 29/07/2017.
  */
object Trade {

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println(
        s"""
           |Usage: GetTradeData <market:string>
        """.stripMargin)
      System.exit(1)
    }
    val market = args(0).trim
    val conf = ConfigFactory.load()

    val tid = 0
    val url = conf.getString("markets.%s.url") + "?symbol=btc_cny&since=%s"
    val raw = Source.fromURL(url.format(tid), "UTF8").mkString
    val trades = parseOKCoinTrade(raw)

    while (true) {
      val url = conf.getString("markets.%s.url") + "?symbol=btc_cny&since=%s"
      val raw = Source.fromURL(url.format(tid), "UTF8").mkString
    }
  }

  def parseOKCoinTrade(raw: String): Seq[Trade] = {
    val trades: Seq[Trade] = for {
      JArray(trades) <- parse(raw)
      JObject(trade) <- trades
      JField("tid", JString(id)) <- trade
      JField("amount", JDouble(amount)) <- trade
      JField("price", JDouble(price)) <- trade
      JField("date_ms", JLong(tms)) <- trade

      t = Trade(
        id = id,
        amount = amount,
        price = price,
        time = tms
      )
    } yield t

    trades.sortWith((x, y) => x.id < y.id)
  }

  private case class Trade(id: String = "", amount: Double = 0, price: Double = 0, time: Long = 0) {

    val date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time))

    def toTextRow(): String = {
      "%s %.6f %.6f %d".format(id, amount, price, time)
    }
  }

}
