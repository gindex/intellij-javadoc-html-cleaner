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
import com.github.gindex.cleaner.CleanerColorSettings.Companion.linkStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.preStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.strongStyleKey
import com.github.gindex.cleaner.CleanerColorSettings.Companion.teletypeStyleKey
import com.intellij.openapi.editor.colors.TextAttributesKey
import java.util.regex.Matcher
import java.util.regex.Pattern

open class Tag(name: String) {
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

        val tags = listOf(BOLD, STRONG, EM, PRE, H1, H2, H3, H4, H5, H6, LINK, CODE, ITALIC, CITE, TELETYPE)

        val anyTagPattern: Pattern = Pattern.compile("(</?.+?>)+", Pattern.DOTALL)
    }

    //TODO
    val tagStartAndEndPattern: Pattern = Pattern.compile("<${name}.+?</${name}>"/*, Pattern.DOTALL*/)
}

class BoldTag(name: String) : Tag(name)
class StrongTag(name: String) : Tag(name)
class EmTag(name: String) : Tag(name)
class H1Tag(name: String) : Tag(name)
class H2Tag(name: String) : Tag(name)
class H3Tag(name: String) : Tag(name)
class H4Tag(name: String) : Tag(name)
class H5Tag(name: String) : Tag(name)
class H16Tag(name: String) : Tag(name)
class PreTag(name: String) : Tag(name)
class LinkTag(name: String) : Tag(name)
class CodeTag(name: String) : Tag(name)
class ItalicTag(name: String) : Tag(name)
class CiteTag(name: String) : Tag(name)
class TeletypeTag(name: String) : Tag(name)

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