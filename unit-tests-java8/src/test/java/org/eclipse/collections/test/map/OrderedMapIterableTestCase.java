/*
 * Copyright (c) 2021 Two Sigma.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.map;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.OrderedMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.test.OrderedIterableTestCase;
import org.eclipse.collections.test.list.TransformsToListTrait;
import org.junit.Test;

import static org.eclipse.collections.test.IterableTestCase.assertEquals;
import static org.junit.Assert.assertThrows;

public interface OrderedMapIterableTestCase extends MapIterableTestCase, OrderedIterableTestCase, TransformsToListTrait
{
    @Override
    <T> OrderedMap<Object, T> newWith(T... elements);

    @Override
    <K, V> OrderedMap<K, V> newWithKeysValues(Object... elements);

    @Override
    default <T> ListIterable<T> getExpectedFiltered(T... elements)
    {
        return Lists.immutable.with(elements);
    }

    @Override
    default <T> MutableList<T> newMutableForFilter(T... elements)
    {
        return Lists.mutable.with(elements);
    }

    @Test
    default void take()
    {
        OrderedMap<Integer, String> orderedMap = this.newWithKeysValues(3, "Three", 2, "Two", 1, "Three");
        assertEquals(this.newWithKeysValues(), orderedMap.take(0));
        assertEquals(this.newWithKeysValues(3, "Three"), orderedMap.take(1));
        assertEquals(this.newWithKeysValues(3, "Three", 2, "Two"), orderedMap.take(2));
        assertEquals(this.newWithKeysValues(3, "Three", 2, "Two", 1, "Three"), orderedMap.take(3));
        assertEquals(this.newWithKeysValues(3, "Three", 2, "Two", 1, "Three"), orderedMap.take(4));
        assertEquals(this.newWithKeysValues(3, "Three", 2, "Two", 1, "Three"), orderedMap.take(Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> orderedMap.take(-1));
    }

    @Test
    default void drop()
    {
        OrderedMap<Integer, String> orderedMap = this.newWithKeysValues(3, "Three", 2, "Two", 1, "Three");
        assertEquals(this.newWithKeysValues(3, "Three", 2, "Two", 1, "Three"), orderedMap.drop(0));
        assertEquals(this.newWithKeysValues(2, "Two", 1, "Three"), orderedMap.drop(1));
        assertEquals(this.newWithKeysValues(1, "Three"), orderedMap.drop(2));
        assertEquals(this.newWithKeysValues(), orderedMap.drop(3));
        assertEquals(this.newWithKeysValues(), orderedMap.drop(4));
        assertEquals(this.newWithKeysValues(), orderedMap.drop(Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> orderedMap.drop(-1));
    }

    @Override
    default void MapIterable_flipUniqueValues()
    {
        MapIterable<String, Integer> map = this.newWithKeysValues("Three", 3, "Two", 2, "One", 1);
        assertThrows(UnsupportedOperationException.class, map::flipUniqueValues);
    }
}
