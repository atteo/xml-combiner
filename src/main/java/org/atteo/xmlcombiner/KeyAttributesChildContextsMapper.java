package org.atteo.xmlcombiner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class KeyAttributesChildContextsMapper implements ChildContextsMapper {

    @Override
    public Map<Key, List<Context>> mapChildContexts(Context parent, List<String> keyAttributeNames) {
        List<Context> contexts = parent.groupChildContexts();

        Map<Key, List<Context>> map = new LinkedHashMap<>();
        for (Context context : contexts) {
            Element contextElement = context.getElement();

            if (contextElement != null) {
                Map<String, String> keys = new LinkedHashMap<>();
                for (String keyAttributeName : keyAttributeNames) {
                    Attr keyNode = contextElement.getAttributeNode(keyAttributeName);
                    if (keyNode != null) {
                        keys.put(keyAttributeName, keyNode.getValue());
                    }
                }
                {
                    Attr keyNode = contextElement.getAttributeNode(Context.ID_ATTRIBUTE_NAME);
                    if (keyNode != null) {
                        keys.put(Context.ID_ATTRIBUTE_NAME, keyNode.getValue());
                    }
                }
                Key key = new Key(contextElement.getTagName(), keys);

                List<Context> destinationContexts = map.computeIfAbsent(key, k -> new ArrayList<>());
                destinationContexts.add(context);

            } else {
                List<Context> destinationContexts = map.computeIfAbsent(Key.BEFORE_END, k -> new ArrayList<>());
                destinationContexts.add(context);
            }
        }
        return map;
    }
}
