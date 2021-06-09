package org.ordus.networklinkspeedtracker.gui

import org.ordus.networklinkspeedtracker.model.NetworkLinkSpeedConfig

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, FlowLayout, GridLayout, Toolkit}
import javax.swing._

class ConfigWindow extends JDialog {
  setModal(true)
  setResizable(false)
  setAlwaysOnTop(true)
  setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

  var configNetwork: NetworkLinkSpeedConfig = _

  val intervalLabel = new JLabel("Interval")
  val intervalText = new JTextPane()
  val interfaceLabel = new JLabel("Interface")
  val interfaceText = new JTextPane()

  val okButton: JButton = new JButton("Ok")
  okButton.addActionListener(new ActionListener() {
    override def actionPerformed(e: ActionEvent): Unit = {
      configNetwork = NetworkLinkSpeedConfig(intervalText.getText.toInt, interfaceText.getText)
      dispose()
    }
  })
  val cancelButton: JButton = new JButton("Cancel")
  cancelButton.addActionListener(new ActionListener() {

    override def actionPerformed(e: ActionEvent): Unit = {
      dispose()
    }
  })

  add(new JPanel(new BorderLayout()) {
    add(new JPanel(new GridLayout(2, 0)) {
      add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {
        add(intervalLabel)
        add(intervalText)
      })
      add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {
        add(interfaceLabel)
        add(interfaceText)
      })
    }, BorderLayout.CENTER)
    add(new JPanel(new FlowLayout(FlowLayout.CENTER)) {
      add(okButton)
      add(cancelButton)
    }, BorderLayout.SOUTH)
  })
  pack()
  setLocation((Toolkit.getDefaultToolkit.getScreenSize.width) / 2 - getWidth / 2, (Toolkit.getDefaultToolkit.getScreenSize.height) / 2 - getHeight / 2)

  def showConfigDialog(config: NetworkLinkSpeedConfig): Option[NetworkLinkSpeedConfig] = {
    if (isVisible) {
      requestFocus()
      None
    } else {
      configNetwork = config
      intervalText.setText(config.interval.toString)
      interfaceText.setText(config.interface)
      setVisible(true)
      Some(configNetwork)
    }
  }

}
