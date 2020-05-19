package com.github.gindex.cleaner

import com.github.gindex.cleaner.CleanerColorSettings.Companion.boldStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.citeStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.codeStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.emStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.h1StyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.h2StyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.h3StyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.h4StyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.h5StyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.h6StyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.italicStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.jCodeStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.jLinkStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.jLinkplainStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.jLiteralStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.jValueStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.linkStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.preStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.strongStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.teletypeStyleKey
import com.github.gindex.cleaner.HtmlTag.BoldTag
import com.github.gindex.cleaner.HtmlTag.CiteTag
import com.github.gindex.cleaner.HtmlTag.CodeTag
import com.github.gindex.cleaner.HtmlTag.EmTag
import com.github.gindex.cleaner.HtmlTag.H16Tag
import com.github.gindex.cleaner.HtmlTag.H1Tag
import com.github.gindex.cleaner.HtmlTag.H2Tag
import com.github.gindex.cleaner.HtmlTag.H3Tag
import com.github.gindex.cleaner.HtmlTag.H4Tag
import com.github.gindex.cleaner.HtmlTag.H5Tag
import com.github.gindex.cleaner.HtmlTag.ItalicTag
import com.github.gindex.cleaner.HtmlTag.LinkTag
import com.github.gindex.cleaner.HtmlTag.PreTag
import com.github.gindex.cleaner.HtmlTag.StrongTag
import com.github.gindex.cleaner.HtmlTag.TeletypeTag
import com.github.gindex.cleaner.JavadocTag.JCodeTag
import com.github.gindex.cleaner.JavadocTag.JLinkTag
import com.github.gindex.cleaner.JavadocTag.JLinkplaingTag
import com.github.gindex.cleaner.JavadocTag.JLiteral
import com.github.gindex.cleaner.JavadocTag.JValueTag
import com.intellij.openapi.editor.colors.TextAttributesKey
import java.util.regex.Matcher
import java.util.regex.Pattern

abstract class Tag {
    companion object {
        private val BOLD = BoldTag("b")
        private val STRONG = StrongTag("strong")
        private val EM = EmTag("em")
        private val H1 = H1Tag("h1")
        private val H2 = H2Tag("h2")
        private val H3 = H3Tag("h3")
        private val H4 = H4Tag("h4")
        private val H5 = H5Tag("h5")
        private val H6 = H16Tag("h6")
        private val PRE = PreTag("pre")
        private val LINK = LinkTag("a")
        private val CODE = CodeTag("code")
        private val ITALIC = ItalicTag("i")
        private val CITE = CiteTag("cite")
        private val TELETYPE = TeletypeTag("tt")
        private val JCODE = JCodeTag("code")
        private val JLINK = JLinkTag("link")
        private val JVALUE = JValueTag("value")
        private val JLINKPLAING = JLinkplaingTag("linkplain")
        private val JLITERAL = JLiteral("literal")

        val tags = listOf(BOLD, STRONG, EM, PRE, H1, H2, H3, H4, H5, H6, LINK, CODE, ITALIC, CITE, TELETYPE,
                JCODE, JLINK, JVALUE, JLINKPLAING, JLITERAL)
    }

    abstract val tagStartAndEndPattern: Pattern

    fun style(): TextAttributesKey? = when (this) {
        is BoldTag -> boldStyleKey
        is StrongTag -> strongStyleKey
        is EmTag -> emStyleKey
        is H1Tag -> h1StyleKey
        is H2Tag -> h2StyleKey
        is H3Tag -> h3StyleKey
        is H4Tag -> h4StyleKey
        is H5Tag -> h5StyleKey
        is H16Tag -> h6StyleKey
        is PreTag -> preStyleKey
        is LinkTag -> linkStyleKey
        is CodeTag -> codeStyleKey
        is ItalicTag -> italicStyleKey
        is CiteTag -> citeStyleKey
        is TeletypeTag -> teletypeStyleKey
        is JCodeTag -> jCodeStyleKey
        is JLinkTag -> jLinkStyleKey
        is JValueTag -> jValueStyleKey
        is JLinkplaingTag -> jLinkplainStyleKey
        is JLiteral -> jLiteralStyleKey
        else -> null
    }
}

sealed class HtmlTag(name: String) : Tag() {
    companion object {
        val anyTagPattern: Pattern = Pattern.compile("(</?.+?>)+", Pattern.DOTALL)
    }

    override val tagStartAndEndPattern: Pattern = Pattern.compile("<${name}(\\s.+?)?>.+?</${name}>", Pattern.DOTALL)

    class BoldTag(name: String) : HtmlTag(name)
    class StrongTag(name: String) : HtmlTag(name)
    class EmTag(name: String) : HtmlTag(name)
    class H1Tag(name: String) : HtmlTag(name)
    class H2Tag(name: String) : HtmlTag(name)
    class H3Tag(name: String) : HtmlTag(name)
    class H4Tag(name: String) : HtmlTag(name)
    class H5Tag(name: String) : HtmlTag(name)
    class H16Tag(name: String) : HtmlTag(name)
    class PreTag(name: String) : HtmlTag(name)
    class LinkTag(name: String) : HtmlTag(name)
    class CodeTag(name: String) : HtmlTag(name)
    class ItalicTag(name: String) : HtmlTag(name)
    class CiteTag(name: String) : HtmlTag(name)
    class TeletypeTag(name: String) : HtmlTag(name)
}

sealed class JavadocTag(name: String) : Tag() {
    companion object {
        val anyTagPattern: Pattern = Pattern.compile("(\\s?\\{@\\w+)(.*?)(})", Pattern.DOTALL)
    }

    override val tagStartAndEndPattern: Pattern = Pattern.compile("\\{@${name}(\\s).*?}", Pattern.DOTALL)

    class JCodeTag(name: String) : JavadocTag(name)
    class JLinkTag(name: String) : JavadocTag(name)
    class JValueTag(name: String) : JavadocTag(name)
    class JLinkplaingTag(name: String) : JavadocTag(name)
    class JLiteral(name: String) : JavadocTag(name)
}

data class TextAndRange(val text: String, val start: Int, val end: Int)

fun Pattern.extractMatchingRanges(str: String, groupIds: List<Int> = listOf(0)): List<TextAndRange> {
    tailrec fun collectAll(matcher: Matcher, rangeCollector: List<TextAndRange>): List<TextAndRange> =
            if (matcher.find()) {
                val range = groupIds.map {
                    TextAndRange(matcher.group(it), matcher.start(it), matcher.end(it))
                }

                collectAll(matcher, rangeCollector + range)
            } else {
                rangeCollector
            }

    return collectAll(matcher(str), emptyList())
}
