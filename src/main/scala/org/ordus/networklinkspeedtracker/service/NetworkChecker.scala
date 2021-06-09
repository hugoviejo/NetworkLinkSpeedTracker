package org.ordus.networklinkspeedtracker.service

import org.ordus.networklinkspeedtracker.constants.Constants
import org.ordus.networklinkspeedtracker.gui.GuiManager
import org.ordus.networklinkspeedtracker.model.NetworkLinkSpeedConfig

import scala.sys.process._

class NetworkChecker(config: NetworkLinkSpeedConfig) extends Runnable {
  override def run(): Unit = {
    while (true) {
      val speed = checkNetworkLinkSpeed(config.interface)
      GuiManager.updateIcon(config, speed)
      try {
        Thread.sleep(config.interval)
      } catch {
        case _: InterruptedException => {}
      }
    }
  }

  def checkNetworkLinkSpeed(interface: String): String = {
    try {
      val interfaceData = s"ifconfig $interface".!!
      val media = interfaceData.split("\\n").filter(_.contains("media")).head
      media.substring(media.indexOf("(") + 1, media.indexOf("baseT"))
    } catch {
      case _: Exception => {
        Constants.SpeedNoConnection
      }
    }
  }
}
