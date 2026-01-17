package org.atteo.xmlcombiner;

import java.util.List;
import java.util.Map;

/**
 * Defines how child contexts are grouped by their key attributes for merging.
 */
public interface ChildContextsMapper {

    /**
     * Builds a lookup of child contexts keyed by {@link Key}.
     *
     * @param parent the parent context whose children will be analyzed
     * @param keyAttributeNames attribute names that contribute to an element key
     * @return mappings from {@link Key} to lists of matching {@link Context}
     */
    Map<Key, List<Context>> mapChildContexts(Context parent, List<String> keyAttributeNames);
}
