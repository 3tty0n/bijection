/*
Copyright 2012 Twitter, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.twitter.bijection

import java.lang.{
  Short => JShort,
  Integer => JInt,
  Long => JLong,
  Float => JFloat,
  Double => JDouble,
  Byte => JByte
}
import java.nio.ByteBuffer
import java.util.UUID

import Bijection.build

import scala.util.control.Exception.allCatch

trait NumericInjections extends GeneratedTupleInjections {
  implicit val byte2Short: Injection[Byte, Short] = new AbstractInjection[Byte, Short] {
    def apply(i: Byte) = i.toShort
    def invert(l: Short) =
      if (l <= Byte.MaxValue && l >= Byte.MinValue) Some(l.toByte) else None
  }
  implicit val short2Int: Injection[Short, Int] = new AbstractInjection[Short, Int] {
    def apply(i: Short) = i.toInt
    def invert(l: Int) =
      if (l <= Short.MaxValue && l >= Short.MinValue) Some(l.toShort) else None
  }
  implicit val int2Long: Injection[Int, Long] = new AbstractInjection[Int,Long] {
    def apply(i: Int) = i.toLong
    def invert(l: Long) =
      if (l <= Int.MaxValue && l >= Int.MinValue) Some(l.toInt) else None
  }

  implicit val byte2String: Injection[Byte, String] =
    new AbstractInjection[Byte, String] {
      def apply(b: Byte) = b.toString
      override def invert(s: String) = allCatch.opt(s.toByte)
    }
  implicit val jbyte2String: Injection[JByte, String] =
    new AbstractInjection[JByte, String] {
      def apply(b: JByte) = b.toString
      override def invert(s: String) = allCatch.opt(JByte.valueOf(s))
    }
  implicit val short2String: Injection[Short, String] =
    new AbstractInjection[Short, String] {
      def apply(s: Short) = s.toString
      override def invert(s: String) = allCatch.opt(s.toShort)
    }
  implicit val jshort2String: Injection[JShort, String] =
    new AbstractInjection[JShort, String] {
      def apply(b: JShort) = b.toString
      override def invert(s: String) = allCatch.opt(JShort.valueOf(s))
    }
  implicit val int2String: Injection[Int, String] =
    new AbstractInjection[Int, String] {
      def apply(i: Int) = i.toString
      override def invert(s: String) = allCatch.opt(s.toInt)
    }
  implicit val jint2String: Injection[JInt, String] =
    new AbstractInjection[JInt, String] {
      def apply(i: JInt) = i.toString
      override def invert(s: String) = allCatch.opt(JInt.valueOf(s))
    }

  implicit val long2String: Injection[Long, String] =
    new AbstractInjection[Long, String] {
      def apply(l: Long) = l.toString
      override def invert(s: String) = allCatch.opt(s.toLong)
    }
  implicit val jlong2String: Injection[JLong, String] =
    new AbstractInjection[JLong, String] {
      def apply(l: JLong) = l.toString
      override def invert(s: String) = allCatch.opt(JLong.valueOf(s))
    }

  implicit val float2String: Injection[Float, String] =
    new AbstractInjection[Float, String] {
      def apply(f: Float) = f.toString
      override def invert(s: String) = allCatch.opt(s.toFloat)
    }

  implicit val jfloat2String: Injection[JFloat, String] =
    new AbstractInjection[JFloat, String] {
      def apply(f: JFloat) = f.toString
      override def invert(s: String) = allCatch.opt(JFloat.valueOf(s))
    }

  implicit val double2String: Injection[Double, String] =
    new AbstractInjection[Double, String] {
      def apply(d: Double) = d.toString
      override def invert(s: String) = allCatch.opt(s.toDouble)
    }

  implicit val jdouble2String: Injection[JDouble, String] =
    new AbstractInjection[JDouble, String] {
      def apply(d: JDouble) = d.toString
      override def invert(s: String) = allCatch.opt(JDouble.valueOf(s))
    }

  implicit val short2BigEndian: Injection[Short, Array[Byte]] =
    new AbstractInjection[Short, Array[Byte]] {
      def apply(value: Short) = {
        val buf = ByteBuffer.allocate(2)
        buf.putShort(value)
        buf.array
      }
      override def invert(b: Array[Byte]) = allCatch.opt(ByteBuffer.wrap(b).getShort)
    }

  implicit val int2BigEndian: Injection[Int, Array[Byte]] =
    new AbstractInjection[Int, Array[Byte]] { value =>
      def apply(value: Int) = {
        val buf = ByteBuffer.allocate(4)
        buf.putInt(value)
        buf.array
      }
      override def invert(b: Array[Byte]) = allCatch.opt(ByteBuffer.wrap(b).getInt)
    }

  implicit val long2BigEndian: Injection[Long, Array[Byte]] =
    new AbstractInjection[Long, Array[Byte]] {
      def apply(value: Long) = {
        val buf = ByteBuffer.allocate(8)
        buf.putLong(value)
        buf.array
      }
      override def invert(b: Array[Byte]) = allCatch.opt(ByteBuffer.wrap(b).getLong)
    }

  implicit val float2BigEndian: Injection[Float, Array[Byte]] =
    Injection.fromBijection(Bijection.float2IntIEEE754) andThen int2BigEndian
  implicit val double2BigEndian: Injection[Double, Array[Byte]] =
    Injection.fromBijection(Bijection.double2LongIEEE754) andThen long2BigEndian
}
