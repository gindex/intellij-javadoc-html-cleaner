package com.github.gindex.cleaner

import com.intellij.ide.highlighter.JavaFileHighlighter
import com.intellij.openapi.editor.HighlighterColors.TEXT
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
        val boldStyleKey = createTextAttributesKey("BOLD_FONT", TEXT)
        val strongStyleKey = createTextAttributesKey("STRONG_FONT", TEXT)
        val emStyleKey = createTextAttributesKey("EM_FONT", TEXT)
        val h1StyleKey = createTextAttributesKey("H1_FONT", TEXT)
        val h2StyleKey = createTextAttributesKey("H2_FONT", TEXT)
        val h3StyleKey = createTextAttributesKey("H3_FONT", TEXT)
        val h4StyleKey = createTextAttributesKey("H4_FONT", TEXT)
        val h5StyleKey = createTextAttributesKey("H5_FONT", TEXT)
        val h6StyleKey = createTextAttributesKey("H6_FONT", TEXT)
        val preStyleKey = createTextAttributesKey("PRE_FONT", TEXT)
        val linkStyleKey = createTextAttributesKey("LINK_FONT", TEXT)
        val codeStyleKey = createTextAttributesKey("CODE_FONT", TEXT)
        val italicStyleKey = createTextAttributesKey("ITALIC_FONT", TEXT)
        val citeStyleKey = createTextAttributesKey("CITE_FONT", TEXT)
        val teletypeStyleKey = createTextAttributesKey("TT_FONT", TEXT)
        val jCodeStyleKey = createTextAttributesKey("JCODE_FONT", TEXT)
        val jLinkStyleKey = createTextAttributesKey("JLINK_FONT", TEXT)
        val jValueStyleKey = createTextAttributesKey("JVALUE_FONT", TEXT)
        val jLinkplainStyleKey = createTextAttributesKey("JLINKPLAIN_FONT", TEXT)
        val jLiteralStyleKey = createTextAttributesKey("JLITERAL_FONT", TEXT)
    }

    override fun getHighlighter(): SyntaxHighlighter = JavaFileHighlighter(LanguageLevel.HIGHEST)

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> = mapOf(
            "b" to boldStyleKey,
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
            "i" to italicStyleKey,
            "cite" to citeStyleKey,
            "teletype" to teletypeStyleKey,
            "_code" to jCodeStyleKey,
            "_value" to jValueStyleKey,
            "_link" to jLinkStyleKey,
            "_linkplain" to jLinkplainStyleKey,
            "_literal" to jLiteralStyleKey
    ).toMutableMap()

    override fun getIcon(): Icon? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = arrayOf(
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
            AttributesDescriptor("<h6>", h6StyleKey),
            AttributesDescriptor("{@code }", jCodeStyleKey),
            AttributesDescriptor("{@value }", jValueStyleKey),
            AttributesDescriptor("{@link }", jLinkStyleKey),
            AttributesDescriptor("{@linkplain }", jLinkplainStyleKey),
            AttributesDescriptor("{@literal }", jLiteralStyleKey)
    )

    override fun getColorDescriptors(): Array<ColorDescriptor> = emptyArray()

    override fun getDisplayName(): String = "JavaDoc HTML Cleaner"

    override fun getDemoText(): String = """
        |/**
        | * This is a test comment
        | *
        | * Test for bold: <b>bold</b>
        | * Test for strong: <strong>strong</strong>
        | * Test for em: <em>em</em>
        | * Test for h1: <h1>h1</h1>
        | * Test for h2: <h2>h2</h2>
        | * Test for h3: <h3>h3</h3>
        | * Test for h4: <h4>h4</h4>
        | * Test for h5: <h5>h5</h5>
        | * Test for h6: <h6>h6</h6>
        | * Test for pre: <pre>pre</pre>
        | * Test for link: <link>link</link>
        | * Test for code: <code>code</code>
        | * Test for italic: <i>italic</i>
        | * Test for cite: <cite>cite</cite>
        | * Test for teletype: <teletype>teletype</teletype>
        | * Test for @code: <_code>{@code code}</_code>
        | * Test for @value: <_value>{@value value}</_value>
        | * Test for @link: <_link>{@link link}</_link>
        | * Test for @linkplain: <_linkplain>{@linkplain linkplain}</_linkplain>
        | * Test for @literal: <_literal>{@literal literal}</_literal>
        | */ 
        """.trimMargin()
}
