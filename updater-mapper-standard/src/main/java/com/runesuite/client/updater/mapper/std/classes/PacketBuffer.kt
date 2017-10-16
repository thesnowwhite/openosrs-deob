package com.runesuite.client.updater.mapper.std.classes

import com.hunterwb.kxtra.collections.list.startsWith
import com.runesuite.mapper.IdentityMapper
import com.runesuite.mapper.annotations.DependsOn
import com.runesuite.mapper.annotations.MethodParameters
import com.runesuite.mapper.extensions.Predicate
import com.runesuite.mapper.extensions.and
import com.runesuite.mapper.extensions.predicateOf
import com.runesuite.mapper.extensions.type
import com.runesuite.mapper.tree.Class2
import com.runesuite.mapper.tree.Field2
import com.runesuite.mapper.tree.Method2
import org.objectweb.asm.Type

@DependsOn(Buffer::class)
class PacketBuffer : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == type<Buffer>() }

    class bitIndex : InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == Type.INT_TYPE }
    }

    class isaacCipher : InstanceField() {
        override val predicate = predicateOf<Field2> { it.type in it.jar }
    }

    @MethodParameters("array")
    class newIsaacCipher : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == Type.VOID_TYPE }
                .and { it.arguments.startsWith(IntArray::class.type) }
    }
}