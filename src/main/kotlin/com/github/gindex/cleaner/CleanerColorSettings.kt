package com.github.gindex.cleaner

import com.intellij.ide.highlighter.JavaFileHighlighter
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.pom.java.LanguageLevel
import javax.swing.Icon


class CleanerColorSettings : ColorSettingsPage {
    companion object {
        val boldStyleKey = createTextAttributesKey("BOLD_FONT", HighlighterColors.TEXT)
        val italicStyleKey = createTextAttributesKey("ITALIC_FONT", HighlighterColors.TEXT)

        private val ATTRS = arrayOf(
            AttributesDescriptor("<b>", boldStyleKey),
            AttributesDescriptor("<strong>", boldStyleKey),
            AttributesDescriptor("<pre>", boldStyleKey),
            AttributesDescriptor("<em>", boldStyleKey),
            AttributesDescriptor("<h1..6>", boldStyleKey),
            AttributesDescriptor("<a href= ..> | <a name= ..>", boldStyleKey),
            AttributesDescriptor("<i>", italicStyleKey),
            AttributesDescriptor("<code>", italicStyleKey),
            AttributesDescriptor("<cite>", italicStyleKey),
            AttributesDescriptor("<tt>", italicStyleKey)
        )

    }

    override fun getHighlighter(): SyntaxHighlighter {
        return JavaFileHighlighter(LanguageLevel.HIGHEST)
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> {
        return mapOf(
            "bold" to boldStyleKey,
            "italic" to italicStyleKey
        ).toMutableMap()
    }

    override fun getIcon(): Icon? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return ATTRS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "JavaDoc HTML Cleaner"
    }

    override fun getDemoText(): String {
        return "My demo: <code> this.toString() </code>"
    }

}