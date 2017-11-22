package com.runesuite.client.updater.mapper.std.classes

import com.runesuite.mapper.IdentityMapper
import com.runesuite.mapper.OrderMapper
import com.runesuite.mapper.annotations.DependsOn
import com.runesuite.mapper.annotations.MethodParameters
import com.runesuite.mapper.extensions.and
import com.runesuite.mapper.extensions.predicateOf
import com.runesuite.mapper.extensions.type
import com.runesuite.mapper.tree.Class2
import com.runesuite.mapper.tree.Field2
import com.runesuite.mapper.tree.Instruction2
import com.runesuite.mapper.tree.Method2
import org.objectweb.asm.Opcodes.GETFIELD
import org.objectweb.asm.Opcodes.PUTFIELD
import org.objectweb.asm.Type.VOID_TYPE

// https://en.wikipedia.org/wiki/Indexed_color
@DependsOn(Rasterizer2D::class)
class IndexedSprite : IdentityMapper.Class() {

    override val predicate = predicateOf<Class2> { it.superType == type<Rasterizer2D>() }
            .and { it.instanceFields.count { it.type == ByteArray::class.type } == 1 }
            .and { it.instanceFields.count { it.type == IntArray::class.type } == 1 }

    @MethodParameters()
    class normalize : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.size in 0..1 }
    }

    class palette : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == IntArray::class.type }
    }

    class pixels : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == ByteArray::class.type }
    }

    @DependsOn(normalize::class)
    class width : OrderMapper.InMethod.Field(normalize::class, -2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == GETFIELD && it.fieldOwner == type<IndexedSprite>() }
    }

    @DependsOn(normalize::class)
    class height : OrderMapper.InMethod.Field(normalize::class, -1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == GETFIELD && it.fieldOwner == type<IndexedSprite>() }
    }

    @DependsOn(normalize::class)
    class offsetX : OrderMapper.InMethod.Field(normalize::class, -2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldOwner == type<IndexedSprite>() }
    }

    @DependsOn(normalize::class)
    class offsetY : OrderMapper.InMethod.Field(normalize::class, -1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldOwner == type<IndexedSprite>() }
    }

    @DependsOn(normalize::class)
    class subWidth : OrderMapper.InMethod.Field(normalize::class, -4) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldOwner == type<IndexedSprite>() }
    }

    @DependsOn(normalize::class)
    class subHeight : OrderMapper.InMethod.Field(normalize::class, -3) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldOwner == type<IndexedSprite>() }
    }
}