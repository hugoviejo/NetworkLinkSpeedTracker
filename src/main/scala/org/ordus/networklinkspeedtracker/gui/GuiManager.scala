package org.ordus.networklinkspeedtracker.gui

import org.ordus.networklinkspeedtracker.model.NetworkLinkSpeedConfig
import org.ordus.networklinkspeedtracker.service.NetworkChecker

import java.awt.event.{ActionEvent, ActionListener}
import java.awt._

object GuiManager extends IconManager {
  private var trayIcon: TrayIcon = _
  private var checkThread: Thread = _

  private var config: NetworkLinkSpeedConfig = NetworkLinkSpeedConfig(5000, "en7")

  def createGui(): Boolean = {
    System.setProperty("apple.awt.UIElement", "true")
    if (!SystemTray.isSupported) {
      System.err.println("SystemTray is not supported");
      false;
    } else {
      val configWindow = new ConfigWindow()
      val popup: PopupMenu = new PopupMenu()
      trayIcon = new TrayIcon(createImage(IconNetwork, "tray icon"));
      val tray: SystemTray = SystemTray.getSystemTray;

      val displayConfig: MenuItem = new MenuItem("Config")
      displayConfig.addActionListener(new ActionListener() {
        override def actionPerformed(e: ActionEvent): Unit = {
          stopCheck()
          configWindow.showConfigDialog(config) match {
            case Some(configResult) => {
              config = configResult
              startCheck(config)
            }
            case None => {}
          }
        }
      })
      val exitItem: MenuItem = new MenuItem("Exit")
      exitItem.addActionListener(new ActionListener() {
        def actionPerformed(e: ActionEvent): Unit = {
          tray.remove(trayIcon)
          System.exit(0)
        }
      })
      popup.add(displayConfig)
      popup.add(exitItem)
      trayIcon.setPopupMenu(popup)

      try {
        tray.add(trayIcon)
        startCheck(config)
        true
      } catch {
        case e: AWTException =>
          System.err.println("TrayIcon could not be added.")
          e.printStackTrace()
          System.exit(1)
          false
      }
    }
  }

  def updateIcon(config: NetworkLinkSpeedConfig, speed: String): Unit = {
    trayIcon.setImage(getNetworkImage(speed))
    trayIcon.setToolTip(s"Network Interface: ${config.interface}\nNetwork Link: $speed")
  }

  def startCheck(config: NetworkLinkSpeedConfig): Unit = {
    checkThread = new Thread(new NetworkChecker(config))
    checkThread.start()
  }

  def stopCheck(): Unit = {
    checkThread.interrupt()
  }
}
