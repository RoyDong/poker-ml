package com.roydong.poker.dealer.common

/**
  * Created by roydong on 29/07/2017.
  */

case class KLine(
                  openTime: Int = 0,
                  openPrice: Int = 0,

                  maxPrice: Int = 0,
                  minPrice: Int = 0,

                  avgPrice: Int = 0,
                  tradeAmount: Int = 0,
                  tradeNum: Int = 0,

                  closeTime: Int = 0,
                  closePrice: Int = 0
                ) {

}
