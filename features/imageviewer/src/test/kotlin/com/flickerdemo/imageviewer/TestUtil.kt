package com.flickerdemo.imageviewer

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
import kotlin.reflect.KClass

fun <T : Any> KClass<T>.mock(): T = Mockito.mock(this.java)

fun <T> whenCalled(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!