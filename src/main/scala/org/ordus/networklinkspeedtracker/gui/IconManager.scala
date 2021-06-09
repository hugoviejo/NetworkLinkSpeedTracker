package org.ordus.networklinkspeedtracker.gui

import org.ordus.networklinkspeedtracker.constants.Constants

import java.awt.Image

trait IconManager {

  import java.net.URL
  import javax.swing.ImageIcon

  val IconNetwork:String = "/network.png"
  val IconNoNetwork:String = "/nonetwork.png"
  val IconNetwork100:String = "/network100.png"
  val IconNetwork1000:String = "/network1000.png"

  def createImage(path: String, description: String): Image = {
    val imageURL: URL = classOf[IconManager].getResource(path)
    if (imageURL == null) {
      System.err.println("Resource not found: " + path)
      null
    }
    else new ImageIcon(imageURL, description).getImage
  }

  def getNetworkImage(speed: String): Image = {
    createImage(speed match {
      case Constants.SpeedGigaEthernet => IconNetwork1000
      case Constants.SpeedFastEthernet => IconNetwork100
      case Constants.SpeedNoConnection => IconNoNetwork
    }, speed)
  }
}
