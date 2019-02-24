package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2

@DependsOn(AbstractSoundNode::class)
class SoundNode3 : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == type<AbstractSoundNode>() }
            .and { it.instanceFields.size == 3 }

    @DependsOn(NodeDeque::class)
    class queue : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == type<NodeDeque>() }
    }
}