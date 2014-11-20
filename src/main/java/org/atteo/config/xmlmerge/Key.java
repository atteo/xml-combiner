/*
 * Copyright 2012 Atteo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.atteo.config.xmlmerge;

/**
 * Element name and the value of it's 'id' attribute if exists.
 */
class Key {
	public static final Key BEFORE_END = new Key("", "");
	private final String name;
	private final String id;

	public Key(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		if (name != null) {
			hash += name.hashCode();
		}
		if (id != null) {
			hash = hash * 37 + id.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Key other = (Key) obj;
		if ((name == null) ? (other.getName() != null) : !name.equals(other.getName())) {
			return false;
		}
		if ((id == null) ? (other.getId() != null) : !id.equals(other.getId())) {
			return false;
		}
		return true;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		if (id != null) {
			return name + "#" + id;
		} else {
			return name;
		}
	}

}
