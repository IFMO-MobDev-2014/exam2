package me.volhovm.android

package object exam2 {
  def cast[A, B](x: A): B = x match {
    case a: B => a
    case _ => throw new ClassCastException
  }
}
