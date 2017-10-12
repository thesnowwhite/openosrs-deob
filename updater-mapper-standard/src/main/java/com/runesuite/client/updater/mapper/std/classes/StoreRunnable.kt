package com.runesuite.client.updater.mapper.std.classes

import com.runesuite.mapper.IdentityMapper
import com.runesuite.mapper.extensions.Predicate
import com.runesuite.mapper.extensions.and
import com.runesuite.mapper.extensions.predicateOf
import com.runesuite.mapper.extensions.type
import com.runesuite.mapper.tree.Class2

class StoreRunnable : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.interfaces.contains(Runnable::class.type) }
            .and { it.superType == Any::class.type }
            .and { it.instanceFields.isEmpty() }
}