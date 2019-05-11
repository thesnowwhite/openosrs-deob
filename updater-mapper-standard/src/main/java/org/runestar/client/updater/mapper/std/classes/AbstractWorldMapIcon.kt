package org.runestar.client.updater.mapper.std.classes

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.Predicate
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2
import java.lang.reflect.Modifier

@DependsOn(TileLocation::class)
class AbstractWorldMapIcon : IdentityMapper.Class() {

    override val predicate = predicateOf<Class2> { it.superType == Any::class.type }
            .and { it.instanceFields.size == 4 }
            .and { it.instanceFields.count { it.type == type<TileLocation>() } == 2 }
            .and { it.instanceFields.count { it.type == Type.INT_TYPE } == 2 }

    @DependsOn(TileLocation::class)
    class coord1 : OrderMapper.InConstructor.Field(AbstractWorldMapIcon::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.PUTFIELD && it.fieldType == type<TileLocation>() }
    }

    @DependsOn(TileLocation::class)
    class coord2 : OrderMapper.InConstructor.Field(AbstractWorldMapIcon::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.PUTFIELD && it.fieldType == type<TileLocation>() }
    }

    @MethodParameters()
    @DependsOn(WorldMapLabel::class)
    class label : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<WorldMapLabel>() }
    }

    @MethodParameters()
    class element : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == Type.INT_TYPE }
                .and { Modifier.isPublic(it.access) && Modifier.isAbstract(it.access) }
    }
}