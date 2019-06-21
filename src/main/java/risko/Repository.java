/*
 * Copyright (C) 2019 henke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package risko;

import java.util.HashMap;

/**
 *
 * @author henke
 */

class NotFound extends Exception {
    NotFound(String k) {
        super(k + ": not found");
    }
}

public class Repository<K, V> extends HashMap<K, V> {
    Repository() {
        super();
    }
    
    void takeFrom(Repository other, K key) throws NotFound {
        if (other.get(key) == null) {
            throw new NotFound(key.toString());
        }
        V value = (V) other.remove(key);
        this.put(key, value);
    }
}
