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

package io.charlescd.moove.infrastructure.service.client

import io.charlescd.moove.infrastructure.configuration.SimpleFeignEncoderConfiguration
import io.charlescd.moove.infrastructure.service.client.request.HermesSubscriptionCreateRequest
import io.charlescd.moove.infrastructure.service.client.request.HermesSubscriptionUpdateRequest
import io.charlescd.moove.infrastructure.service.client.response.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@FeignClient(name = "hermesClient", url = "\${charlescd.hermes.url}", configuration = [ SimpleFeignEncoderConfiguration::class])
interface HermesClient {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        value = ["/subscriptions"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun subscribe(
        @RequestHeader("x-author") authorEmail: String,
        @RequestBody request: HermesSubscriptionCreateRequest
    ): HermesSubscriptionCreateResponse

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(
        value = ["/subscriptions/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateSubscription(
        @RequestHeader("x-author") authorEmail: String,
        @PathVariable("id") id: String,
        @RequestBody request: HermesSubscriptionUpdateRequest
    ): HermesSubscriptionResponse

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        value = ["/subscriptions/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getSubscription(
        @RequestHeader("x-author") authorEmail: String,
        @PathVariable("id") id: String
    ): HermesSubscriptionResponse

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
        value = ["/subscriptions/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteSubscription(
        @RequestHeader("x-author") authorEmail: String,
        @PathVariable("id") id: String
    )

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        value = ["/subscriptions/{id}/health-check"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun healthCheckSubscription(
        @RequestHeader("x-author") authorEmail: String,
        @PathVariable("id") id: String
    ): HermesHealthCheckSubscriptionResponse
}