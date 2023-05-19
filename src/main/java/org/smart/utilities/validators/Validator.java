package org.smart.utilities.validators;

public interface Validator<E> {
  void validate(E obj);
}
