/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.budjb.spring.distributed.lock.hazelcast

import com.hazelcast.core.ILock
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class HazelcastDistributedLockSpec extends Specification {
    def 'Hazelcast locks support leases'() {
        setup:
        ILock lock = Mock(ILock)
        expect:
        new HazelcastDistributedLock(lock).supportsLeases()
    }

    def 'Method calls are proxied to the underlying lock implementation'() {
        setup:
        ILock hazelcastLock = Mock(ILock)
        HazelcastDistributedLock lock = new HazelcastDistributedLock(hazelcastLock)

        when:
        lock.isLocked()
        lock.lock()
        lock.lockInterruptibly()
        lock.tryLock()
        lock.tryLock(1, TimeUnit.MICROSECONDS)
        lock.unlock()
        lock.newCondition()
        lock.tryLock(1, TimeUnit.MICROSECONDS, 1, TimeUnit.MICROSECONDS)
        lock.lock(1, TimeUnit.MICROSECONDS)

        then:
        1 * hazelcastLock.isLocked()
        1 * hazelcastLock.lock()
        1 * hazelcastLock.lockInterruptibly()
        1 * hazelcastLock.tryLock()
        1 * hazelcastLock.tryLock(1, TimeUnit.MICROSECONDS)
        1 * hazelcastLock.unlock()
        1 * hazelcastLock.newCondition()
        1 * hazelcastLock.tryLock(1, TimeUnit.MICROSECONDS, 1, TimeUnit.MICROSECONDS)
        1 * hazelcastLock.lock(1, TimeUnit.MICROSECONDS)

    }
}
