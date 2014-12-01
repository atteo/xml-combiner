Overview
========
XmlCombiner allows to combine multiple XML files into one.

For instance having two XML documents:

```xml
<config>
	<name>Alex</name>
</config>
```

and
```xml
<config>
	<surname>Murphy</surname>
</config>
```

then the result of merging those two documents will be:
```xml
<config>
	<name>Alex</name>
	<surname>Murphy</surname>
</config>
```

Attributes are merged in the same way:

```xml
<config name="Alex"/>
```

and

```xml
<config surname="Murphy"/>
```

results in

```xml
<config name="Alex" surname="Murphy"/>
```

Usage
=====

```java

import org.atteo.xmlcombiner.XmlCombiner;

// create combiner
XmlCombiner combiner = new XmlCombiner();

// combine files
combiner.combine(firstFile);
combiner.combine(secondFile);

// store the result
combiner.buildDocument(resultFile);

```

Controlling the merging behavior
================================

By default matching tags from two files are [merged](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#MERGE). That is, given two XML documents:
```xml
<config>
	<name>John</name>
	<surname>Murphy</surname>
</config>
```

and
```xml
<config>
	<name>Alex</name>
</config>
```

the result would be:

```xml
<config>
	<name>Alex</name>
	<surname>Murphy</surname>
</config>
```

Observe how the default behavior resulted in using the content of the &lt;name&gt; tag from the second file, ignoring the content from the first one.

If instead we would like for the children of the &lt;config&gt; tag from the second file to be appended to the children of the &lt;config&gt; tag from the first file then we can alter the way the combiner works using special ['combine.children'](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html) attribute.

Merging first file with 'combine.children' attribute set to ['append'](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/  atteo/xmlcombiner/CombineChildren.html#APPEND) value:
```xml
<config combine.children='append'>
    <name>John</name>
	<surname>Murphy</surname>
</config>
```

with the second file unchanged

```xml
<config>
	<name>Alex</name>
</config>
```

results in

```xml
<config>
    <name>John</name>
    <surname>Murphy</surname>
    <name>Alex</name>
</config>
```

In addition there is also ['combine.self'](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html) attribute which allows to control how the element itself is combined.
Combining

```xml
<config>
    <name>John</name>
</config>
```

with

```xml
<config>
    <name combine.self='remove'/>
</config>
```

results in

```xml
<config>
</config>
```

Below you can find the table of all allowed values which link to their detailed description.

| CombineChildren | CombineSelf |
|-----------------|-------------|
| [merge](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#MERGE) (default) | [merge](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#MERGE) (default) |
| [append](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#APPEND) | [defaults](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#DEFAULTS) |
| | [overridable](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#OVERRIDABLE) |
| | [overridable_by_tag](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#OVERRIDABLE_BY_TAG) |
| | [override](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#OVERRIDE) |
| | [remove](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#REMOVE) | 

Matching the elements
=====================
Matching the elements between the merged XML attributes based only on their tag names is usually insufficient.
For instance let's analyze the following two files:

```xml
<config>
    <div id='title'>
		<h1>Title</h1>
	</div>
    <div id='button'>
		<button>OK</button>
	</div>
</config>
```

and

```xml
<config>
    <div id='button'>
        <button>Cancel</button>
    </div>
</config>
```

Here the intent is to merge 'div#button' elements between two files. So the key used to match the elements should consist of tag name and 'id' attribute.

Global keys
-----------
We can tell XmlCombiner which attributes to include in the key by listing their names in its [constructor](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.0/xml-combiner-2.0-javadoc.jar/!/org/atteo/xmlcombiner/XmlCombiner.html#XmlCombiner(java.lang.String)) call as follows:

```java
import org.atteo.xmlcombiner.XmlCombiner;

// create combiner
XmlCombiner combiner = new XmlCombiner("id");
...
```

Then the result will correctly be:
```xml
<config>
    <div id='title'>
		<h1>Title</h1>
	</div>
    <div id='button'>
		<button>Cancel</button>
	</div>
</config>
```

Local keys
----------
Another option to specify the keys is within the XML document itself.
For instance combining

```xml
<config combine.keys='id'>
    <service id='first' name='alpha'/>
    <service id='second' name='beta'/>
</config>
```

with

```xml
<config>
    <service id='second' name='theta'/>
</config>
```

will result in

```xml
<config>
    <service id='first' name='alpha'/>
    <service id='second' name='theta'/>
</config>
```

Multiple keys can be specified by separating them using comma, for example:
```xml
<config combine.keys='id,name'>
...
```

Artificial key
--------------
Sometimes the XML file format does not contain any usable keys. In this case you can specify an artificial key in 'combine.id' attribute.
For instance combining

```xml
<config>
    <service combine.id='1' name='a'/>
    <service combine.id='2' name='b'/>
</config>
```

with

```xml
<config>
    <service combine.id='1' name='c'/>
    <service combine.id='3' name='d'/>
</config>
```

will result in

```xml
<config>
    <service name='c'/>
    <service name='b'/>
    <service name='d'/>
</config>
```

Notice how 'combine.id' attribute was removed from the final output.

