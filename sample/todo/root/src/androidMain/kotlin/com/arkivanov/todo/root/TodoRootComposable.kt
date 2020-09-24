package com.arkivanov.todo.root

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.invoke
import com.arkivanov.todo.edit.invoke
import com.arkivanov.todo.main.invoke

@Composable
operator fun TodoRoot.Model.invoke() {
    child {
        Crossfade(current = it) { child ->
            when (child) {
                is TodoRoot.Child.Main -> child.model()
                is TodoRoot.Child.Edit -> child.model()
            }
        }
    }
}
