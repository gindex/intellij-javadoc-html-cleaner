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
import com.intellij.openapi.editor.colors.TextAttributesKey
import java.util.regex.Matcher
import java.util.regex.Pattern

abstract class Tag() {
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
}

open class HtmlTag(name: String): Tag() {
    companion object {
        val anyTagPattern: Pattern = Pattern.compile("(</?.+?>)+", Pattern.DOTALL)
    }
    override val tagStartAndEndPattern: Pattern = Pattern.compile("<${name}(\\s.+?)?>.+?</${name}>", Pattern.DOTALL)
}

open class JavadocTag(name: String): Tag() {

    override val tagStartAndEndPattern: Pattern = Pattern.compile("\\{@${name}.+?}", Pattern.DOTALL)
}

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

class JCodeTag(name: String) : JavadocTag(name)
class JLinkTag(name: String) : JavadocTag(name)
class JValueTag(name: String) : JavadocTag(name)
class JLinkplaingTag(name: String) : JavadocTag(name)
class JLiteral(name: String) : JavadocTag(name)


object TagStyle {
    fun mapTagToStyle(tag: Tag): TextAttributesKey? {
        return when (tag) {
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
}

data class TextAndRange(val text: String, val start: Int, val end: Int)

fun Pattern.extractMatchingRanges(str: String): List<TextAndRange> {
    tailrec fun collect(matcher: Matcher, rangeCollector: List<TextAndRange>): List<TextAndRange> {
        return if (matcher.find()) {
            val range = matcher.run {
                TextAndRange(group(), start(), end())
            }
            collect(matcher, rangeCollector.plus(range))
        } else {
            rangeCollector
        }
    }

    val matcher = this.matcher(str)
    return collect(matcher, emptyList())
}