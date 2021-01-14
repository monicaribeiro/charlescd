/*
 *
 *  Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.charlescd.moove.application

import io.charlescd.moove.domain.HealthCheckWebhookSubscription
import io.charlescd.moove.domain.SimpleWebhookSubscription
import io.charlescd.moove.domain.User
import io.charlescd.moove.domain.WebhookSubscription
import io.charlescd.moove.domain.exceptions.NotFoundException
import io.charlescd.moove.domain.service.HermesService
import javax.inject.Named

@Named
class WebhookService(private val hermesService: HermesService, private val userService: UserService) {

    fun subscribe(authorization: String, webhookSubscription: WebhookSubscription): String {
        return hermesService.subscribe(getAuthorEmail(authorization), webhookSubscription)
    }

    fun getSubscription(workspaceId: String, authorization: String, id: String): SimpleWebhookSubscription {
        val author = getAuthor(authorization)
        val subscription = hermesService.getSubscription(author.email, id)
        validateWorkspace(workspaceId, id, author, subscription)
        return subscription
    }

    fun updateSubscription(workspaceId: String, authorization: String, id: String, events: List<String>): SimpleWebhookSubscription {
        val author = getAuthor(authorization)
        validateSubscription(workspaceId, author, id)
        return hermesService.updateSubscription(author.email, id, events)
    }

    fun deleteSubscription(workspaceId: String, authorization: String, id: String) {
        val author = getAuthor(authorization)
        validateSubscription(workspaceId, author, id)
        hermesService.deleteSubscription(author.email, id)
    }

    fun healthCheckSubscription(workspaceId: String, authorization: String, id: String): HealthCheckWebhookSubscription {
        val author = getAuthor(authorization)
        validateSubscription(workspaceId, author, id)
        return hermesService.healthCheckSubscription(author.email, id)
    }

    private fun getAuthor(authorization: String): User {
        return userService.findByAuthorizationToken(authorization)
    }

    private fun getAuthorEmail(authorization: String): String {
        return userService.getEmailFromToken(authorization)
    }

    private fun validateWorkspace(workspaceId: String, id: String, author: User, subscription: SimpleWebhookSubscription) {
        if (!author.root && subscription.workspaceId != workspaceId) {
            throw NotFoundException("subscription", id)
        }
    }

    private fun validateSubscription(workspaceId: String, author: User, id: String) {
        val subscription = hermesService.getSubscription(author.email, id)
        validateWorkspace(workspaceId, id, author, subscription)
    }
}
