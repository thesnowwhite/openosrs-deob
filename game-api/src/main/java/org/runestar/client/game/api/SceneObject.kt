package org.runestar.client.game.api

import org.runestar.client.game.raw.Accessor
import org.runestar.client.game.raw.Client
import org.runestar.client.game.raw.access.*
import java.util.*

abstract class SceneObject {

    abstract val tag: EntityTag

    abstract val accessor: Accessor

    val location get() = tag.location

    abstract val orientation: Angle

    abstract val position: Position

    abstract val models: Collection<Model>

    class Interactable(
            override val accessor: XGameObject
    ) : SceneObject() {

        override val orientation: Angle get() = Angle(accessor.orientation)

        override val position: Position get() = Position(accessor.centerX, accessor.centerY, 0, location.plane)

        val model: Model? get() {
            val m = accessor.entity as? XModel ?: accessor.entity.model ?: return null
            return Model(m, position, orientation)
        }

        override val models: List<Model> get() = model?.let { Collections.singletonList(it) } ?: emptyList()

        override val tag get() = EntityTag(accessor.tag, accessor.plane)

        override fun toString(): String {
            return "SceneObject.Interactable(tag=$tag)"
        }
    }

    class Floor(
            override val accessor: XFloorDecoration,
            val plane: Int = Client.accessor.plane
    ) : SceneObject() {

        override val orientation: Angle get() = Angle.ZERO

        override val position: Position get() = location.center

        val model: Model? get() {
            val m = accessor.entity as? XModel ?: accessor.entity.model ?: return null
            return Model(m, position, orientation)
        }

        override val models: List<Model> get() = model?.let { Collections.singletonList(it) } ?: emptyList()

        override val tag get() = EntityTag(accessor.tag, plane)

        override fun toString(): String {
            return "SceneObject.Floor(tag=$tag)"
        }
    }

    class Wall(
            override val accessor: XWallDecoration,
            val plane: Int = Client.accessor.plane
    ) : SceneObject() {

        override val orientation: Angle get() = Angle(accessor.orientation)

        override val position: Position get() = location.center // todo

        val model1: Model? get() {
            val m = accessor.entity1 as? XModel ?: accessor.entity1?.model ?: return null
            return Model(m, position, orientation)
        }

        val model2: Model? get() {
            val m = accessor.entity2 as? XModel ?: accessor.entity2?.model ?: return null
            return Model(m, position, orientation)
        }

        override val models: List<Model> get() {
            val m1 = model1 ?: return emptyList()
            val m2 = model2 ?: return Collections.singletonList(m1)
            return listOf(m1, m2)
        }

        override val tag get() = EntityTag(accessor.tag, plane)

        override fun toString(): String {
            return "SceneObject.Wall(tag=$tag)"
        }
    }

    class Boundary(
            override val accessor: XBoundaryObject,
            val plane: Int = Client.accessor.plane
    ) : SceneObject() {

        override val orientation: Angle get() = Angle.ZERO

        override val position: Position get() = location.center

        val model1: Model? get() {
            val m = accessor.entity1 as? XModel ?: accessor.entity1?.model ?: return null
            return Model(m, position, orientation)
        }

        val model2: Model? get() {
            val m = accessor.entity2 as? XModel ?: accessor.entity2?.model ?: return null
            return Model(m, position, orientation)
        }

        override val models: List<Model> get() {
            val m1 = model1 ?: return emptyList()
            val m2 = model2 ?: return Collections.singletonList(m1)
            return listOf(m1, m2)
        }

        override val tag get() = EntityTag(accessor.tag, plane)

        override fun toString(): String {
            return "SceneObject.Boundary(tag=$tag)"
        }
    }
}