package org.atteo.xmlcombiner;

import java.util.List;
import java.util.Map;

public interface ChildContextsMapper {

	Map<Key, List<Context>> mapChildContexts(Context parent, List<String> keyAttributeNames);

}
