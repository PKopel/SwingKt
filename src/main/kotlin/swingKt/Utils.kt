package swingKt

import java.awt.Color
import java.awt.event.ActionEvent
import java.text.Format
import javax.swing.*
import javax.swing.border.TitledBorder
import javax.swing.plaf.FontUIResource

/**
 * Sets up and shows frame [f] with title [name],
 * default close operation [exitOperation] with default value [JFrame.EXIT_ON_CLOSE],
 * [width] with default value 0 and [height] with default value 0.
 */
fun show(
        f: JFrame, name: String,
        exitOperation: Int = JFrame.EXIT_ON_CLOSE,
        width: Int = 0,
        height: Int = 0
) {
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

/**
 * Sets font [f] as default font in an app.
 */
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

/**
 * Translates RGB color specified by [r], [g] and [b] arguments to [java.awt.Color] object
 */
fun getRGBColor(r: Int, g: Int, b: Int): Color {
    val hsb = FloatArray(3)
    Color.RGBtoHSB(r, g, b, hsb)
    return Color.getHSBColor(hsb[0], hsb[1], hsb[2])
}

/**
 * Creates custom [ListCellRenderer] from functions passed as arguments.
 */
fun <T> getListCellRenderer(foregroundColorFunction: ((T) -> Color)? = null, backgroundColorFunction: ((T) -> Color)? = null):
        ListCellRenderer<in T> {
    return ListCellRenderer<T> { list, value, index, isSelected, cellHasFocus ->
        val component = DefaultListCellRenderer().getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
        if (foregroundColorFunction != null) component.foreground = foregroundColorFunction(value)
        if (backgroundColorFunction != null) component.background = backgroundColorFunction(value)
        component
    }
}

/**
 * Creates [JRadioButtonMenuItem] from
 *  [text] (default ""), [background] (default [Color.LIGHT_GRAY]),
 *  [listener] (default {}) and [selected] (default false).
 */
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

/**
 * Creates [JButton] from
 *  [text] (default ""), [keyShortcut] (default null),
 *  [background] (default [Color.LIGHT_GRAY]) and [action] (no default value).
 */
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

/**
 * Creates [JTextField] with [TitledBorder] from
 *  [text] (default ""), [background] (default [Color.LIGHT_GRAY]),
 *  [editable] (default true), [title] (default "") and [listener] (default {}).
 */
fun textField(
        text: String = "",
        background: Color = Color.LIGHT_GRAY,
        editable: Boolean = true,
        title: String = "",
        listener: (ActionEvent) -> Unit = {}
): JTextField {
    val field = JTextField(text)
    field.background = background
    field.isEditable = editable
    field.addActionListener(listener)
    if (title != "") field.border = TitledBorder(title)
    return field
}

/**
 * Creates [JFormattedTextField] with [TitledBorder] from
 *  [text] (default ""), [background] (default [Color.LIGHT_GRAY]),
 *  [editable] (default true), [title] (default "") and [listener] (default {}).
 */
fun formattedTextField(
        text: String = "",
        formatter: Format,
        background: Color = Color.LIGHT_GRAY,
        editable: Boolean = true,
        title: String = "",
        listener: (ActionEvent) -> Unit = {}
): JFormattedTextField {
    val field = JFormattedTextField(formatter)
    field.text = text
    field.background = background
    field.isEditable = editable
    field.addActionListener(listener)
    if (title != "") field.border = TitledBorder(title)
    return field
}

/**
 * Creates [Pair] of [JList] and [DefaultListModel] from
 *  [elements] [Collection], [model] (default new object of [DefaultListModel] class),
 *  [title] (default ""), [background] (default [Color.LIGHT_GRAY]),
 *  [foregroundColorFunction] (default null) and [backgroundColorFunction] (default null).
 */
fun <T> list(
        elements: Collection<T>,
        model: DefaultListModel<T> = DefaultListModel(),
        title: String = "",
        background: Color = Color.LIGHT_GRAY,
        foregroundColorFunction: ((T) -> Color)? = null,
        backgroundColorFunction: ((T) -> Color)? = null
): Pair<JList<T>, DefaultListModel<T>> {
    elements.forEach { model.addElement(it) }
    val list = JList<T>(model)
    if (title != "") list.border = TitledBorder(title)
    list.background = background
    list.cellRenderer = getListCellRenderer(
            foregroundColorFunction,
            backgroundColorFunction)
    return Pair(list, model)
}

/**
 * Creates [Pair] of [JComboBox] and [DefaultComboBoxModel] from
 *  [elements] [Collection], [model] (default new object of [DefaultComboBoxModel] class),
 *  [rowCount] (default size of [elements]), [title] (default ""),
 *  [background] (default [Color.LIGHT_GRAY])
 *  and [action] (default {}).
 */
fun <T> comboBox(
        elements: Collection<T>,
        model: DefaultComboBoxModel<T> = DefaultComboBoxModel(),
        rowCount: Int = elements.size,
        title: String = "",
        background: Color = Color.LIGHT_GRAY,
        action: (ActionEvent) -> Unit = {}
): Pair<JComboBox<T>, DefaultComboBoxModel<T>> {
    elements.forEach { model.addElement(it) }
    val box = JComboBox<T>(model)
    if (title != "") box.border = TitledBorder(title)
    box.addActionListener(action)
    box.maximumRowCount = rowCount
    box.background = background
    return Pair(box, model)
}