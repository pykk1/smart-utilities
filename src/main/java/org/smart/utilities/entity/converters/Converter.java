package org.smart.utilities.entity.converters;

/**
 * Used to convert to/from DTO and Entity.
 *
 * @param <D> DTO
 * @param <E> Entity
 */
public interface Converter<D, E>{
  E convertToEntity(D dto);
  D convertToDTO(E entity);
}
