package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.and
import org.runestar.client.updater.mapper.predicateOf
import org.runestar.client.updater.mapper.type
import org.runestar.client.updater.mapper.Class2
import org.objectweb.asm.Type
import java.lang.reflect.Modifier

class Bounds : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == Any::class.java.type }
            .and { it.interfaces.isEmpty() }
            .and { it.instanceFields.size == 4 }
            .and { it.instanceFields.all { it.type == Type.INT_TYPE } }
            .and { it.instanceMethods.isNotEmpty() }
            .and { !Modifier.isAbstract(it.access) }
}