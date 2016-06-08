package com.bob.util

/**
 * Created by bob on 16/6/8.
 */
object TypeExt {

  /**
   * 模拟linux中shell管道
   * @param t
   * @tparam T
   * @return
   */
  implicit def pipifunc[T](t: T) = new {
    def |[R](f: T => R): R = f(t)
  }
}