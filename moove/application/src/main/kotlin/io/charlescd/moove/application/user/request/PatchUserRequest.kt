/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.charlescd.moove.application.user.request

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.charlescd.moove.application.BasePatchRequest
import io.charlescd.moove.application.OpCodeEnum
import io.charlescd.moove.application.PatchOperation
import io.charlescd.moove.domain.User
import org.springframework.util.Assert

data class PatchUserRequest(override val patches: List<PatchOperation>) : BasePatchRequest<User>(patches) {

    companion object {
        val paths = listOf("/name")
    }

    override fun validate() {
        validatePaths()
        validateOperations()
        validateValues()
    }

    private fun validatePaths() {
        patches.forEach { patch ->
            Assert.isTrue(paths.contains(patch.path), "Path ${patch.path} is not allowed.")
        }
    }

    private fun validateOperations() {
        Assert.isTrue(patches.none { it.op == OpCodeEnum.ADD }, "Add operation not allowed.")
        Assert.isTrue(patches.none { it.op == OpCodeEnum.REMOVE }, "Remove operation not allowed.")
    }

    private fun validateValues() {
        patches.forEach { patch ->
            Assert.notNull(patch.value, "Name cannot be null.")
            jacksonObjectMapper().convertValue<String>(patch.value!!).let { name ->
                Assert.isTrue((name.length in 1..64), "Name minimum size is 1 and maximum is 64.")
            }
        }
    }
}
