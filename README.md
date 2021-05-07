[![Build Status](https://travis-ci.org/atteo/xml-combiner.svg)](https://travis-ci.org/atteo/xml-combiner)
[![Coverage Status](https://img.shields.io/coveralls/atteo/xml-combiner.svg)](https://coveralls.io/r/atteo/xml-combiner)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.atteo/xml-combiner/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.atteo/xml-combiner)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0)

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

Maven dependency is:
```xml
<dependency>
    <groupId>org.atteo</groupId>
    <artifactId>xml-combiner</artifactId>
    <version>3.0.0</version>
</dependency>
```

Controlling the merging behavior
================================

By default matching tags from two files are [merged](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#MERGE). That is, given two XML documents:
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

If instead we would like for the children of the &lt;config&gt; tag from the second file to be appended to the children of the &lt;config&gt; tag from the first file then we can alter the way the combiner works using special ['combine.children'](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html) attribute.

Merging first file with 'combine.children' attribute set to ['append'](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#APPEND) value:
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

In addition there is also ['combine.self'](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html) attribute which allows to control how the element itself is combined.
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
| [merge](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#MERGE) (default) | [merge](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#MERGE) (default) |
| [append](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineChildren.html#APPEND) | [defaults](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#DEFAULTS) |
| | [overridable](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#OVERRIDABLE) |
| | [overridable_by_tag](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#OVERRIDABLE_BY_TAG) |
| | [override](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#OVERRIDE) |
| | [remove](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/CombineSelf.html#REMOVE) | 

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
We can tell XmlCombiner which attributes to include in the key by listing their names in its [constructor](https://oss.sonatype.org/service/local/repositories/releases/archive/org/atteo/xml-combiner/2.2/xml-combiner-2.2-javadoc.jar/!/org/atteo/xmlcombiner/XmlCombiner.html#XmlCombiner(java.lang.String)) call as follows:

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
Another option to specify the keys is within the XML document itself using 'combine.keys' attribute.
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

Filtering
=========
Filtering gives you the ability to further modify the resulting XML. For instance given two documents
```xml
<config>
    <element name='hydrogen' weight='1'/>
    <element name='helium' weight='2'/>
</config>
```

and

```xml
<config>
    <element name='hydrogen' weight='10'/>
    <element name='lithium' weight='20'/>
</config>
```

we want to get

```xml
<config>
    <element name='hydrogen' weight='11'/>
    <element name='helium' weight='2'/>
    <element name='lithium' weight='20'/>
</config>
```

That is, we want the weight to be the sum of the weights of the merged elements. To achieve that we can define the filter like this:

```java
XmlCombiner.Filter weightFilter = new XmlCombiner.Filter() {
	@Override
	public void postProcess(Element recessive, Element dominant, Element result) {
		if (recessive == null || dominant == null) {
			return;
		}
		Attr recessiveNode = recessive.getAttributeNode("weight");
		Attr dominantNode = dominant.getAttributeNode("weight");
		if (recessiveNode == null || dominantNode == null) {
			return;
		}

		int recessiveWeight = Integer.parseInt(recessiveNode.getValue());
		int dominantWeight = Integer.parseInt(dominantNode.getValue());

		result.setAttribute("weight", Integer.toString(recessiveWeight + dominantWeight));
	}
};

XmlCombiner combiner = new XmlCombiner("name");
combiner.setFilter(weightFilter);
```

Alternatives
============
* [Plexus Utils Xpp3DomUtils](http://plexus.codehaus.org/plexus-utils/apidocs/org/codehaus/plexus/util/xml/Xpp3DomUtils.html) - used by Maven to merge plugin configurations, not so straightforward to use outside Maven
* [EL4J XmlMerge](http://www.javaworld.com/article/2077736/open-source-tools/xml-merging-made-easy.html) - slightly different algorithm, it allows to specify merging behavior in separate file

