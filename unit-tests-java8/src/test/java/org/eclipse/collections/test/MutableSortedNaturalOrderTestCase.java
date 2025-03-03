/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;

import java.util.Iterator;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.test.collection.mutable.MutableCollectionTestCase;
import org.junit.Test;

import static org.eclipse.collections.test.IterableTestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public interface MutableSortedNaturalOrderTestCase extends SortedNaturalOrderTestCase, MutableOrderedIterableTestCase, MutableCollectionTestCase
{
    @Test
    @Override
    default void Iterable_remove()
    {
        Iterable<Integer> iterable = this.newWith(1, 1, 1, 2, 2, 3);
        Iterator<Integer> iterator = iterable.iterator();
        iterator.next();
        iterator.remove();
        assertEquals(this.newWith(1, 1, 2, 2, 3), iterable);
    }

    @Override
    @Test
    default void MutableCollection_removeIf()
    {
        MutableCollection<Integer> collection1 = this.newWith(1, 1, 2, 2, 3, 3, 4, 4, 5, 5);
        assertTrue(collection1.removeIf(Predicates.cast(each -> each % 2 == 0)));
        assertEquals(this.getExpectedFiltered(1, 1, 3, 3, 5, 5), collection1);

        MutableCollection<Integer> collection2 = this.newWith(1, 2, 3, 4);
        assertFalse(collection2.removeIf(Predicates.equal(5)));
        assertTrue(collection2.removeIf(Predicates.greaterThan(0)));
        assertEquals(this.newWith(), collection2);
        assertFalse(collection2.removeIf(Predicates.greaterThan(2)));

        Predicate<Object> predicate = null;
        assertThrows(NullPointerException.class, () -> this.newWith(1, 4, 5, 7).removeIf(predicate));
    }

    @Override
    @Test
    default void MutableCollection_removeIfWith()
    {
        MutableCollection<Integer> collection = this.newWith(1, 1, 2, 2, 3, 3, 4, 4, 5, 5);
        assertTrue(collection.removeIfWith(Predicates2.in(), Lists.immutable.with(5, 3, 1)));
        assertEquals(this.getExpectedFiltered(2, 2, 4, 4), collection);
        assertThrows(NullPointerException.class, () -> this.newWith(7, 4, 5, 1).removeIfWith(null, this));

        MutableCollection<Integer> collection2 = this.newWith(1, 2, 3, 4);
        assertFalse(collection2.removeIfWith(Predicates2.equal(), 5));
        assertTrue(collection2.removeIfWith(Predicates2.greaterThan(), 0));
        assertEquals(this.newWith(), collection2);
        assertFalse(collection2.removeIfWith(Predicates2.greaterThan(), 2));

        assertThrows(NullPointerException.class, () -> this.newWith(1, 4, 5, 7).removeIfWith(null, null));
    }
}
