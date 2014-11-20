Overview
========
XmlCombiner allows to merge multiple XML files into one.

The merging algorithm for two XML trees is as follows:

* The elements from both trees with matching both tag name and the value of 'id' attribute are paired.
* Based on selected behavior the content of the paired elements is then merged.
* Finally the paired elements are recursively combined and any not paired elements are appended.

Example:

For two input files:

1.
```xml
<config>
   <service id="1">
       <parameter>value</parameter>
   </service>
   <service id="2"/>
</config>
```

2.
```xml
<config>
   <service id="1">
       <parameter>othervalue</parameter>
   </service>
   <service id="3"/>
</config>
```

The merging algorithm will produce:
```xml
<config>
   <service id="1">
       <parameter>othervalue</parameter>
   </service>
   <service id="2"/>
   <service id="3"/>
</config>
```

You can control merging behavior using 'combine.self' and 'combine.children' attributes.
For instance if you specify 'combine.children=APPEND' on <config> element in first document, like below:

1.
```xml
<config combine.children='APPEND'>
   <service id="1">
       <parameter>value</parameter>
   </service>
   <service id="2"/>
</config>
```

then the following result will be produced:

```xml
<config>
   <service id="1">
       <parameter>value</parameter>
   </service>
   <service id="2"/>
   <service id="1">
       <parameter>othervalue</parameter>
   </service>
   <service id="3"/>
</config>
```

