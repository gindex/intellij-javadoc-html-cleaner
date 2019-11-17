Intellij Plugin ― JavaDoc HTML Cleaner
====================================== 

Improves JavDoc readability by hiding HTML markup and replacing reserved chars.
Basic features:
- Folds HTML tags (collapse / expand `⌘-/⌘+` on macOS)
- Highlights contained text
- Provides highlighting configuration
- Replaces reserved chars

For configuration navigate to `Preferences -> Editor -> Color Schema -> JavaDoc HTML Cleaner`. 
Includes default configuration for the default and dracula theme. 

Usage
-----
Tags are collapsed by default. 

![](https://user-images.githubusercontent.com/4037842/69012627-364d9100-0978-11ea-9aec-255539d4d7f2.gif)

Build plugin
------------

Requires:
- JDK 1.8
- Gradle 6.0

Build the plugin:

`.\gradlew clean buildPlugin`

Run the plugin in Intellij:

`.\gradlew runIde`

Compatibility
-------------

Compatible with Intellij IDEA 2019.2+

License
-------

This plugin is licensed under [MIT](LICENSE).