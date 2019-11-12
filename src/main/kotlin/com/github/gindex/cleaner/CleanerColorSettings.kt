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
        val strongStyleKey = createTextAttributesKey("STRONG_FONT", HighlighterColors.TEXT)
        val emStyleKey = createTextAttributesKey("EM_FONT", HighlighterColors.TEXT)
        val h1StyleKey = createTextAttributesKey("H1_FONT", HighlighterColors.TEXT)
        val h2StyleKey = createTextAttributesKey("H2_FONT", HighlighterColors.TEXT)
        val h3StyleKey = createTextAttributesKey("H3_FONT", HighlighterColors.TEXT)
        val h4StyleKey = createTextAttributesKey("H4_FONT", HighlighterColors.TEXT)
        val h5StyleKey = createTextAttributesKey("H5_FONT", HighlighterColors.TEXT)
        val h6StyleKey = createTextAttributesKey("H6_FONT", HighlighterColors.TEXT)
        val preStyleKey = createTextAttributesKey("PRE_FONT", HighlighterColors.TEXT)
        val linkStyleKey = createTextAttributesKey("LINK_FONT", HighlighterColors.TEXT)
        val codeStyleKey = createTextAttributesKey("CODE_FONT", HighlighterColors.TEXT)
        val italicStyleKey = createTextAttributesKey("ITALIC_FONT", HighlighterColors.TEXT)
        val citeStyleKey = createTextAttributesKey("CITE_FONT", HighlighterColors.TEXT)
        val teletypeStyleKey = createTextAttributesKey("TT_FONT", HighlighterColors.TEXT)


        private val ATTRS = arrayOf(
                AttributesDescriptor("<b>", boldStyleKey),
                AttributesDescriptor("<strong>", strongStyleKey),
                AttributesDescriptor("<pre>", preStyleKey),
                AttributesDescriptor("<em>", emStyleKey),
                AttributesDescriptor("<a ..>", linkStyleKey),
                AttributesDescriptor("<i>", italicStyleKey),
                AttributesDescriptor("<code>", codeStyleKey),
                AttributesDescriptor("<cite>", citeStyleKey),
                AttributesDescriptor("<tt>", teletypeStyleKey),
                AttributesDescriptor("<h1>", h1StyleKey),
                AttributesDescriptor("<h2>", h2StyleKey),
                AttributesDescriptor("<h3>", h3StyleKey),
                AttributesDescriptor("<h4>", h4StyleKey),
                AttributesDescriptor("<h5>", h5StyleKey),
                AttributesDescriptor("<h6>", h6StyleKey)
        )

    }

    override fun getHighlighter(): SyntaxHighlighter {
        return JavaFileHighlighter(LanguageLevel.HIGHEST)
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> {
        return mapOf(
                "bold" to boldStyleKey,
                "strong" to strongStyleKey,
                "em" to emStyleKey,
                "h1" to h1StyleKey,
                "h2" to h2StyleKey,
                "h3" to h3StyleKey,
                "h4" to h4StyleKey,
                "h5" to h5StyleKey,
                "h6" to h6StyleKey,
                "pre" to preStyleKey,
                "link" to linkStyleKey,
                "code" to codeStyleKey,
                "italic" to italicStyleKey,
                "cite" to citeStyleKey,
                "teletype" to teletypeStyleKey
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