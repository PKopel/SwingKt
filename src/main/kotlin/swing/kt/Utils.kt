package swingKt

import java.awt.Color
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.plaf.FontUIResource

fun show(f: JFrame, name: String, exitOperation: Int = JFrame.EXIT_ON_CLOSE, width: Int = 0, height: Int = 0) {
    SwingUtilities.invokeLater {
        f.title = name
        f.defaultCloseOperation = exitOperation
        if (width == 0 || height == 0)
            f.extendedState = JFrame.MAXIMIZED_BOTH
        else
            f.setSize(width, height)
        f.isVisible = true
    }
}

fun setUIFont(f: FontUIResource) {
    val keys = UIManager.getDefaults().keys()
    while (keys.hasMoreElements()) {
        val key = keys.nextElement()
        val value = UIManager.get(key)
        if (value is FontUIResource) {
            UIManager.put(key, f)
        }
    }
}

fun getRGBColor(r: Int, g: Int, b: Int): Color {
    val hsb = FloatArray(3)
    Color.RGBtoHSB(r, g, b, hsb)
    return Color.getHSBColor(hsb[0], hsb[1], hsb[2])
}

fun radioMenuItem(
        text: String = "",
        background: Color = Color.LIGHT_GRAY,
        listener: (ActionEvent) -> Unit = {},
        selected: Boolean = false
): JRadioButtonMenuItem {
    val item = JRadioButtonMenuItem(text)
    item.isSelected = selected
    item.background = background
    item.addActionListener(listener)
    return item
}

fun button(
        text: String = "",
        keyShortcut: KeyStroke? = null,
        background: Color = Color.LIGHT_GRAY,
        action: (ActionEvent) -> Unit
): JButton {
    val button = JButton()
    button.background = background
    button.action = object : AbstractAction() {
        override fun actionPerformed(p0: ActionEvent) = action(p0)
    }
    if (keyShortcut != null) {
        button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyShortcut, "key shortcut")
        button.actionMap.put("key shortcut", button.action)
    }
    button.text = text
    return button
}

fun textField(
        text: String = "",
        background: Color = Color.LIGHT_GRAY,
        editable: Boolean = true,
        listener: (ActionEvent) -> Unit = {}
): JTextField {
    val field = JTextField(text)
    field.background = background
    field.isEditable = editable
    field.addActionListener(listener)
    return field
}