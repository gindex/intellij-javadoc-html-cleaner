package com.github.gindex.cleaner

import com.github.gindex.cleaner.CleanerColorSettings.Companion.boldStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.italicStyleKey
import com.intellij.openapi.editor.colors.TextAttributesKey
import java.util.regex.Matcher
import java.util.regex.Pattern

open class Tag(name: String) {
    companion object {
        private val BOLD = BoldTag("b")
        private val STRONG = BoldTag("strong")
        private val EM = BoldTag("em")
        private val H1 = BoldTag("h1")
        private val H2 = BoldTag("h2")
        private val H3 = BoldTag("h3")
        private val H4 = BoldTag("h4")
        private val H5 = BoldTag("h5")
        private val H6 = BoldTag("h6")
        private val PRE = PreTag("pre")
        private val LINK = LinkTag("a")
        private val CODE = CodeTag("code")
        private val ITALIC = ItalicTag("i")
        private val CITE = ItalicTag("cite")
        private val TELETYPE = ItalicTag("tt")

        val tags = listOf(BOLD, STRONG, EM, PRE, H1, H2, H3, H4, H5, H6, LINK, CODE, ITALIC, CITE, TELETYPE)

        val anyTagPattern: Pattern = Pattern.compile("(</?.+?>)+", Pattern.DOTALL)
    }

    //TODO
    val tagStartAndEndPattern: Pattern = Pattern.compile("<${name}.+?</${name}>"/*, Pattern.DOTALL*/)
}

class BoldTag(name: String) : Tag(name)
class ItalicTag(name: String) : Tag(name)
class PreTag(name: String) : Tag(name)
class LinkTag(name: String) : Tag(name)
class CodeTag(name: String) : Tag(name)

object TagStyle {
    fun mapTagToStyle(tag: Tag): TextAttributesKey? {
        return when (tag) {
            is BoldTag -> boldStyleKey
            is ItalicTag -> italicStyleKey
            is PreTag -> boldStyleKey
            is LinkTag -> boldStyleKey
            is CodeTag -> italicStyleKey
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