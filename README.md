Intellij Plugin ― JavaDoc HTML Cleaner
====================================== 

[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT)
[![GitHub Release](https://img.shields.io/github/release/gindex/intellij-javadoc-html-cleaner.svg)](https://github.com/gindex/intellij-javadoc-html-cleaner/releases) 
[![JetBrains Marketplace](https://img.shields.io/jetbrains/plugin/v/13344-javadoc-html-cleaner)](https://plugins.jetbrains.com/plugin/13344-javadoc-html-cleaner)
[![JetBrains Marketplace Download](https://img.shields.io/jetbrains/plugin/d/13344-javadoc-html-cleaner)](https://plugins.jetbrains.com/plugin/13344-javadoc-html-cleaner)
[![Github Gradle C](https://github.com/gindex/intellij-javadoc-html-cleaner/workflows/Gradle%20CI/badge.svg)](https://github.com/gindex/intellij-javadoc-html-cleaner/actions)

Improves JavDoc readability by hiding HTML markup and replacing reserved chars.
Basic features:
- Folds HTML and Javadoc tags (collapse / expand `⌘-/⌘+` on macOS)
- Highlights contained text
- Provides highlighting configuration
- Replaces reserved chars

For configuration navigate to `Preferences -> Editor -> Color Schema -> JavaDoc HTML Cleaner`. 
Includes default configuration for the light and darcula theme. 

Usage
-----
Tags are collapsed by default. 

![Folding Example](https://user-images.githubusercontent.com/4037842/70388764-5b9d5000-19b6-11ea-9b3f-cc53ad60e3cc.gif)

Build plugin
------------

Requires:
- JDK 1.8
- Gradle 6

Build the plugin:

`./gradlew clean buildPlugin`

Run the plugin in Intellij:

`./gradlew runIde`

Compatibility
-------------

Compatible with Intellij IDEA 2019.2+

License
-------

This plugin is licensed under [MIT](LICENSE).