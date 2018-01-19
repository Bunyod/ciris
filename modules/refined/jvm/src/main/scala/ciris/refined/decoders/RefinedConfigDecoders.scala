package ciris.refined.decoders

import ciris.ConfigDecoder
import eu.timepit.refined.api.{RefType, Validate}

import scala.reflect.runtime.universe.WeakTypeTag

trait RefinedConfigDecoders {
  implicit def refTypeConfigDecoder[F[_, _], T, P](
    implicit decoder: ConfigDecoder[T],
    refType: RefType[F],
    validate: Validate[T, P],
    typeTag: WeakTypeTag[F[T, P]]
  ): ConfigDecoder[F[T, P]] = {
    val typeName = typeTag.tpe.toString
    decoder.mapEither(typeName)(refType.refine[P].apply(_))
  }
}
