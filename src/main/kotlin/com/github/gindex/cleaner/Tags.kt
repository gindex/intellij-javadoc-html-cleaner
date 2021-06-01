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

interface Tag {
    val tagStartAndEndPattern: Pattern
    val style: TextAttributesKey

    companion object {
        val tags: Array<Tag> = emptyArray<Tag>() + HtmlTag.values() + JavadocTag.values()
    }
}

enum class HtmlTag(name: String, override val style: TextAttributesKey) : Tag {
    BOLD("b", boldStyleKey),
    STRONG("strong", strongStyleKey),
    EM("em", emStyleKey),
    H1("h1", h1StyleKey),
    H2("h2", h2StyleKey),
    H3("h3", h3StyleKey),
    H4("h4", h4StyleKey),
    H5("h5", h5StyleKey),
    H6("h6", h6StyleKey),
    PRE("pre", preStyleKey),
    LINK("a", linkStyleKey),
    CODE("code", codeStyleKey),
    ITALIC("i", italicStyleKey),
    CITE("cite", citeStyleKey),
    TELETYPE("tt", teletypeStyleKey);

    override val tagStartAndEndPattern: Pattern = Pattern.compile("<${name}(\\s.+?)?>.+?</${name}>", Pattern.DOTALL)

    companion object {
        val anyTagPattern: Pattern = Pattern.compile("(</?.+?>)+", Pattern.DOTALL)
    }
}

enum class JavadocTag(name: String, override val style: TextAttributesKey) : Tag {
    JCODE("code", jCodeStyleKey),
    JLINK("link", jLinkStyleKey),
    JVALUE("value", jValueStyleKey),
    JLINKPLAING("linkplain", jLinkplainStyleKey),
    JLITERAL("literal", jLiteralStyleKey);

    override val tagStartAndEndPattern: Pattern = Pattern.compile("\\{@${name}(\\s).*?}", Pattern.DOTALL)

    companion object {
        val anyTagPattern: Pattern = Pattern.compile("(\\s?\\{@\\w+)(.*?)(})", Pattern.DOTALL)
    }
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
