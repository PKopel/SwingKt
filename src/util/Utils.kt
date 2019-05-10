package util

import java.awt.Color
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.event.AncestorEvent
import javax.swing.event.AncestorListener

fun run(f: JFrame, width: Int, height: Int, name: String? = null) {
    SwingUtilities.invokeLater {
        f.title = name ?: f::class.java.simpleName
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.setSize(width, height)
        f.isVisible = true
    }
}

fun getRGBColor(r: Int, g: Int, b:Int): Color{
    val hsb = FloatArray(3)
    Color.RGBtoHSB(r,g,b,hsb)
    return Color.getHSBColor(hsb[0],hsb[1],hsb[2])
}

fun radioMenuItem(text: String = "",
                  background: Color = Color.LIGHT_GRAY,
                  listener: (ActionEvent) -> Unit = {},
                  selected: Boolean = false): JRadioButtonMenuItem {
    val item = JRadioButtonMenuItem(text)
    item.isSelected = selected
    item.background = background
    item.addActionListener(listener)
    return item
}

fun button(text: String = "",
           background: Color = Color.LIGHT_GRAY,
           listener: (ActionEvent) -> Unit = {}): JButton {
    val button = JButton(text)
    button.background = background
    button.addActionListener(listener)
    return button
}

fun textField(text: String = "",
              background: Color = Color.LIGHT_GRAY,
              editable: Boolean = true,
              listener: (ActionEvent) -> Unit = {}): JTextField{
    val field = JTextField(text)
    field.background=background
    field.isEditable=editable
    field.addActionListener(listener)
    return field
}